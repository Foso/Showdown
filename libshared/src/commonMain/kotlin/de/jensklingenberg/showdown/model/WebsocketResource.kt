package de.jensklingenberg.showdown.model

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration




fun ServerRequest.PlayerRequest.toJson(): String {
    return Json(JsonConfiguration.Stable).stringify(ServerRequest.PlayerRequest.serializer(), this)
}

fun getServerCommandType(json: String): ServerRequestTypes {

    return ServerRequestTypes.values().firstOrNull() {
        json.startsWith("{\"id\":${it.ordinal}")
    } ?: ServerRequestTypes.UNKNOWN
}

fun getServerRequest(json: String): ServerRequest? {
    val type = getServerCommandType(json)
    return when (type) {
        ServerRequestTypes.PLAYEREVENT -> {
            ServerCommandParser.getPlayerRequest(json)
        }

        else -> {
            null
        }
    }
}


