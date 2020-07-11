package de.jensklingenberg.showdown.server.game

import de.jensklingenberg.showdown.model.*
import de.jensklingenberg.showdown.server.model.Room
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

    private val gameMap = mutableMapOf<String, ServerGame>()

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
        GlobalScope.launch {
            sendMessage(memberId, "HALLO")
        }
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
        room: Room
    ) {
        println("Receiver ROOM:" + room.name + " PW: " + room.password)
        var gameSource = gameMap[room.name]
        val playerExist = playersSessions.containsKey(sessionId)
        val request: Request = fromJson<Request>(command) ?: Request("", "")

        when (request.path) {
            SETROOMPASSSWORDPATH -> {
                gameSource?.changePassword(request.body)
            }
            SHOWVOTESPATH -> {
                gameSource?.showVotes(sessionId)
            }
            RESTARTPATH -> {
                gameSource?.restart()
            }
            VOTEPATH -> {
                val id = request.body.toInt()
                gameSource?.onPlayerVoted(sessionId, id)

            }
            CHNAGECONFIGPATH -> {
                fromJson<ClientGameConfig>(request.body)?.let {config->
                    gameSource?.changeConfig(config)
                }

            }
            JOINROOMPATH -> {
                fromJson<JoinGame>(request.body)?.let {joinGame->
                    if (gameMap.none { it.key == room.name }) {
                        gameSource = createNewRoom(room.name)
                    }

                    if (joinGame.roomPassword == gameSource?.gameConfig?.room?.password) {
                        gameSource?.playerJoined(Player(sessionId, joinGame.playerName))
                    } else {
                        sendTo(sessionId, ServerResponse.ErrorEvent(ShowdownError.NotAuthorizedError()).toJson())
                    }
                }

            }

        }


    }

    fun sendBroadcast(data: String) {
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

    override fun createNewRoom(roomName: String): ServerGame {
        val game = ServerGame(this, getDefaultConfig(roomName))

        gameMap.putIfAbsent(roomName, game)

        println("CREATE ROOM " + roomName)
        return game
    }

    override fun closeRoom(roomName: String) {
        println("CloseRoom" + roomName)
        gameMap.remove(roomName)
    }

    override fun removeMember(sessionId: String) {
        playersSessions.remove(sessionId)
        members.remove(sessionId)
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
