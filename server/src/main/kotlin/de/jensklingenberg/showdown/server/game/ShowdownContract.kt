package de.jensklingenberg.showdown.server.game

import de.jensklingenberg.showdown.server.model.Player


interface GameServer {

    /**
     * Send [data] to the client with the [sessionId]
     */
    fun sendData(sessionId: String, data: String)

    fun onPlayerAdded(sessionId: String, player: Player)

    fun closeRoom(roomName: String)


    fun removePlayer(sessionId: String)
}