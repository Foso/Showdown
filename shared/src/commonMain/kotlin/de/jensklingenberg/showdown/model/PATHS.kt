package de.jensklingenberg.showdown.model

/**
 * Contains the path for a request/response between Server and Client
 */
enum class PATHS(val path: String) {
    SETROOMPASSSWORDPATH("/room/password/set"),
    MESSAGE("/event"),
    EMPTY(""),
    ROOMCONFIGUPDATE("/room/config"),
    SPECTATORPATH("/spectator"),
    SHOWVOTES("/room/showvotes"),
    JOINROOM("/room/join"),
    RESTARTPATH("/room/restart"),
    VOTEPATH("/vote"),
    SETAUTOREVEALPATH("/room/autoreveal/set"),
    SETANONYMVOTES("/room/config/anonymvote/set")
}