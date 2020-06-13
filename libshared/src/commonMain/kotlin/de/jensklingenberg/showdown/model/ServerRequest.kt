package de.jensklingenberg.showdown.model

import kotlinx.serialization.Serializable


@Serializable
enum class ServerRequestTypes {
    PLAYEREVENT,
    MESSAGE,
    ERROR,
    UNKNOWN,
    RESET
}

@Serializable
sealed class PlayerRequestEvent {

    @Serializable
    class StartGame : PlayerRequestEvent()

    @Serializable
    class JoinGameRequest(val playerName:String) : PlayerRequestEvent()

    @Serializable
    class Voted(val cardId: Int) : PlayerRequestEvent()

    @Serializable
    class ShowVotes : PlayerRequestEvent()

}

@Serializable
sealed class ServerRequest(val id: Int) {

    @Serializable
    class PlayerRequest(val playerRequestEvent: PlayerRequestEvent) : ServerRequest(
        ServerRequestTypes.PLAYEREVENT.ordinal
    )

    @Serializable
    class ResetRequest : ServerRequest(ServerRequestTypes.RESET.ordinal)

    @Serializable
    class MessageRequest : ServerRequest(ServerRequestTypes.MESSAGE.ordinal)

}

