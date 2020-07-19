package de.jensklingenberg.showdown.model


data class WebsocketResource<T>(val type: WebSocketResourceType, val data: T?, val message: String = "")

enum class WebSocketResourceType {
    Notification,

    MESSAGE,
    GameEvent,
    UNKNOWN,
    RESPONSE
}

fun getWebsocketType(toString: String): WebSocketResourceType {
    //TODO:Find better way to get type

    return WebSocketResourceType.values().firstOrNull {
        toString.contains("\"type\":\"${it.name}\"")
    }?:WebSocketResourceType.UNKNOWN

}