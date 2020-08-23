package de.jensklingenberg.showdown.model

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.serialization.parse
import kotlinx.serialization.stringify

class ServerResponseParser {
    companion object {
        private val json =
            Json { allowStructuredMapKeys = true }

        fun getGameStateChangedCommand(jsonStr: String): ServerResponse.GameStateChanged {
            return json.decodeFromString(
                ServerResponse.GameStateChanged.serializer(), jsonStr
            )
        }


        fun getErrorCommand(jsonStr: String): ServerResponse.ErrorEvent {
            return json.decodeFromString(
                ServerResponse.ErrorEvent.serializer(), jsonStr
            )
        }

        fun toJson(cmd: ServerResponse.ErrorEvent): String {
            return json.encodeToString(
                ServerResponse.ErrorEvent.serializer(), cmd
            )
        }

    }
}