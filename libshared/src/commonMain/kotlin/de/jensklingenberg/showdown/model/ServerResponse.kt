package de.jensklingenberg.showdown.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration


enum class ClientCommands {
     ERROR,  STATE_CHANGED, MESSAGE, PLAYER_EVENT,UNKNOWN
}

@Serializable
sealed class ServerResponse(val id: Int) {

    @Serializable
    class PlayerEvent(val playerResponseEvent: PlayerResponseEvent) : ServerResponse(ClientCommands.PLAYER_EVENT.ordinal)

    @Serializable
    class GameStateChanged(val state: GameState) : ServerResponse(ClientCommands.STATE_CHANGED.ordinal)

    @Serializable
    class ErrorEvent(val message: String) : ServerResponse(ClientCommands.ERROR.ordinal)

}


fun ServerResponse.PlayerEvent.toJson(): String {
    return Json(JsonConfiguration.Stable).stringify(ServerResponse.PlayerEvent.serializer(), this)
}

fun ServerResponse.GameStateChanged.toJson(): String {
    return Json(JsonConfiguration.Stable).stringify(ServerResponse.GameStateChanged.serializer(), this)
}


fun getClientCommandType(toString: String): ClientCommands {

    return ClientCommands.values().firstOrNull() {
        toString.startsWith("{\"id\":${it.ordinal}")
    }?:ClientCommands.UNKNOWN
}

fun getServerResponse(json:String): ServerResponse? {
    val type = getClientCommandType(json)
   return when(type){
        ClientCommands.STATE_CHANGED -> {
            ServerResponseParser.getGameStateChangedCommand(json)
        }
       ClientCommands.ERROR -> {
           ServerResponseParser.getErrorCommand(json)
       }

        ClientCommands.PLAYER_EVENT -> {
            ServerResponseParser.getPlayerEvent(json)
        }
       else -> {
           null
       }
    }
}