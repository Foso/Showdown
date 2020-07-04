package de.jensklingenberg.showdown.model

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

class ServerResponseParser {
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

        fun toJson(cmd: ServerResponse.ErrorEvent): String {
            return json.stringify(ServerResponse.ErrorEvent.serializer(), cmd)
        }

    }
}