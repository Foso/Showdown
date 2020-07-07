package showdown.web.network

import de.jensklingenberg.showdown.model.*
import org.w3c.dom.CloseEvent
import org.w3c.dom.MessageEvent
import org.w3c.dom.WebSocket
import org.w3c.dom.events.Event

class GameApiHandler {

    private var socket: WebSocket? = null

    private lateinit var observer: NetworkApiObserver

    fun start(observer: NetworkApiObserver) {
        this.observer = observer
        socket = org.w3c.dom.WebSocket(NetworkPreferences().websocketUrl())

        socket?.onopen = { event: Event ->
            console.log("ONOPEN")
        }

        socket?.onmessage = { event: Event ->
            onMessage((event as MessageEvent))
        }

        socket?.onerror = { event: Event ->
           console.log("ONERROR "+event.target.toString())
            observer.onError(ServerResponse.ErrorEvent(ShowdownError.NoConnectionError()))
        }
        socket?.onclose = { event: Event ->
            val even = event as CloseEvent
            console.log("onclose "+even.code)
        }
    }

    private fun onMessage(messageEvent: MessageEvent) {

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