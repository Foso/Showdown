package de.jensklingenberg.showdown.model

import kotlinx.serialization.Serializable


class ServerConfig(var voteOptions: VoteOptions = VoteOptions.Fibo(), val autoReveal: Boolean = false, var createdAt:String)

@Serializable
data class ClientGameConfig(var voteOptions: VoteOptions = VoteOptions.Fibo(), val autoReveal: Boolean = false, var createdAt:String)

@Serializable
data class ClientVote(val playerName: String, val voteValue:String)

@Serializable
class Option(val id:Int,val text:String)

@Serializable
data class Player(val sessionId: String, val name: String = "Unnamed")

@Serializable
data class Result(val name:String,val voters:String)