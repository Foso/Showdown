package showdown.web.network

import de.jensklingenberg.showdown.model.*
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.w3c.dom.MessageEvent
import org.w3c.dom.WebSocket
import org.w3c.dom.events.Event

sealed class Either {
    object Success : Either()
    data class Error(val showdownError: ShowdownError) : Either()
}

class GameApiClient {

    private var socket: WebSocket? = null

    private lateinit var observer: NetworkApiObserver

    fun start(observer: NetworkApiObserver): Flow<Either> {

        return callbackFlow {
            this@GameApiClient.observer = observer
            socket = WebSocket(NetworkPreferences().websocketUrl())

            socket?.apply {
                onopen = {
                    trySend(Either.Success)
                }
                onmessage = { event: Event ->
                    onMessage((event as MessageEvent))
                }

                onerror = {
                    observer.onError(ShowdownError.NoConnectionError)
                    trySend(Either.Error(ShowdownError.NoConnectionError))
                    close()
                }
                onclose = {
                    observer.onError(ShowdownError.NoConnectionError)
                    close()
                }
            }
            awaitClose {
                println("Closed")
            }
        }
    }

    private fun getPath(path: String): PATHS {
        return PATHS.values().find { it.path == path } ?: PATHS.EMPTY
    }

    private fun <T> fromJson(json: String): T? {
        return JSON.parse<T>(json)
    }

    private inline fun <reified T> decodeFromString(json: String): T? = try {
        Json.decodeFromString<T>(json)
    } catch (ex: Exception) {
        null
    }

    private fun onMessage(messageEvent: MessageEvent) {

        val json = messageEvent.data.toString()
        var response: Response? = null
        try {
            response = Json.decodeFromString<Response>(json)
        } catch (ex: Exception) {
            println("onMessage$ex     $json")
        }

        response?.let { it ->
            when (val path = getPath(it.path)) {
                PATHS.SPECTATORPATH -> {
                    fromJson<Boolean>(response.body)?.let {
                        observer.onSpectatorStatusChanged(it)
                    }
                }

                PATHS.ROOMCONFIGUPDATE -> {
                    decodeFromString<ClientGameConfig>(it.body)?.let {
                        observer.onConfigUpdated(it)
                    }
                }

                PATHS.MESSAGE -> {
                    observer.onMessageEvent(response.body)
                }

                PATHS.ERROR -> {
                    decodeFromString<ShowdownError>(it.body)?.let {
                        observer.onError(it)
                    }

                }

                PATHS.STATECHANGED -> {
                    decodeFromString<GameState>(it.body)?.let {
                        observer.onGameStateChanged(it)
                    }
                }

                else -> {
                    // println("DOnt Care about $path")
                }
            }
        }


    }

    fun sendMessage(message: String) {
        socket?.send(message)
    }
}