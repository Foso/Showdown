package de.jensklingenberg.showdown.server.game


import de.jensklingenberg.showdown.model.*

import de.jensklingenberg.showdown.server.model.TempVote

fun getDefaultConfig() = GameConfig(GameMode.Fibo(),true)

class GameSource(private val server: GameServer, var gameConfig: GameConfig) {

    private val playerList = mutableListOf<Player>()
    private val tempVotes = arrayListOf<TempVote>()
    var ownerID = 0

    fun onReset() {
        tempVotes.clear()

        sendMembers()
        sendGameStateChanged(GameState.Started)
    }

    fun addPlayer(sessionId: String, name: String) {
        val newPlayerID = playerList.size
        val player = Player(newPlayerID, name)
        playerList.add(player)
        server.onPlayerAdded(sessionId, player)

        sendPlayerEvent(PlayerResponseEvent.JOINED(player))
        sendMembers()
        sendGameStateChanged(GameState.GameConfigUpdate(gameConfig))

    }

    fun changeConfig(gameConfig: GameConfig){
        this.gameConfig=gameConfig
        //sendOptions()
        sendGameStateChanged(GameState.GameConfigUpdate(gameConfig))

    }


    fun onPlayerVoted(playerId: Int, voteId: Int) {
        tempVotes.add(TempVote(voteId, playerId))
        sendMembers()
        if (gameConfig.autoReveal) {
            if (tempVotes.size == playerList.size) {
                showVotes()
            }
        }
    }

    fun onPlayerRejoined(sessionId: String, name: String) {
        sendMembers()
        sendGameStateChanged(GameState.GameConfigUpdate(gameConfig))

    }

    private fun sendMembers() {
        val votesList = playerList.map { player ->

            val voted = tempVotes.any { it.playerId == player.id }

            val symbol = if (voted) {
                "Voted"
            } else {
                "Not voted"
            }

            ClientVote(player.name, symbol)
        }

        sendGameStateChanged(GameState.VoteUpdate(votesList))

    }

    fun showVotes(playerId: Int) {
        showVotes()
    }

    private fun showVotes() {
        val results = tempVotes.groupBy { it.voteId }.map {
            val (votedId, tempVotesList) = it
            val voteText = gameConfig.gameMode.options[votedId].text
            val voters = tempVotesList.joinToString(separator = ", ") { temp ->
                playerList.find { it.id == temp.playerId }?.name ?: ""
            } + " (${tempVotesList.size} Voters)"
            Result(voteText, voters)
        }

        sendGameStateChanged(GameState.Showdown(results))
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

