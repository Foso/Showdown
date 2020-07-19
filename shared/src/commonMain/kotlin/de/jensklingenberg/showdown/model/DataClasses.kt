package de.jensklingenberg.showdown.model

import kotlinx.serialization.Serializable

enum class PATHS(val path:String){
    SETROOMPASSSWORDPATH( "/room/password/set"),
    MESSAGE("/event"),
    ROOMUPDATE("/room/update"),
    EMPTY(""),
    ROOMCONFIGUPDATE("/room/config")
}
val SETROOMPASSSWORDPATH= "/room/password/set"
val SETAUTOREVEALPATH= "/room/autoreveal/set"

val SHOWVOTESPATH= "/room/showvotes"
val RESTARTPATH= "/room/restart"
val JOINROOMPATH= "/room/join"
val VOTEPATH= "/vote"

val GAMEEVENTPATH = "/event"


interface Config{
    var voteOptions: List<String>
     val autoReveal: Boolean
    var createdAt:String
}

@Serializable
data class ClientGameConfig(override var voteOptions: List<String> = fibo, override val autoReveal: Boolean = false, override var createdAt:String,val roomHasPassword:Boolean=false) :Config

@Serializable
data class Member(val playerName: String, val voted: Boolean, val isConnected: Boolean)

@Serializable
data class Result(val optionName:String, val voterName:String)
