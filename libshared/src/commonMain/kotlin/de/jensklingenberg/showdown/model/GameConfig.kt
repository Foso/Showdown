package de.jensklingenberg.showdown.model

import kotlinx.serialization.Serializable

@Serializable
data class GameConfig(var gameMode: GameMode = GameMode.Fibo(),  val autoReveal: Boolean = true)
