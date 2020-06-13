package de.jensklingenberg.showdown.model

import kotlinx.serialization.Serializable

@Serializable
data class ClientVote(val playerName: String, val voteValue:String)


@Serializable
sealed class GameState {

    /**
     * Initial State for clients
     */
    @Serializable
    object NotConnected : GameState()

    /**
     * A player is waiting for other players
     */

    @Serializable
    object Lobby : GameState()

    @Serializable
    object Started : GameState()

    @Serializable
    class VoteUpdate(val clientVotes: List<ClientVote>) : GameState()

    @Serializable
    class OptionsUpdate(val options: List<Option>) : GameState()

    @Serializable
    class Showdown(val results:List<Result>) : GameState()



}