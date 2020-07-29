package de.jensklingenberg.showdown.server.game

import com.nhaarman.mockitokotlin2.anyOrNull
import com.nhaarman.mockitokotlin2.atLeastOnce
import com.nhaarman.mockitokotlin2.eq
import com.soywiz.klock.DateTime
import com.squareup.moshi.Moshi
import de.jensklingenberg.showdown.model.*
import de.jensklingenberg.showdown.server.common.toJson
import de.jensklingenberg.showdown.server.model.Player
import de.jensklingenberg.showdown.server.model.Room
import de.jensklingenberg.showdown.server.model.ServerConfig
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito

class ServerGameTest {
    val mockServer = Mockito.mock(GameServer::class.java)

    private val moshi = Moshi.Builder().build()

    @Before
    fun setUp() {
    }

    @Test
    fun test() {
        val room = Room("Test")
        val config = ServerConfig(fibo, true, DateTime.now().unixMillisDouble.toString(), room)

        val player = Player("1", "Jens")
        val game = ServerGame(mockServer, config)
        game.playerJoined(player)
        Mockito.verify(mockServer).onPlayerAdded(anyString(), anyOrNull())
        game.onPlayerVoted(player.sessionId, 0)
        Mockito.verify(mockServer).onPlayerAdded(eq(player.sessionId), anyOrNull())

    }

    @Test
    fun whenPlayerGetsSpectator_SendNewStateToThePlayer() {
        val configJson = moshi.toJson(true)
        val response = Response(PATHS.SPECTATORPATH.path, configJson)
        val testResponse = WebsocketResource(WebSocketResourceType.RESPONSE, response)

        val room = Room("Test")
        val config = ServerConfig(fibo, true, DateTime.now().unixMillisDouble.toString(), room)

        val player1 = Player("1", "Jens")
        val game = ServerGame(mockServer, config)
        game.playerJoined(player1)
        game.onSpectate(player1.sessionId, true)

        Mockito.verify(mockServer, atLeastOnce()).sendData(eq(player1.sessionId), eq(testResponse.toJson()))
    }

    @Test
    fun whenPlayerAddsRoomPassword_() {
        val roomPassword = "secrete"
        val room = Room("Test")
        val config = ServerConfig(fibo, true, DateTime.now().unixMillisDouble.toString(), room)

        val player1 = Player("1", "Jens")
        val game = ServerGame(mockServer, config)
        game.playerJoined(player1)
        game.changeRoomPassword(player1.sessionId, roomPassword)
        assert(game.gameConfig.room.password == roomPassword)
    }


    @After
    fun tearDown() {
    }
}