package de.jensklingenberg.showdown.server.game

import de.jensklingenberg.showdown.model.Player
interface GameServer {

    fun sendData(sessionId: String, data: String)
    fun onPlayerAdded(sessionId: String, player: Player)
    fun createNewRoom(roomName: String) :ServerGame
    fun closeRoom(roomName: String)
    fun removeMember(sessionId: String)
}