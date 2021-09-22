package de.jensklingenberg.showdown.model

import kotlinx.serialization.json.Json


class ServerResponseParser {
    companion object {
        private val json =
            Json {
                allowStructuredMapKeys = true
                ignoreUnknownKeys = true
            }

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