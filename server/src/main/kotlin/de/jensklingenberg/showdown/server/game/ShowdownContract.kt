package de.jensklingenberg.showdown.server.game

import de.jensklingenberg.showdown.model.Player

interface ShowdownContract {
    interface RpsGameServer {
        fun sendBroadcast(data: String)
        fun sendData(playerId: Int, data: String)
        fun onPlayerAdded(sessionId: String, player: Player)

    }

    interface Presenter {
        fun onReset()
        fun onAddPlayer(sessionId: String, name: String)
        fun showVotes()
        fun onPlayerVoted(playerId: Int, cardId1: Int)
        fun onPlayerRejoined(sessionId: String, name: String)
    }
}