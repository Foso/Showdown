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
    class MembersUpdate(val members: List<Member>) : GameState()

    @Serializable
    class ShowVotes(val results:List<Result>) : GameState()

}

enum class EnGameState{
    NOTSTARTED,STARTED,MEMBERSUDPATE,SHOWVOTES
}

class MyGameState(val enGameState: EnGameState,val body:Any)
class MyMembersUpdate(val members: String)