package de.jensklingenberg.showdown.model

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration



fun ServerRequest.ResetRequest.toJson(): String {
    return Json(JsonConfiguration.Stable).stringify(ServerRequest.ResetRequest.serializer(), this)
}

fun ServerRequest.PlayerRequest.toJson(): String {
    return Json(JsonConfiguration.Stable).stringify(ServerRequest.PlayerRequest.serializer(), this)
}

fun getServerCommandType(toString: String): ServerRequestTypes {

    return ServerRequestTypes.values().firstOrNull() {
        toString.startsWith("{\"id\":${it.ordinal}")
    }?: ServerRequestTypes.UNKNOWN
}


