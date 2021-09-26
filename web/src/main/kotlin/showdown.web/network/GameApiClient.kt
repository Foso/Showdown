package showdown.web.network

import com.badoo.reaktive.completable.completable
import de.jensklingenberg.showdown.model.ClientGameConfig
import de.jensklingenberg.showdown.model.GameState
import de.jensklingenberg.showdown.model.PATHS
import de.jensklingenberg.showdown.model.Response

import de.jensklingenberg.showdown.model.ShowdownError

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.w3c.dom.MessageEvent
import org.w3c.dom.WebSocket
import org.w3c.dom.events.Event

class GameApiClient {

    private var socket: WebSocket? = null

    private lateinit var observer: NetworkApiObserver

    fun start(observer: NetworkApiObserver) = completable { emitter ->
        this.observer = observer
        socket = WebSocket(NetworkPreferences().websocketUrl())

        socket?.apply {
            onopen = {
                emitter.onComplete()
            }
            onmessage = { event: Event ->
                onMessage((event as MessageEvent))
            }

            onerror = {
                observer.onError(ShowdownError.NoConnectionError)
                emitter.onError(Throwable("NO Connection"))
            }
            onclose = {
                observer.onError(ShowdownError.NoConnectionError)
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
        println("TT" + ex + "     " + json)
        null
    }

    private fun onMessage(messageEvent: MessageEvent) {

        val json = messageEvent.data.toString()
        println("onMessage $json")
        var response: Response? = null
        try {
            response = Json.decodeFromString<Response>(json)
        } catch (ex: Exception) {
            println("TT" + ex + "     " + json)
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
                    // console.log("RESPONSE"+response.body)
                }

                PATHS.SETROOMPASSSWORDPATH, PATHS.EMPTY -> {
                    println("PATH: $path $json")
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
                    println("DOnt Care about $path")
                }
            }
        }


    }

    fun sendMessage(message: String) {
        println("sendMessageToServer: " + message)
        socket?.send(message)
    }
}