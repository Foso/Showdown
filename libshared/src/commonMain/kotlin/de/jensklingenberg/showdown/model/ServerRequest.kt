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
sealed class GameMode(open val list: List<String>) {
    @Serializable
    class Fibo : GameMode(listOf("0", "1", "2", "3", "5", "8", "13", "21", "34", "55", "89", "?"))
    @Serializable
    class Custom(val list2: List<String> = listOf("Hund", "Katze", "Maus", "Bär", "Tiger")) : GameMode(list2)
}

@Serializable
sealed class PlayerRequestEvent {

    @Serializable
    class CreateRoom(val gameMode : GameMode, val name:String) : PlayerRequestEvent()

    @Serializable
    class JoinGameRequest(val playerName:String) : PlayerRequestEvent()

    @Serializable
    class Voted(val voteId: Int) : PlayerRequestEvent()

    @Serializable
    class ShowVotes : PlayerRequestEvent()

    @Serializable
    class ResetRequest : PlayerRequestEvent()
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

