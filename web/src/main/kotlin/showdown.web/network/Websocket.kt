package showdown.web.network

import challenge.network.MyWebSocket
import org.w3c.dom.MessageEvent
import org.w3c.dom.events.Event

interface Websocket {
    val listener: MyWebSocket.WebSocketListener

    fun send(message: String)

    fun close()

    interface WebSocketListener {
        fun onMessage(messageEvent: MessageEvent)
        fun onError(messageEvent: Event)
    }

}