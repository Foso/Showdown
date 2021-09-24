package de.jensklingenberg.showdown.model

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


enum class ServerResponseTypes {
    ERROR, STATE_CHANGED, MESSAGE, UNKNOWN
}


@Serializable
sealed class ServerResponse(val id: Int) {

    @Serializable
    class GameStateChanged(val state: GameState) : ServerResponse(ServerResponseTypes.STATE_CHANGED.ordinal)

    @Serializable
    class ErrorEvent(val error: ShowdownError) : ServerResponse(ServerResponseTypes.ERROR.ordinal)

}


private val json = Json { allowStructuredMapKeys = true }

@OptIn(ExperimentalSerializationApi::class)
fun ServerResponse.ErrorEvent.toJson(): String {
    return json.encodeToString(this)
}

@OptIn(ExperimentalSerializationApi::class)
fun ServerResponse.GameStateChanged.toJson(): String {
    return json.encodeToString(this)
}

