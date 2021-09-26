package de.jensklingenberg.showdown.model

import kotlinx.serialization.Serializable


interface GameConfig {
    var voteOptions: List<String>

    /**
     * If true, all votes will be shown, when all
     * players voted
     */
    val autoReveal: Boolean

    /**
     * The time when a game was created
     */
    val createdAt: String
    val anonymResults: Boolean
    val votingName: String
}

@Serializable
data class ClientGameConfig(
    override var voteOptions: List<String> = fibo,
    override val autoReveal: Boolean = false,
    override var createdAt: String,
    val roomHasPassword: Boolean = false,
    override var anonymResults: Boolean = false,
    override val votingName: String = ""
) : GameConfig

@Serializable
data class Member(val playerName: String, val voted: Boolean, val isConnected: Boolean, val isSpectator: Boolean)

@Serializable
data class Result(val optionName: String, val voterName: String)

@Serializable
data class History(val name: String, val answer: String)