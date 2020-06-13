package de.jensklingenberg.showdown.server.server

import de.jensklingenberg.showdown.model.*
import de.jensklingenberg.showdown.server.game.ShowdownContract
import de.jensklingenberg.showdown.server.game.ShowdownPresenter
import io.ktor.http.cio.websocket.CloseReason
import io.ktor.http.cio.websocket.Frame
import io.ktor.http.cio.websocket.WebSocketSession
import io.ktor.http.cio.websocket.close
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ClosedSendChannelException
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicInteger


/**
 * Class in charge of the logic of the chat server.
 * It contains handlers to events and commands to send messages to specific users in the server.
 */
class ShowdownServer : ShowdownContract.RpsGameServer {

    private val gamePresenter: ShowdownContract.Presenter =
        ShowdownPresenter(this)

    /**
     * Atomic counter used to get unique user-names based on the maxiumum users the server had.
     */
    private val usersCounter = AtomicInteger()

    /**
     * A concurrent map associating session IDs to user names.
     */
    private val memberNames = ConcurrentHashMap<String, String>()


    private val playersSessions = ConcurrentHashMap<String, Player>()

    /**
     * Associates a session-id to a set of websockets.
     * Since a browser is able to open several tabs and windows with the same cookies and thus the same session.
     * There might be several opened sockets for the same client.
     */
    private val members = ConcurrentHashMap<String, MutableList<WebSocketSession>>()

    /**
     * A list of the lastest messages sent to the server, so new members can have a bit context of what
     * other people was talking about before joining.
     */
    private val lastMessages = LinkedList<String>()

    /**
     * Handles that a member identified with a session id and a socket joined.
     */
    suspend fun memberJoin(memberId: String, socket: WebSocketSession) {
        // Checks if this user is already registered in the server and gives him/her a temporal name if required.
        val name = memberNames.computeIfAbsent(memberId) { "user${usersCounter.incrementAndGet()}" }

        // Associates this socket to the member id.
        // Since iteration is likely to happen more frequently than adding new items,
        // we use a `CopyOnWriteArrayList`.
        // We could also control how many sockets we would allow per client here before appending it.
        // But since this is a sample we are not doing it.
        val socketList = members.computeIfAbsent(memberId) { CopyOnWriteArrayList<WebSocketSession>() }
        socketList.add(socket)

        // Only when joining the first socket for a member notifies the rest of the users.
        if (socketList.size == 1) {
            val playerId = members.keys.indexOf(memberId)

            println("Member joined: $name.")
            broadcast("server", "Member joined: $name.")
        }

        // Sends the user the latest messages from this server to let the member have a bit context.
        val messages = synchronized(lastMessages) { lastMessages.toList() }
        for (message in messages) {
            socket.send(Frame.Text("HALLo"))
        }
    }

    /**
     * Handles a [member] idenitified by its session id renaming [to] a specific name.
     */
    suspend fun memberRenamed(member: String, to: String) {
        // Re-sets the member name.
        val oldName = memberNames.put(member, to) ?: member
        // Notifies everyone in the server about this change.
        broadcast("server", "Member renamed from $oldName to $to")
    }

    /**
     * Handles that a [member] with a specific [socket] left the server.
     */
    suspend fun memberLeft(member: String, socket: WebSocketSession) {
        // Removes the socket connection for this member
        val connections = members[member]
        connections?.remove(socket)

        // If no more sockets are connected for this member, let's remove it from the server
        // and notify the rest of the users about this event.
        if (connections != null && connections.isEmpty()) {
            val name = memberNames.remove(member) ?: member
            broadcast("server", "Member left: $name.")
        }
    }

    /**
     * Handles the 'who' command by sending the member a list of all all members names in the server.
     */
    suspend fun who(sender: String) {
        members[sender]?.send(Frame.Text(memberNames.values.joinToString(prefix = "[server::who] ")))
    }

    /**
     * Handles the 'help' command by sending the member a list of available commands.
     */
    suspend fun help(sender: String) {
        members[sender]?.send(Frame.Text("[server::help] Possible commands are: /user, /help and /who"))
    }

    /**
     * Handles sending to a [recipient] from a [sender] a [message].
     *
     * Both [recipient] and [sender] are identified by its session-id.
     */
    suspend fun sendTo(recipient: String, sender: String, message: String) {
        members[recipient]?.send(Frame.Text("[$sender] $message"))
    }

    suspend fun sendTo(recipient: String, message: String) {
        members[recipient]?.send(Frame.Text(message))
    }

    suspend fun sendMessage(recipient: String, message: String) {
        members[recipient]?.send(Frame.Text("$message"))
    }

    /**
     * Handles a [message] sent from a [sender] by notifying the rest of the users.
     */
    suspend fun message(sender: String, message: String) {
        // Pre-format the message to be send, to prevent doing it for all the users or connected sockets.
        val name = memberNames[sender] ?: sender
        val formatted = "[$name] $message"

        // Sends this pre-formatted message to all the members in the server.
        broadcast(formatted)

        // Appends the message to the list of [lastMessages] and caps that collection to 100 items to prevent
        // growing too much.
        synchronized(lastMessages) {
            lastMessages.add(formatted)
            if (lastMessages.size > 100) {
                lastMessages.removeFirst()
            }
        }
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
     * Sends a [message] coming from a [sender] to all the members in the server, including all the connections per member.
     */
    private suspend fun broadcast(sender: String, message: String) {
        val name = memberNames[sender] ?: sender
        broadcast("[$name] $message")
    }

    /**
     * Sends a [message] to a list of [this] [WebSocketSession].
     */
    suspend fun List<WebSocketSession>.send(frame: Frame) {
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

    /**
     * We received a message. Let's process it.
     */
    suspend fun receivedMessage(sessionId: String, command: String) {
        val playerId = members.keys.indexOf(sessionId)
        val commandType = getServerCommandType(command)

        when (commandType) {

            ServerRequestTypes.RESET -> {
                gamePresenter.onReset()
                members.clear()
            }

            ServerRequestTypes.PLAYEREVENT -> {
                val cmd = ServerCommandParser.getPlayerRequest(command)
                when (val event = cmd.playerRequestEvent) {

                    is PlayerRequestEvent.JoinGameRequest -> {
                        if (!playersSessions.containsKey(sessionId)) {
                            gamePresenter.onAddPlayer(sessionId,event.playerName)
                        }else{
                            gamePresenter.onPlayerRejoined(sessionId,event.playerName)
                        }
                    }

                    is PlayerRequestEvent.StartGame -> {
                        //gamePresenter.onPlayerReady(playerId)
                    }
                    is PlayerRequestEvent.ShowVotes -> {
                        gamePresenter.showVotes()
                    }
                    is PlayerRequestEvent.Voted -> {
                        gamePresenter.onPlayerVoted(playerId,event.cardId)
                    }
                }
            }
            ServerRequestTypes.MESSAGE -> TODO()
            ServerRequestTypes.ERROR -> TODO()
            ServerRequestTypes.UNKNOWN -> TODO()
            null -> TODO()
        }
        // game.makeMove(playerId,)
        // We are going to handle commands (text starting with '/') and normal messages
        when {
            // The command `who` responds the user about all the member names connected to the user.
            command.startsWith("/who") -> who(sessionId)
            // The command `user` allows the user to set its name.
            command.startsWith("/user") -> {
                // We strip the command part to get the rest of the parameters.
                // In this case the only parameter is the user's newName.
                val newName = command.removePrefix("/user").trim()
                // We verify that it is a valid name (in terms of length) to prevent abusing
                when {
                    newName.isEmpty() -> sendTo(sessionId, "server::help", "/user [newName]")
                    newName.length > 50 -> sendTo(
                        sessionId,
                        "server::help",
                        "new name is too long: 50 characters limit"
                    )
                    else -> memberRenamed(sessionId, newName)
                }
            }
            // The command 'help' allows users to get a list of available commands.
            command.startsWith("/help") -> help(sessionId)
            // If no commands matched at this point, we notify about it.
            command.startsWith("/") -> sendTo(
                sessionId,
                "server::help",
                "Unknown command ${command.takeWhile { !it.isWhitespace() }}"
            )
            // Handle a normal message.
            else -> {
                //message(id, command)
            }
        }
    }

    override fun sendBroadcast(data: String) {
        GlobalScope.launch {
            broadcast(data)
        }
    }

    override fun sendData(playerId: Int, data: String) {
        val sessionId = playersSessions.filter { it.value.id == playerId }.keys.first()

        GlobalScope.launch {
            sendMessage(sessionId, data)
        }
    }

    override fun onPlayerAdded(sessionId: String, player: Player) {
        playersSessions[sessionId] = player

    }


}
