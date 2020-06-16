package de.jensklingenberg.showdown.model

import kotlinx.serialization.Serializable


@Serializable
sealed class GameState {

    /**
     * Initial State for clients
     */
    @Serializable
    object NotConnected : GameState()


    @Serializable
    object Started : GameState()

    @Serializable
    class VoteUpdate(val clientVotes: List<ClientVote>) : GameState()


    @Serializable
    class Showdown(val results:List<Result>) : GameState()


    @Serializable
    class GameConfigUpdate(val gameConfig: GameConfig) : GameState()



}