package de.jensklingenberg.showdown.model

import kotlinx.serialization.Serializable


@Serializable
sealed class GameState {

    /**
     * Initial State for clients
     */
    @Serializable
    object NotStarted : GameState()

    @Serializable
    class Started(val clientGameConfig: ClientGameConfig) : GameState()

    @Serializable
    class PlayerListUpdate(val members: List<Member>) : GameState()

    @Serializable
    class ShowVotes(val results: List<Result>) : GameState()

}



