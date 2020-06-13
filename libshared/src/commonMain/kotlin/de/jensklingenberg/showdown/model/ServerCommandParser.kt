package de.jensklingenberg.showdown.model

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration

class ServerCommandParser {
    companion object {
        fun getPlayerRequest(jsonStr: String): ServerRequest.PlayerRequest {
            val json =
                Json(JsonConfiguration.Stable)
            return json.parse(ServerRequest.PlayerRequest.serializer(), jsonStr)

        }
    }
}