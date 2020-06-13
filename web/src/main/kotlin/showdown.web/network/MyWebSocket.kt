package challenge.network

import showdown.web.network.Websocket
import org.w3c.dom.MessageEvent
import org.w3c.dom.WebSocket

import org.w3c.dom.events.Event
import org.w3c.dom.events.EventListener

class MyWebSocket(url: String, override val listener: WebSocketListener) : Websocket {


    private val webSocket = WebSocket(url)


    init {
        webSocket.onmessage = { event: Event ->
            listener.onMessage((event as MessageEvent))
        }

        webSocket.onerror = { event: Event ->
            listener.onError((event))
        }

        webSocket.onclose = { event: Event ->

            listener.onClose((event))
        }
        webSocket.onopen = { event: Event ->
            listener.onOpen((event))
        }

        webSocket.addEventListener("dd", EventListener { })

    }


    override fun close() {

        webSocket.close()

    }


    override fun send(message: String) {
        webSocket.send(message)

    }

    interface WebSocketListener {
        fun onMessage(messageEvent: MessageEvent)
        fun onError(messageEvent: Event)
        fun onClose(messageEvent: Event)
        fun onOpen(event: Event)

    }


}