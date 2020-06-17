package de.jensklingenberg.showdown.model

import kotlinx.serialization.Serializable

@Serializable
data class GameConfig(var gameMode: GameMode = GameMode.Fibo(),  val autoReveal: Boolean = true)

@Serializable
data class ClientVote(val playerName: String, val voteValue:String)

@Serializable
class Option(val id:Int,val text:String)

@Serializable
data class Player(val sessionId: String, val name: String = "Unnamed")

@Serializable
data class Result(val name:String,val voters:String)