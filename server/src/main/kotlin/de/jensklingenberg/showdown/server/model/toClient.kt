package de.jensklingenberg.showdown.server.model

import de.jensklingenberg.showdown.model.*

fun ServerConfig.toClient(): ClientGameConfig {
    return ClientGameConfig(this.voteOptions,this.autoReveal,this.createdAt)
}

data class ServerConfig(override var voteOptions: List<String> = fibo, override val autoReveal: Boolean = false, override var createdAt:String, var room:Room):
    Config
