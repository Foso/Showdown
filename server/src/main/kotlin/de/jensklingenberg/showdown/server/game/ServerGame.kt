package de.jensklingenberg.showdown.server.game


import com.soywiz.klock.DateTime
import com.squareup.moshi.Moshi
import de.jensklingenberg.showdown.model.*
import de.jensklingenberg.showdown.model.api.clientrequest.NewGameConfig
import de.jensklingenberg.showdown.server.common.toJson
import de.jensklingenberg.showdown.server.model.Player
import de.jensklingenberg.showdown.server.model.ServerConfig
import de.jensklingenberg.showdown.server.model.Vote
import de.jensklingenberg.showdown.server.model.toClient

/**
 * This class contains the state/logic of a game
 * [server] that will handle a responses to the clients
 * [gameConfig] the config for a game
 */
class ServerGame(private val server: GameServer, var gameConfig: ServerConfig) {

    private val playerList = arrayListOf<Player>()
    private var gameState: GameState = GameState.NotStarted
    private val inactivePlayerIds = mutableListOf<String>()
    private val moshi = Moshi.Builder().build()

    fun changePassword(sessionId: String, password: String) {
        val newRoomData = gameConfig.room.copy(password = password)
        gameConfig = gameConfig.copy(room = newRoomData)

        val response = Response(
            PATHS.MESSAGE.path,
            if (password.isEmpty()) {
                "Room password was removed by: " + playerList.find { it.sessionId == sessionId }?.name
            } else {
                "Room password was set by: " + playerList.find { it.sessionId == sessionId }?.name
            }
        )

        val websocketResource = WebsocketResource(WebSocketResourceType.RESPONSE, response)
        sendBroadcast(websocketResource.toJson())
        sendRoomConfigUpdate(gameConfig.toClient())
    }

    fun setAutoReveal(autoReveal: Boolean) {
        gameConfig = gameConfig.copy(autoReveal = autoReveal)
        sendRoomConfigUpdate(gameConfig.toClient())
    }

    private fun sendRoomConfigUpdate(clientGameConfig: ClientGameConfig) {
        val configJson = moshi.toJson(clientGameConfig)
        val response = Response(PATHS.ROOMCONFIGUPDATE.path, configJson)
        val websocketResource = WebsocketResource(WebSocketResourceType.RESPONSE, response)

        sendBroadcast(websocketResource.toJson())
    }

    fun restart() {
        gameState =
            GameState.Started(gameConfig.toClient().copy(createdAt = DateTime.now().unixMillisDouble.toString()))
        inactivePlayerIds.forEach { inactivePlayer ->
            playerList.removeIf { it.sessionId == inactivePlayer }
        }
        inactivePlayerIds.clear()
        clearVotes()
        sendPlayerList()
        sendGameStateChanged(gameState)
    }

    private fun clearVotes() {
        playerList.replaceAll { it.copy(vote = null) }
    }

    fun playerJoined(player: Player) {

        if (playerList.any { it.sessionId == player.sessionId }) {
            onPlayerRejoined(player.sessionId, player.name)
        } else {
            if (gameState == GameState.NotStarted) {
                gameState = GameState.Started(gameConfig.toClient())
            }
            playerList.add(player)
            server.onPlayerAdded(player.sessionId, player)

            sendPlayerList()
            sendGameStateChanged(player.sessionId, gameState)
        }
    }

    private fun onPlayerRejoined(sessionId: String, name: String) {
        //Set new player name
        playerList.replaceAll { player ->
            if (player.sessionId == sessionId) {
                player.copy(name = name)
            } else {
                player
            }
        }
        inactivePlayerIds.removeIf { it == sessionId }
        sendPlayerList()
        sendGameStateChanged(sessionId, gameState)
    }

    fun changeConfig(clientGameConfig: NewGameConfig) {
        this.gameConfig = gameConfig.copy(
            createdAt = DateTime.now().unixMillisLong.toString(),
            autoReveal = clientGameConfig.autoReveal,
            voteOptions = clientGameConfig.voteOptions
        )
        clearVotes()
        gameState = GameState.Started(this.gameConfig.toClient())
        sendGameStateChanged(gameState)
        sendPlayerList()
    }


    fun onPlayerVoted(sessionId: String, voteId: Int) {
        if (gameState is GameState.ShowVotes) {
            return
        }
        playerList.replaceAll { player ->
            if (player.sessionId == sessionId) {
                player.copy(vote = Vote(voteId, sessionId))
            } else {
                player
            }
        }

        sendPlayerList()
        if (gameConfig.autoReveal) {
            if (playerList.all { it.vote != null }) {
                sendVotes()
            }
        }
    }

    fun onPlayerLostConnection(sessionId: String) {
        playerList.find { it.sessionId == sessionId }?.let {
            inactivePlayerIds.add(it.sessionId)
        }

        sendPlayerList()
        closeRoomIfEmpty()
    }

    //When all players are inactive, delete the room
    private fun closeRoomIfEmpty() {
        if (playerList.size == inactivePlayerIds.size) {
            playerList.forEach {
                server.removePlayer(it.sessionId)
            }
            server.closeRoom(gameConfig.room.name)
        }
    }

    private fun sendPlayerList() {
        val votesList = playerList.map { player ->
            val isInActive = inactivePlayerIds.any { it == player.sessionId }
            val voted = player.vote != null
            Member(player.name, voted, isConnected = !isInActive)
        }.sortedBy { it.voted }
        sendGameStateChanged(GameState.PlayerListUpdate(votesList))
    }

    fun showVotes(sessionId: String) {
        sendVotes()
    }

    private fun sendVotes() {
        if (playerList.none { it.vote != null }) {
            return
        }
        val newVotes = playerList.filter { it.vote != null }.map {
            val voteText = gameConfig.voteOptions[it.vote!!.voteId]
            Result(voteText, it.name)
        }.sortedBy { it.optionName }

        gameState = GameState.ShowVotes(newVotes)
        sendGameStateChanged(gameState)
    }


    private fun sendGameStateChanged(sessionId: String, gameState: GameState) {
        val json = ServerResponse.GameStateChanged(gameState).toJson()
        server.sendData(sessionId, json)
    }

    private fun sendGameStateChanged(gameState: GameState) {
        sendBroadcast(ServerResponse.GameStateChanged(gameState).toJson())
    }

    private fun sendBroadcast(json: String) {
        playerList.forEach {
            server.sendData(it.sessionId, json)
        }
    }

}

