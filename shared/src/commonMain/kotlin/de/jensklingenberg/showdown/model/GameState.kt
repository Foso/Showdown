package de.jensklingenberg.showdown.model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


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


private val json = Json { allowStructuredMapKeys = true }


@OptIn(ExperimentalSerializationApi::class)
fun GameState.toJson(): String {
    return json.encodeToString(this)
}