package showdown.web.network

import com.badoo.reaktive.completable.completable
import de.jensklingenberg.showdown.model.*
import org.w3c.dom.CloseEvent
import org.w3c.dom.MessageEvent
import org.w3c.dom.WebSocket
import org.w3c.dom.events.Event

class GameApiClient {

    private var socket: WebSocket? = null

    private lateinit var observer: NetworkApiObserver

    fun start(observer: NetworkApiObserver) = completable {
         this.observer = observer
         socket = org.w3c.dom.WebSocket(NetworkPreferences().websocketUrl())

         socket?.onopen = { event: Event ->
             it.onComplete()
         }

         socket?.onmessage = { event: Event ->
             onMessage((event as MessageEvent))
         }

         socket?.onerror = { event: Event ->
             observer.onError(ServerResponse.ErrorEvent(ShowdownError.NoConnectionError()))
             it.onError(Throwable("NO Connection"))
         }
         socket?.onclose = { event: Event ->
             val even = event as CloseEvent
             observer.onError(ServerResponse.ErrorEvent(ShowdownError.NoConnectionError()))
         }
     }

    private fun onMessage(messageEvent: MessageEvent) {
        console.log("ONMESSAGE: "+messageEvent.data.toString())

        when(getWebsocketType(messageEvent.data.toString())){
            WebSocketResourceType.GameState -> {
                val resource = JSON.parse<WebsocketResource<MyGameState>>(messageEvent.data.toString())
                val my = resource.data
                when (my?.enGameState) {
                    EnGameState.NOTSTARTED -> {

                    }
                    EnGameState.STARTED -> {

                    }
                    EnGameState.MEMBERSUDPATE -> {


                    }
                    EnGameState.SHOWVOTES -> {

                    }
                    null -> {

                    }
                }
                console.log("EVENT: " + resource.data)

            }
            WebSocketResourceType.Notification -> {

            }
            WebSocketResourceType.MESSAGE -> {

            }
            WebSocketResourceType.UNKNOWN -> {

            }
        }

        when (val type = getServerResponse(messageEvent.data.toString())) {
            is ServerResponse.PlayerEvent -> {

            }
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