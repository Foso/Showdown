package de.jensklingenberg.showdown.model

import kotlinx.serialization.Serializable


@Serializable
open class GameState {

    /**
     * Initial State for clients
     */
    @Serializable
    object NotStarted : GameState()

    @Serializable
    object Started : GameState()

    @Serializable
    class NewStarted(val clientGameConfig: ClientGameConfig) : GameState()

    @Serializable
    class MembersUpdate(val members: List<Member>) : GameState()

    @Serializable
    class GameConfigUpdate(val clientGameConfig: ClientGameConfig) : GameState()

    @Serializable
    class ShowVotes(val results:List<Result>) : GameState()

}