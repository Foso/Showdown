package de.jensklingenberg.showdown.server.game


import com.soywiz.klock.DateTime
import de.jensklingenberg.showdown.model.*

import de.jensklingenberg.showdown.server.model.TempVote

fun getDefaultConfig() = ClientGameConfig(VoteOptions.Fibo(), false,createdAt = DateTime.now().utc.toString())
//http://localhost:23567/room/hans
class Game(private val server: GameServer, var clientGameConfig: ClientGameConfig) {

    private val playerList = mutableListOf<Player>()
    private val votes = arrayListOf<TempVote>()
    private var gameState: GameState = GameState.Started
    private val inactive = mutableListOf<Player>()
    val loggit = true
 val players : ArrayList<String> = arrayListOf()

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

    fun addPlayer( player: Player) {

        logm("addPlayer "+player.name)
        playerList.add(player)
        server.onPlayerAdded(player.sessionId, player)

        sendPlayerEvent(PlayerResponseEvent.JOINED(player))
        sendMembers()
        sendGameStateChanged(GameState.GameConfigUpdate(clientGameConfig))

    }

    fun changeConfig(clientGameConfig: ClientGameConfig) {
        logm("changeConfig ")
        this.clientGameConfig = clientGameConfig.copy(createdAt =  DateTime.now().utc.toString())
        //sendOptions()
        votes.clear()
        gameState = GameState.GameConfigUpdate( this.clientGameConfig)
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
        if (clientGameConfig.autoReveal) {
            if (votes.size == playerList.size) {
                showVotes()
            }
        }
    }

    fun onPlayerRejoined(sessionId: String, name: String) {
        logm("onPlayerVoted "+name)
        inactive.removeIf { it.sessionId == sessionId }
        sendMembers()
        sendGameStateChanged(GameState.GameConfigUpdate(clientGameConfig))
    }

    fun onPlayerLeft(sessionId: String) {
        val findPlayer = playerList.find { it.sessionId == sessionId }
        findPlayer?.let {
            inactive.add(it)
        }

        sendMembers()
        sendGameStateChanged(GameState.GameConfigUpdate(clientGameConfig))
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
            val voteText = clientGameConfig.voteOptions.options[votedId].text
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
        server.sendBroadcast(json2)
    }

    private fun sendPlayerEvent(playerResponseEvent: PlayerResponseEvent) {
        val json2 = ServerResponse.PlayerEvent(playerResponseEvent).toJson()
        server.sendBroadcast(json2)
    }


}

