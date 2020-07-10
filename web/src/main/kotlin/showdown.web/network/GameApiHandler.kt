package showdown.web.network

import com.badoo.reaktive.completable.Completable
import com.badoo.reaktive.completable.completable
import de.jensklingenberg.showdown.model.*
import org.w3c.dom.CloseEvent
import org.w3c.dom.MessageEvent
import org.w3c.dom.WebSocket
import org.w3c.dom.events.Event

class GameApiHandler {

    private var socket: WebSocket? = null

    private lateinit var observer: NetworkApiObserver

    fun start(observer: NetworkApiObserver) = completable {
         this.observer = observer
         socket = org.w3c.dom.WebSocket(NetworkPreferences().websocketUrl())

         socket?.onopen = { event: Event ->
             console.log("ONOPEN")
             it.onComplete()
         }

         socket?.onmessage = { event: Event ->

             onMessage((event as MessageEvent))
         }

         socket?.onerror = { event: Event ->
             console.log("ONERROR "+event.target.toString())
             observer.onError(ServerResponse.ErrorEvent(ShowdownError.NoConnectionError()))
             it.onError(Throwable("NO Connection"))
         }
         socket?.onclose = { event: Event ->
             val even = event as CloseEvent
             observer.onError(ServerResponse.ErrorEvent(ShowdownError.NoConnectionError()))
             console.log("onclose "+even.code)
         }
     }

    private fun onMessage(messageEvent: MessageEvent) {
        console.log("ONMESSAGE"+messageEvent.data.toString())
        when (val type = getServerResponse(messageEvent.data.toString())) {
            is ServerResponse.PlayerEvent -> {
                observer.onPlayerEventChanged(type.playerResponseEvent)
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