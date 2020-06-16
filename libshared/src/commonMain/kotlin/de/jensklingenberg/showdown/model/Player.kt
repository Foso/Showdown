package de.jensklingenberg.showdown.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

@Serializable
data class Player(val id: Int, val name: String = "Unnamed")
