package de.jensklingenberg.showdown.server.game


import com.soywiz.klock.DateTime
import de.jensklingenberg.showdown.model.*
import de.jensklingenberg.showdown.server.model.*

fun getDefaultConfig(roomName: String) = ServerConfig(
    Fibo(), false, createdAt = DateTime.now().utc.toString(),
    room = Room(roomName, "")
)

//http://localhost:23567/room/hans
class ServerGame(private val server: GameServer, var gameConfig: ServerConfig) {

    private val playerList = mutableListOf<Player>()
    private val votes = arrayListOf<Vote>()
    private var gameState: GameState = GameState.NotStarted
    private val inactivePlayers = mutableListOf<Player>()
    private val loggit = true

    fun changePassword(password: String) {
        val newRoomData =gameConfig.room.copy(password = password)
        gameConfig = gameConfig.copy(room = newRoomData)
    }

    fun restart() {
        logm("restart")
        gameState = GameState.Started
        inactivePlayers.forEach { inactivePlayer ->
            playerList.removeIf { it.sessionId == inactivePlayer.sessionId }
        }
        inactivePlayers.clear()
        votes.clear()
        sendPlayers()
        sendGameStateChanged(gameState)
    }

    private fun logm(message: String) {
        if (loggit) {
            println(message)
        }
    }

    fun playerJoined(player: Player) {

        if (playerList.any { it.sessionId == player.sessionId }) {
            onPlayerRejoined(player.sessionId, player.name)
        } else {
            if (gameState == GameState.NotStarted) {
                gameState = GameState.Started
            }
            logm("addPlayer " + player.name + "To Room" + gameConfig.room)
            playerList.add(player)
            server.onPlayerAdded(player.sessionId, player)

           // sendPlayerEvent(PlayerResponseEvent.JOINED(player))
            sendPlayers()
            sendGameStateChanged(GameState.GameConfigUpdate(gameConfig.toClient()))
        }


    }

    private fun onPlayerRejoined(sessionId: String, name: String) {
        logm("onPlayerRejoined " + name)
        playerList.replaceAll {
            if(it.sessionId==sessionId){
                it.copy(name=name)
            }else{
                it
            }
        }
        inactivePlayers.removeIf { it.sessionId == sessionId }
        sendPlayers()
        sendGameStateChanged(GameState.GameConfigUpdate(gameConfig.toClient()))
    }

    fun changeConfig(clientGameConfig: ClientGameConfig) {
        logm("changeConfig ")
        this.gameConfig = gameConfig.copy(
            room = Room(gameConfig.room.name, "HEY"),
            createdAt = DateTime.now().utc.toString(),
            autoReveal = clientGameConfig.autoReveal,
            voteOptions = clientGameConfig.voteOptions
        )
        votes.clear()
        gameState = GameState.GameConfigUpdate(this.gameConfig.toClient())
        sendGameStateChanged(gameState)
        sendPlayers()
    }


    fun onPlayerVoted(playerId: String, voteId: Int) {
        logm("onPlayerVoted " + playerId)

        if (gameState is GameState.ShowVotes) {
            return
        }
        println("player: " + playerId + "VOted: " + voteId)
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
        sendGameStateChanged(GameState.GameConfigUpdate(gameConfig.toClient()))
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
                "(Left)"
            } else {
                ""
            }
            val voted = votes.any { it.playerId == player.sessionId }
            val playerStatusText = if (voted) {
                "Voted $inActiveText"
            } else {
                "? $inActiveText"
            }

            Member(player.name, playerStatusText)
        }

        sendGameStateChanged(GameState.MembersUpdate(votesList))

    }

    fun showVotes(sessionId: String) {
        showVotes()
    }

    private fun showVotes() {
        logm("SHOWVOTES")
        val newresults = votes.map {
            val voterId = it.playerId
            val voteText = gameConfig.voteOptions.options[it.voteId]
            val voterName = playerList.find { it.sessionId == voterId }?.name ?: ""
            Result(voteText, voterName)
        }.sortedBy { it.optionName }

        gameState = GameState.ShowVotes(newresults)
        sendGameStateChanged(gameState)
    }


    private fun sendGameStateChanged(gameState: GameState) {
        sendBroadcast(ServerResponse.GameStateChanged(gameState).toJson())
    }

    private fun sendBroadcast(json2: String) {
        playerList.forEach {
            server.sendData(it.sessionId, json2)
        }
    }

    private fun sendPlayerEvent(playerResponseEvent: PlayerResponseEvent) {
        val json = ServerResponse.PlayerEvent(playerResponseEvent).toJson()
        sendBroadcast(json)
    }


}

