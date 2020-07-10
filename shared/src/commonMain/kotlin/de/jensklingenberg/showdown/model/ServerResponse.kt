package de.jensklingenberg.showdown.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration


enum class ServerResponseTypes {
     ERROR,  STATE_CHANGED, MESSAGE, PLAYER_EVENT,UNKNOWN
}
@Serializable
data class Request(val path: String,val body:String)


fun Request.toJson(): String {
    return Json(JsonConfiguration.Stable).stringify(Request.serializer(), this)
}

data class Hallo(val name:String)
@Serializable
sealed class ServerResponse(val id: Int) {

    @Serializable
    class PlayerEvent(val playerResponseEvent: PlayerResponseEvent) : ServerResponse(ServerResponseTypes.PLAYER_EVENT.ordinal)

    @Serializable
    class GameStateChanged(val state: GameState) : ServerResponse(ServerResponseTypes.STATE_CHANGED.ordinal)

    @Serializable
    class ErrorEvent(val error: ShowdownError) : ServerResponse(ServerResponseTypes.ERROR.ordinal)

}
fun ServerResponse.ErrorEvent.toJson(): String {
    return Json(JsonConfiguration.Stable).stringify(ServerResponse.ErrorEvent.serializer(), this)
}

fun ServerResponse.PlayerEvent.toJson(): String {
    return Json(JsonConfiguration.Stable).stringify(ServerResponse.PlayerEvent.serializer(), this)
}

fun ServerResponse.GameStateChanged.toJson(): String {
    return Json(JsonConfiguration.Stable).stringify(ServerResponse.GameStateChanged.serializer(), this)
}


fun getClientCommandType(toString: String): ServerResponseTypes {

    return ServerResponseTypes.values().firstOrNull() {
        toString.startsWith("{\"id\":${it.ordinal}")
    }?:ServerResponseTypes.UNKNOWN
}

fun getServerResponse(json:String): ServerResponse? {
    return when(getClientCommandType(json)){
        ServerResponseTypes.STATE_CHANGED -> {
            ServerResponseParser.getGameStateChangedCommand(json)
        }
       ServerResponseTypes.ERROR -> {

           ServerResponseParser.getErrorCommand(json)
       }

        ServerResponseTypes.PLAYER_EVENT -> {
            ServerResponseParser.getPlayerEvent(json)
        }
       else -> {
           null
       }
    }
}