package showdown.web.game

import de.jensklingenberg.showdown.model.*
import org.w3c.dom.MessageEvent
import org.w3c.dom.WebSocket
import org.w3c.dom.events.Event
import showdown.web.network.NetworkApiObserver
import showdown.web.network.NetworkPreferences

class GameApiHandler {

    private var socket: WebSocket? = null

    private lateinit var observer: NetworkApiObserver

    fun start(observer: NetworkApiObserver) {
        this.observer = observer
        socket = org.w3c.dom.WebSocket(NetworkPreferences().websocketUrl())

        socket?.onmessage = { event: Event ->
            onMessage((event as MessageEvent))
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