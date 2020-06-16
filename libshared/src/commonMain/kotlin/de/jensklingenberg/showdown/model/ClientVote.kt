package de.jensklingenberg.showdown.model

import kotlinx.serialization.Serializable

@Serializable
data class ClientVote(val playerName: String, val voteValue:String)