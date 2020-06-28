package de.jensklingenberg.showdown.server.game


import de.jensklingenberg.showdown.model.*

import de.jensklingenberg.showdown.server.model.TempVote

fun getDefaultConfig() = GameConfig(GameMode.Fibo(), false)
//http://localhost:23567/room/hans
class Game(private val server: GameServer, var gameConfig: GameConfig) {

    private val playerList = mutableListOf<Player>()
    private val votes = arrayListOf<TempVote>()

    private var gameState: GameState = GameState.Started

    private val inactive = mutableListOf<Player>()

    fun onRestart() {
        gameState = GameState.Started
        inactive.forEach { inactivePlayer ->
            playerList.removeIf { it.sessionId == inactivePlayer.sessionId }
        }
        inactive.clear()
        votes.clear()
        sendMembers()
        sendGameStateChanged(gameState)
    }

    fun addPlayer(sessionId: String, name: String, player: Player) {
        playerList.add(player)
        server.onPlayerAdded(sessionId, player)

        sendPlayerEvent(PlayerResponseEvent.JOINED(player))
        sendMembers()
        sendGameStateChanged(GameState.GameConfigUpdate(gameConfig))

    }

    fun changeConfig(gameConfig: GameConfig) {
        this.gameConfig = gameConfig
        //sendOptions()
        votes.clear()
        gameState = GameState.GameConfigUpdate(gameConfig)
        sendGameStateChanged(gameState)
        sendMembers()
    }


    fun onPlayerVoted(playerId: String, voteId: Int) {
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

    fun onPlayerRejoined(sessionId: String, name: String) {
        inactive.removeIf { it.sessionId == sessionId }
        sendMembers()
        sendGameStateChanged(GameState.GameConfigUpdate(gameConfig))
    }

    fun onPlayerLeft(sessionId: String) {
        val findPlayer = playerList.find { it.sessionId == sessionId }
        findPlayer?.let {
            inactive.add(it)
        }

        sendMembers()
        sendGameStateChanged(GameState.GameConfigUpdate(gameConfig))
    }

    private fun sendMembers() {
        val votesList = playerList.map { player ->
            val isInActive = inactive.any { it.sessionId == player.sessionId }
            val inActiveText = if (isInActive) {
                "(Inactive)"
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
            val voteText = gameConfig.gameMode.options[votedId].text
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

