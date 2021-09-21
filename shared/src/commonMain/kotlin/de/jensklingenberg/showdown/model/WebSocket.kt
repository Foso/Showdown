package de.jensklingenberg.showdown.model

import kotlinx.serialization.Serializable

@Serializable
data class WebsocketResource<T>(val type: WebSocketResourceType, val data: T?, val message: String = "")

enum class WebSocketResourceType {

    UNKNOWN,
    RESPONSE
}

fun getWebsocketType(toString: String): WebSocketResourceType {
    //TODO:Find better way to get type

    return WebSocketResourceType.values().firstOrNull {
        toString.contains("\"type\":\"${it.name}\"")
    }?:WebSocketResourceType.UNKNOWN

}