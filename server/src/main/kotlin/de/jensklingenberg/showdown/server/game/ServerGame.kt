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


class ServerGame(private val server: GameServer, var gameConfig: ServerConfig) {

    private val playerList = arrayListOf<Player>()
    private var gameState: GameState = GameState.NotStarted
    private val inactivePlayerIds = mutableListOf<String>()
    val moshi = Moshi.Builder().build()

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


        val configJson = moshi.toJson(gameConfig.toClient())
        val response2 = Response(PATHS.ROOMCONFIGUPDATE.path, configJson)
        val websocketResource2 = WebsocketResource(WebSocketResourceType.RESPONSE, response2)

        sendBroadcast(websocketResource2.toJson())
    }

    fun setAutoReveal(autoReveal: Boolean) {
        gameConfig = gameConfig.copy(autoReveal = autoReveal)
        val tt = moshi.toJson(gameConfig.toClient())
        val response2 = Response(PATHS.ROOMCONFIGUPDATE.path, tt)
        val websocketResource2 = WebsocketResource(WebSocketResourceType.RESPONSE, response2)

        sendBroadcast(websocketResource2.toJson())
    }

    fun restart() {
        gameState =
            GameState.Started(gameConfig.toClient().copy(createdAt = DateTime.now().unixMillisDouble.toString()))
        inactivePlayerIds.forEach { inactivePlayer ->
            playerList.removeIf { it.sessionId == inactivePlayer }
        }
        inactivePlayerIds.clear()
        clearVotes()
        sendPlayers()
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

            sendPlayers()
            sendGameStateChanged(player.sessionId, gameState)
        }
    }

    private fun onPlayerRejoined(sessionId: String, name: String) {
        //Set new name
        playerList.replaceAll { player ->
            if (player.sessionId == sessionId) {
                player.copy(name = name)
            } else {
                player
            }
        }
        inactivePlayerIds.removeIf { it == sessionId }
        sendPlayers()
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
        sendPlayers()
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

        sendPlayers()
        if (gameConfig.autoReveal) {
            if (playerList.all { it.vote != null }) {
                showVotes()
            }
        }
    }

    fun onPlayerLeft(sessionId: String) {
        playerList.find { it.sessionId == sessionId }?.let {
            inactivePlayerIds.add(it.sessionId)
        }

        sendPlayers()
        closeRoomIfEmpty()
    }

    //When all players are inactive, delete the room
    private fun closeRoomIfEmpty() {
        if (playerList.size == inactivePlayerIds.size) {
            playerList.forEach {
                server.removeMember(it.sessionId)
            }
            server.closeRoom(gameConfig.room.name)
        }
    }

    private fun sendPlayers() {
        val votesList = playerList.map { player ->
            val isInActive = inactivePlayerIds.any { it == player.sessionId }
            val voted = player.vote != null
            Member(player.name, voted, isConnected = !isInActive)
        }.sortedBy { it.voted }
        sendGameStateChanged(GameState.PlayerListUpdate(votesList))

    }

    fun showVotes(sessionId: String) {
        showVotes()
    }

    private fun showVotes() {
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

