package de.jensklingenberg.showdown.server.game

import de.jensklingenberg.showdown.model.*
import io.ktor.http.cio.websocket.CloseReason
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.WebSocketSession
import io.ktor.http.cio.websocket.close
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.ClosedSendChannelException
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArrayList


/**
 * Class in charge of the logic of the chat server.
 * It contains handlers to events and commands to send messages to specific users in the server.
 */
class ShowdownServer : GameServer {

    private val gameMap = mutableMapOf<String, Game>()

    private val playersSessions = ConcurrentHashMap<String, Any>()

    /**
     * Associates a session-id to a set of websockets.
     * Since a browser is able to open several tabs and windows with the same cookies and thus the same session.
     * There might be several opened sockets for the same client.
     */
    private val members = ConcurrentHashMap<String, MutableList<WebSocketSession>>()

    /**
     * Handles that a member identified with a session id and a socket joined.
     */
    fun memberJoin(memberId: String, socket: WebSocketSession) {
        // Checks if this user is already registered in the server and gives him/her a temporal name if required.

        // Associates this socket to the member id.
        // Since iteration is likely to happen more frequently than adding new items,
        // we use a `CopyOnWriteArrayList`.
        // We could also control how many sockets we would allow per client here before appending it.
        // But since this is a sample we are not doing it.
        val socketList = members.computeIfAbsent(memberId) { CopyOnWriteArrayList<WebSocketSession>() }
        socketList.add(socket)
    }


    /**
     * Handles that a [sessionId] with a specific [socket] left the server.
     */
    fun memberLeft(sessionId: String, socket: WebSocketSession) {
        // Removes the socket connection for this member
        val connections = members[sessionId]
        connections?.remove(socket)

        // If no more sockets are connected for this member, let's remove it from the server
        // and notify the rest of the users about this event.
        if (connections != null && connections.isEmpty()) {

            gameMap.forEach {
                it.value.onPlayerLeft(sessionId)
            }

            println("Member left: ")

        }
    }

    /**
     * We received a message. Let's process it.
     */
    suspend fun receivedMessage(
        sessionId: String,
        command: String,
        roomName: String,
        password: String
    ) {

        println("Receiver ROOM:" + roomName + " PW: " + password)
        var gameSource = gameMap[roomName]
        val playerExist = playersSessions.containsKey(sessionId)

        when (val type = getServerRequest(command)) {
            !is ServerRequest.PlayerRequest -> {
                if (!playerExist) {
                    return
                }
            }
            else -> {
                when (val event = type.playerRequestEvent) {

                    is PlayerRequestEvent.JoinGameRequest -> {
                        if (gameMap.none { it.key == roomName }) {
                            gameSource = createNewRoom(roomName)
                        }

                        if (event.password == "geheim") {
                            if (!playersSessions.containsKey(sessionId)) {
                                gameSource?.addPlayer( Player(sessionId, event.playerName))
                            } else {
                                gameSource?.onPlayerRejoined(sessionId, event.playerName)
                            }
                        } else {
                            sendTo(sessionId, ServerResponse.ErrorEvent(ShowdownError.NotAuthorizedError()).toJson())
                        }
                    }

                    is PlayerRequestEvent.ShowVotes -> {
                        if (!playerExist) {
                            return
                        }
                        gameSource?.showVotes(sessionId)
                    }
                    is PlayerRequestEvent.Voted -> {
                        gameSource?.onPlayerVoted(sessionId, event.voteId)
                    }
                    is PlayerRequestEvent.RestartRequest -> {
                        gameSource?.restart()
                    }
                    is PlayerRequestEvent.ChangeConfig -> {
                        gameSource?.changeConfig(event.clientGameConfig)
                    }
                }
            }
        }
    }

    override fun sendBroadcast(data: String) {
        GlobalScope.launch {
            broadcast(data)
        }
    }

    override fun sendData(sessionId: String, data: String) {
        val playerSessionId = sessionId//playersSessions.filter { it.value.sessionId == sessionId }.keys.first()

        GlobalScope.launch {
            sendMessage(playerSessionId, data)
        }
    }

    override fun onPlayerAdded(sessionId: String, player: Player) {
        playersSessions[sessionId] = player
    }

    override fun createNewRoom(roomName: String): Game {
        val game = Game(this, getDefaultConfig())

        gameMap.putIfAbsent(roomName, game)

        println("CREATE ROOM " + roomName)
        return game
    }

    override fun closeRoom() {

    }

    private suspend fun sendTo(recipient: String, message: String) {
        members[recipient]?.send(Frame.Text(message))
    }

    suspend fun sendMessage(recipient: String, message: String) {
        members[recipient]?.send(Frame.Text("$message"))
    }


    /**
     * Sends a [message] to all the members in the server, including all the connections per member.
     */
    private suspend fun broadcast(message: String) {
        members.values.forEach { socket ->
            socket.send(Frame.Text(message))
        }
    }


    /**
     * Sends a [message] to a list of [this] [WebSocketSession].
     */
    private suspend fun List<WebSocketSession>.send(frame: Frame) {
        forEach {
            try {
                it.send(frame.copy())
            } catch (t: Throwable) {
                try {
                    it.close(CloseReason(CloseReason.Codes.PROTOCOL_ERROR, ""))
                } catch (ignore: ClosedSendChannelException) {
                    // at some point it will get closed
                }
            }
        }
    }


}
