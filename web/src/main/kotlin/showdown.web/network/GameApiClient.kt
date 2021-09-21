package showdown.web.network

import com.badoo.reaktive.completable.completable
import de.jensklingenberg.showdown.model.ClientGameConfig
import de.jensklingenberg.showdown.model.PATHS
import de.jensklingenberg.showdown.model.Response
import de.jensklingenberg.showdown.model.ServerResponse
import de.jensklingenberg.showdown.model.ShowdownError
import de.jensklingenberg.showdown.model.WebSocketResourceType
import de.jensklingenberg.showdown.model.WebsocketResource
import de.jensklingenberg.showdown.model.getServerResponse
import de.jensklingenberg.showdown.model.getWebsocketType
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
        socket = org.w3c.dom.WebSocket(NetworkPreferences().websocketUrl())

        socket?.apply {
            onopen = {
                emitter.onComplete()
            }
            onmessage = { event: Event ->
                onMessage((event as MessageEvent))
            }

            onerror = {
                observer.onError(ServerResponse.ErrorEvent(ShowdownError.NoConnectionError()))
                emitter.onError(Throwable("NO Connection"))
            }
            onclose = {
                observer.onError(ServerResponse.ErrorEvent(ShowdownError.NoConnectionError()))
            }
        }
    }


    private fun getPath(path: String): PATHS {
        println("getPath:" +path)

        val tt = PATHS.values().find { it.path == path } ?: PATHS.EMPTY
        println("getPath:" +tt)
        return tt
    }

    private fun <T> fromJson(json: String): T? {
        val tt = JSON.parse<T>(json)
        println("tt $tt")
        return tt
    }

    private fun onMessage(messageEvent: MessageEvent) {
        println("ONMessa: "+messageEvent.data.toString())
        val json = messageEvent.data.toString()

        when (getWebsocketType(json)) {

            WebSocketResourceType.RESPONSE -> {
                println("WebSocketResourceType.RESPONSE $json")

                val resource2 = Json.decodeFromString<WebsocketResource<Response>>(json)//JSON.parse<WebsocketResource<Response>>(json)
                val response =                  resource2.data!!//JSON.parse<WebsocketResource<Response>>(json)
                println("resource:: ${response.path}")

                when (val path =getPath(response.path)) {
                    PATHS.MESSAGE -> {
                        observer.onMessageEvent(response.body)
                        // console.log("RESPONSE"+response.body)
                    }

                    PATHS.ROOMCONFIGUPDATE -> {
                        fromJson<ClientGameConfig>(response.body)?.let {
                            observer.onConfigUpdated(it)
                        }
                    }


                    PATHS.SPECTATORPATH -> {
                        fromJson<Boolean>(response.body)?.let {
                            println("Repo: $it")
                            observer.onSpectatorStatusChanged(it )
                        }
                    }
                    PATHS.SETROOMPASSSWORDPATH, PATHS.EMPTY, PATHS.ROOMUPDATE -> {
                        println("PATH: $path $json")
                    }
                }
            }
        }

        when (val type = getServerResponse(json)) {

            is ServerResponse.GameStateChanged -> {
                observer.onGameStateChanged(type.state)
            }
            is ServerResponse.ErrorEvent -> {
                println("SERv  ${type.error}" )
                observer.onError(type)
            }

        }


    }

    fun sendMessage(message: String) {
        println("sendMessageToServer: " + message)
        socket?.send(message)
    }
}