package de.jensklingenberg.showdown.model.api.clientrequest

import kotlinx.serialization.Serializable

@Serializable
data class JoinGame(val playerName: String, val roomPassword: String)