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

    fun get(my: GameEvent): de.jensklingenberg.showdown.model.GameEventType {
       return de.jensklingenberg.showdown.model.GameEventType.values()[my.eventId]
    }

    fun getPath(path: String): PATHS {
       return PATHS.values().find { it.path==path }?:PATHS.EMPTY
    }

    fun <T> fromJson(json:String): T? {
        return  JSON.parse<T>(json)
    }

    private fun onMessage(messageEvent: MessageEvent) {
        val json = messageEvent.data.toString()
        console.log("ONMESSAGE: " + json)

        when (getWebsocketType(json)) {
            WebSocketResourceType.GameEvent -> {
                val resource = JSON.parse<WebsocketResource<GameEvent>>(json)
                val my = resource.data!!
                when (val type = get(my)) {
                    GameEventType.Normal -> {
                        fromJson<WebsocketResource<MyGameEvent>>(json)?.let {

                        }
                        val resource2 = JSON.parse<WebsocketResource<MyGameEvent>>(json)
                        console.log("EVENT: " + resource2.data?.name)
                    }
                }
            }
            WebSocketResourceType.Notification -> {

            }
            WebSocketResourceType.MESSAGE -> {

            }
            WebSocketResourceType.UNKNOWN -> {

            }
            WebSocketResourceType.RESPONSE -> {
                val resource2 = JSON.parse<WebsocketResource<Response>>(json)
                val response = resource2.data!!
                when(val path = getPath(response.path)){
                    PATHS.MESSAGE->{
                        observer.onMessageEvent(response.body)
                        console.log("RESPONSE"+response.body)
                    }
                    PATHS.SETROOMPASSSWORDPATH -> {}
                    PATHS.ROOMCONFIGUPDATE -> {
                        fromJson<ClientGameConfig>(response.body)?.let {
                            observer.onConfigUpdated(it)
                        }
                    }
                    PATHS.EMPTY -> {}
                    else -> {

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