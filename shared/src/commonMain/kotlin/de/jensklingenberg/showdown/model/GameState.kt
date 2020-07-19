package de.jensklingenberg.showdown.model

import kotlinx.serialization.Serializable
import kotlin.reflect.KClass


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
    class ShowVotes(val results:List<Result>) : GameState()

}

enum class GameEventType(val kClass: KClass<*>) {
    Normal(MyGameEvent::class)
}

open class GameEvent(val eventId: Int)
data class MyGameEvent(val name:String):GameEvent(GameEventType.Normal.ordinal)

enum class EnGameState{
    NOTSTARTED,STARTED,MEMBERSUDPATE,SHOWVOTES
}

class MyGameState(val enGameState: EnGameState,val body:Any)
