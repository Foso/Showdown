package de.jensklingenberg.showdown.server.game


import com.soywiz.klock.DateTime
import de.jensklingenberg.showdown.model.*
import de.jensklingenberg.showdown.server.model.Room
import de.jensklingenberg.showdown.server.model.ServerConfig

import de.jensklingenberg.showdown.server.model.TempVote
import de.jensklingenberg.showdown.server.model.toClient

fun getDefaultConfig(roomName: String) = ServerConfig(VoteOptions.Fibo(), false,createdAt = DateTime.now().utc.toString(),
    roomName = Room(roomName)
)
//http://localhost:23567/room/hans
class Game(private val server: GameServer, var gameConfig: ServerConfig) {

    private val playerList = mutableListOf<Player>()
    private val votes = arrayListOf<TempVote>()
    private var gameState: GameState = GameState.Started
    private val inactive = mutableListOf<Player>()
    private val loggit = true

    fun restart() {
        logm("restart")
        gameState = GameState.Started
        inactive.forEach { inactivePlayer ->
            playerList.removeIf { it.sessionId == inactivePlayer.sessionId }
        }
        inactive.clear()
        votes.clear()
        sendMembers()
        sendGameStateChanged(gameState)
    }

    private fun logm(message:String) {
        if(loggit){
            println(message)
        }
    }

    fun playerJoined(player: Player) {

        if(playerList.any{it.sessionId==player.sessionId}){
            onPlayerRejoined(player.sessionId,player.name)
        }else{
            logm("addPlayer "+player.name +"To Room"+gameConfig.roomName)
            playerList.add(player)
            server.onPlayerAdded(player.sessionId, player)

            sendPlayerEvent(PlayerResponseEvent.JOINED(player))
            sendMembers()
            sendGameStateChanged(GameState.GameConfigUpdate(gameConfig.toClient()))
        }


    }

    fun onPlayerRejoined(sessionId: String, name: String) {
        logm("onPlayerRejoined "+name)
        inactive.removeIf { it.sessionId == sessionId }
        sendMembers()
        sendGameStateChanged(GameState.GameConfigUpdate(gameConfig.toClient()))
    }

    fun changeConfig(clientGameConfig: ClientGameConfig) {
        logm("changeConfig ")
        this.gameConfig = gameConfig.copy(createdAt =  DateTime.now().utc.toString(),autoReveal = clientGameConfig.autoReveal,voteOptions = clientGameConfig.voteOptions)
        votes.clear()
        gameState = GameState.GameConfigUpdate( this.gameConfig.toClient())
        sendGameStateChanged(gameState)
        sendMembers()
    }


    fun onPlayerVoted(playerId: String, voteId: Int) {
        logm("onPlayerVoted "+playerId)

        if (gameState is GameState.Showdown) {
            return
        }
        println("player: " + playerId + "VOted: " + voteId)
        votes.removeIf { it.playerId == playerId }
        votes.add(TempVote(voteId, playerId))
        sendMembers()
        if (gameConfig.autoReveal) {
            if (votes.size == playerList.size) {
                showVotes()
            }
        }
    }

    fun onPlayerLeft(sessionId: String) {
        val findPlayer = playerList.find { it.sessionId == sessionId }
        findPlayer?.let {
            inactive.add(it)
        }

        sendMembers()
        sendGameStateChanged(GameState.GameConfigUpdate(gameConfig.toClient()))
        checkIfRoomEmpty()
    }

    private fun checkIfRoomEmpty() {
        if (playerList.size == inactive.size) {
            playerList.forEach {
                server.removeMember(it.sessionId)
            }
            server.closeRoom(gameConfig.roomName.name)
        }
    }

    private fun sendMembers() {
        val votesList = playerList.map { player ->
            val isInActive = inactive.any { it.sessionId == player.sessionId }
            val inActiveText = if (isInActive) {
                "(Left)"
            } else {
                ""
            }
            val voted = votes.any { it.playerId == player.sessionId }
            val symbol = if (voted) {
                "Voted $inActiveText"
            } else {
                "? $inActiveText"
            }

            ClientVote(player.name, symbol)
        }

        sendGameStateChanged(GameState.VoteUpdate(votesList))

    }

    fun showVotes(sessionId: String) {
        showVotes()
    }

    private fun showVotes() {

        val results = votes.groupBy { it.voteId }.map {
            val (votedId, tempVotesList) = it
            val voteText = gameConfig.voteOptions.options[votedId].text
            val voters = tempVotesList.joinToString(separator = ", ") { temp ->
                playerList.find { it.sessionId == temp.playerId }?.name ?: ""
            } + " (${tempVotesList.size} Voters)"
            Result(voteText, voters)
        }
        gameState = GameState.Showdown(results)
        sendGameStateChanged(gameState)
    }


    private fun sendGameStateChanged(gameState: GameState) {
        val json2 = ServerResponse.GameStateChanged(gameState).toJson()
        sendBroadcast(json2)
    }

    private fun sendBroadcast(json2: String) {
        playerList.forEach {
            server.sendData(it.sessionId,json2)
        }
    }

    private fun sendPlayerEvent(playerResponseEvent: PlayerResponseEvent) {
        val json = ServerResponse.PlayerEvent(playerResponseEvent).toJson()
        sendBroadcast(json)
    }


}

