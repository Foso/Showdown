package de.jensklingenberg.showdown.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
@Serializable
class Option(val id:Int,val text:String)

@Serializable
class Result(val name:String,val voters:String)

class ClientCommandParser {
    companion object {
        private val json =
            Json(JsonConfiguration.Stable)


        fun getGameStateChangedCommand(jsonStr: String): ServerResponse.GameStateChanged {
            return json.parse(ServerResponse.GameStateChanged.serializer(), jsonStr)
        }

        fun getPlayerEvent(jsonStr: String): ServerResponse.PlayerEvent {
            return json.parse(ServerResponse.PlayerEvent.serializer(), jsonStr)
        }

        fun getErrorCommand(jsonStr: String): ServerResponse.ErrorEvent {
            return json.parse(ServerResponse.ErrorEvent.serializer(), jsonStr)
        }

        fun getPlayerCards(jsonStr: String): ServerResponse.PlayerVotes {
            return json.parse(ServerResponse.PlayerVotes.serializer(), jsonStr)
        }

        fun toJson(cmd: ServerResponse.ErrorEvent): String {
            return json.stringify(ServerResponse.ErrorEvent.serializer(), cmd)
        }

    }
}