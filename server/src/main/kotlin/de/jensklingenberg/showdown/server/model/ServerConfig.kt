package de.jensklingenberg.showdown.server.model

import com.soywiz.klock.DateTime
import de.jensklingenberg.showdown.model.*

fun ServerConfig.toClient(): ClientGameConfig {
    return ClientGameConfig(
        this.voteOptions,
        this.autoReveal,
        this.createdAt,
        roomHasPassword = this.room.password.isNotEmpty()
    )
}

data class ServerConfig(override var voteOptions: List<String> = fibo, override val autoReveal: Boolean = false, override var createdAt:String, var room:Room):
    Config

fun getDefaultConfig(roomName: String) = ServerConfig(
    fibo, true, createdAt = DateTime.now().unixMillisDouble.toString(),
    room = Room(roomName, "")
)