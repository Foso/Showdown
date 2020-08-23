package de.jensklingenberg.showdown.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json


enum class ServerResponseTypes {
     ERROR,  STATE_CHANGED, MESSAGE, UNKNOWN
}


@Serializable
sealed class ServerResponse(val id: Int) {

    @Serializable
    class GameStateChanged(val state: GameState) : ServerResponse(ServerResponseTypes.STATE_CHANGED.ordinal)

    @Serializable
    class ErrorEvent(val error: ShowdownError) : ServerResponse(ServerResponseTypes.ERROR.ordinal)

}


fun ServerResponse.ErrorEvent.toJson(): String {
    return Json { allowStructuredMapKeys = true }.encodeToString(ServerResponse.ErrorEvent.serializer(), this)
}

fun ServerResponse.GameStateChanged.toJson(): String {
    return Json { allowStructuredMapKeys = true }.encodeToString(ServerResponse.GameStateChanged.serializer(), this)
}

