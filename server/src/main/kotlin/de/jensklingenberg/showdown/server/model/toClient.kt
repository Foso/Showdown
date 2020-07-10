package de.jensklingenberg.showdown.server.model

import de.jensklingenberg.showdown.model.ClientGameConfig
import de.jensklingenberg.showdown.model.Config
import de.jensklingenberg.showdown.model.VoteOptions

fun ServerConfig.toClient(): ClientGameConfig {
    return ClientGameConfig(this.voteOptions,this.autoReveal,this.createdAt)
}

data class ServerConfig(override var voteOptions: VoteOptions = VoteOptions.Fibo(), override val autoReveal: Boolean = false, override var createdAt:String, var room:Room):
    Config
