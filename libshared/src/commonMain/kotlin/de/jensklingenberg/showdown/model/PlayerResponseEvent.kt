package de.jensklingenberg.showdown.model

import kotlinx.serialization.Serializable

@Serializable
sealed class PlayerResponseEvent {
    @Serializable
    class JOINED(val yourPlayer: Player) : PlayerResponseEvent()



}