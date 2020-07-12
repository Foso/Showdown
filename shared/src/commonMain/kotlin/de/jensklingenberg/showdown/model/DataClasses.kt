package de.jensklingenberg.showdown.model

import kotlinx.serialization.Serializable


val SETROOMPASSSWORDPATH= "/room/setpassword"
val SHOWVOTESPATH= "/room/showvotes"
val RESTARTPATH= "/room/restart"
val JOINROOMPATH= "/room/join"
val VOTEPATH= "/vote"
val CHNAGECONFIGPATH = "/change"


interface Config{
    var voteOptions: List<String>
     val autoReveal: Boolean
    var createdAt:String
}

@Serializable
data class ClientGameConfig(override var voteOptions: List<String> = fibo, override val autoReveal: Boolean = false, override var createdAt:String) :Config


data class NewGameConfig(var voteOptions: List<String> = fibo, val autoReveal: Boolean = false)

@Serializable
data class Member(val playerName: String, val voteStatus:String)

@Serializable
data class Result(val optionName:String, val voterName:String)

data class WebsocketResource<T>(val resourceType: WebSocketResourceType, val data: T?, val message: String = "")

enum class WebSocketResourceType {
    Notification,

    MESSAGE,
    GameState,
    UNKNOWN
}

fun getWebsocketType(toString: String): WebSocketResourceType {
    //TODO:Find better way to get type

   return WebSocketResourceType.values().firstOrNull {
        toString.contains("\"type\":\"${it.ordinal}\"")
    }?:WebSocketResourceType.UNKNOWN

}