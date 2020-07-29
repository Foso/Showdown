package showdown.web.network

import com.badoo.reaktive.completable.completable
import de.jensklingenberg.showdown.model.*
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
            onopen = { event: Event ->
                emitter.onComplete()
            }
            onmessage = { event: Event ->
                onMessage((event as MessageEvent))
            }

            onerror = { event: Event ->
                observer.onError(ServerResponse.ErrorEvent(ShowdownError.NoConnectionError()))
                emitter.onError(Throwable("NO Connection"))
            }
            onclose = { event: Event ->
                observer.onError(ServerResponse.ErrorEvent(ShowdownError.NoConnectionError()))
            }
        }
    }


    fun getPath(path: String): PATHS {
        return PATHS.values().find { it.path == path } ?: PATHS.EMPTY
    }

    private fun <T> fromJson(json: String): T? {
        return JSON.parse<T>(json)
    }

    private fun onMessage(messageEvent: MessageEvent) {
        val json = messageEvent.data.toString()
        when (getWebsocketType(json)) {
            WebSocketResourceType.GameEvent, WebSocketResourceType.Notification, WebSocketResourceType.MESSAGE, WebSocketResourceType.UNKNOWN -> {

            }
            WebSocketResourceType.RESPONSE -> {
                val resource2 = JSON.parse<WebsocketResource<Response>>(json)
                val response = resource2.data!!
                when (getPath(response.path)) {
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
                            observer.onSpectatorStatusChanged(it)
                        }
                    }
                    PATHS.SETROOMPASSSWORDPATH, PATHS.EMPTY, PATHS.ROOMUPDATE -> {
                        //TODO()
                    }
                }
            }
        }

        when (val type = getServerResponse(json)) {

            is ServerResponse.GameStateChanged -> {
                observer.onGameStateChanged(type.state)
            }
            is ServerResponse.ErrorEvent -> {
                observer.onError(type)
            }

        }


    }

    fun sendMessage(message: String) {
        socket?.send(message)
    }
}