package de.jensklingenberg.showdown.model

import kotlinx.serialization.Serializable


val SETROOMPASSSWORDPATH= "/room/setpassword"
val JOINROOMPATH= "/room/join"


interface Config{
    var voteOptions: VoteOptions
     val autoReveal: Boolean
    var createdAt:String
}

@Serializable
data class ClientGameConfig(override var voteOptions: VoteOptions = VoteOptions.Fibo(), override val autoReveal: Boolean = false, override var createdAt:String) :Config

@Serializable
data class Member(val playerName: String, val voteStatus:String)

@Serializable
class Option(val id:Int,val text:String)

@Serializable
data class Player(val sessionId: String, val name: String = "Unnamed")

@Serializable
data class Result(val optionName:String, val voterName:String)

data class WebsocketResource<T>(val type: WebSocketType, val data: T?, val message: String = "") {

}

enum class WebSocketType {
    Notification,

    MESSAGE,
    EVENT,
    UNKNOWN
}