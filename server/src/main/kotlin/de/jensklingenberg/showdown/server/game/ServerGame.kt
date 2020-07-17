package de.jensklingenberg.showdown.server.game


import com.soywiz.klock.DateTime
import de.jensklingenberg.showdown.model.*
import de.jensklingenberg.showdown.server.model.*

fun getDefaultConfig(roomName: String) = ServerConfig(
    fibo, false, createdAt = DateTime.now().unixMillisDouble.toString(),
    room = Room(roomName, "")
)

class ServerGame(private val server: GameServer, var gameConfig: ServerConfig) {

    private val playerList = mutableListOf<Player>()
    private val votes = arrayListOf<Vote>()
    private var gameState: GameState = GameState.NotStarted
    private val inactivePlayers = mutableListOf<Player>()

    fun changePassword(password: String) {
        val newRoomData = gameConfig.room.copy(password = password)
        gameConfig = gameConfig.copy(room = newRoomData)
    }

    fun restart() {
        gameState =
            GameState.Started(gameConfig.toClient().copy(createdAt = DateTime.now().unixMillisDouble.toString()))
        inactivePlayers.forEach { inactivePlayer ->
            playerList.removeIf { it.sessionId == inactivePlayer.sessionId }
        }
        inactivePlayers.clear()
        votes.clear()
        sendPlayers()
        sendGameStateChanged(gameState)
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
            sendGameStateChanged(player.sessionId,gameState)

        }

    }

    private fun onPlayerRejoined(sessionId: String, name: String) {
        playerList.replaceAll {player->
            if (player.sessionId == sessionId) {
                player.copy(name = name)
            } else {
                player
            }
        }
        inactivePlayers.removeIf { it.sessionId == sessionId }
        sendPlayers()
        sendGameStateChanged(sessionId,gameState)
    }

    fun changeConfig(clientGameConfig: NewGameConfig) {
        this.gameConfig = gameConfig.copy(
            createdAt = DateTime.now().unixMillisLong.toString(),
            autoReveal = clientGameConfig.autoReveal,
            voteOptions = clientGameConfig.voteOptions
        )
        votes.clear()
        gameState = GameState.Started(this.gameConfig.toClient())
        sendGameStateChanged(gameState)
        sendPlayers()
    }


    fun onPlayerVoted(playerId: String, voteId: Int) {
        if (gameState is GameState.ShowVotes) {
            return
        }
        votes.removeIf { it.playerId == playerId }
        votes.add(Vote(voteId, playerId))
        sendPlayers()
        if (gameConfig.autoReveal) {
            if (votes.size == playerList.size) {
                showVotes()
            }
        }
    }

    fun onPlayerLeft(sessionId: String) {
        val findPlayer = playerList.find { it.sessionId == sessionId }
        findPlayer?.let {
            inactivePlayers.add(it)
        }

        sendPlayers()
        closeRoomIfEmpty()
    }

    //When all players are inactive, delete the room
    private fun closeRoomIfEmpty() {
        if (playerList.size == inactivePlayers.size) {
            playerList.forEach {
                server.removeMember(it.sessionId)
            }
            server.closeRoom(gameConfig.room.name)
        }
    }

    private fun sendPlayers() {
        val votesList = playerList.map { player ->
            val isInActive = inactivePlayers.any { it.sessionId == player.sessionId }
            val inActiveText = if (isInActive) {
                "(player left the game)"
            } else {
                ""
            }
            val voted = votes.any { it.playerId == player.sessionId }

            Member(player.name, inActiveText,voted,isConnected = !isInActive)
        }.sortedBy { it.voted }
        sendGameStateChanged(GameState.MembersUpdate(votesList))

    }

    fun showVotes(sessionId: String) {
        showVotes()
    }

    private fun showVotes() {
        if(votes.isEmpty()){
            return
        }
        val newresults = votes.map {
            val voterId = it.playerId
            val voteText = gameConfig.voteOptions[it.voteId]
            val voterName = playerList.find { it.sessionId == voterId }?.name ?: ""
            Result(voteText, voterName)
        }.sortedBy { it.optionName }

        gameState = GameState.ShowVotes(newresults)
        sendGameStateChanged(gameState)
    }



    private fun sendGameStateChanged(sessionId:String,gameState: GameState) {
       val json= ServerResponse.GameStateChanged(gameState).toJson()
        server.sendData(sessionId,json)
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

