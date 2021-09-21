package de.jensklingenberg.showdown.model

import kotlinx.serialization.Serializable

/**
 * Contains the path for a request/response between Server and Client
 */
enum class PATHS(val path:String){
    SETROOMPASSSWORDPATH( "/room/password/set"),
    MESSAGE("/event"),

    EMPTY(""),
    ROOMCONFIGUPDATE("/room/config"),
    SPECTATORPATH("/spectator"),
    SHOWVOTESPATH("/room/showvotes"),
    JOINROOMPATH("/room/join")

}
val SETROOMPASSSWORDPATH= "/room/password/set"
val SETAUTOREVEALPATH= "/room/autoreveal/set"
val SETANONYMVOTES= "/room/config/anonymvote/set"



val RESTARTPATH= "/room/restart"
val VOTEPATH= "/vote"

val GAMEEVENTPATH = "/event"


interface GameConfig{
    var voteOptions: List<String>

    /**
     * If true, all votes will be shown, when all
     * players voted
     */
     val autoReveal: Boolean

    /**
     * The time when a game was created
     */
    var createdAt:String
    var anonymResults:Boolean
}

@Serializable
data class ClientGameConfig(override var voteOptions: List<String> = fibo, override val autoReveal: Boolean = false, override var createdAt:String, val roomHasPassword:Boolean=false,
                            override var anonymResults: Boolean = false
) :GameConfig

@Serializable
data class Member(val playerName: String, val voted: Boolean, val isConnected: Boolean, val isSpectator:Boolean)

@Serializable
data class Result(val optionName:String, val voterName:String)
