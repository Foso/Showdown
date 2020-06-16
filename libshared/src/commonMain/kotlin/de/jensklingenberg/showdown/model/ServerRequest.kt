package de.jensklingenberg.showdown.model

import kotlinx.serialization.Serializable


@Serializable
enum class ServerRequestTypes {
    PLAYEREVENT,
    MESSAGE,
    ERROR,
    UNKNOWN
}

val list3: List<String> = listOf("Hund", "Katze", "Maus", "Bär", "Tiger")
@Serializable
sealed class GameMode(open val options: List<Option>) {
    @Serializable
    class Fibo : GameMode(listOf("0", "1", "2", "3", "5", "8", "13", "21", "34", "55", "89", "?").mapIndexed { index, s ->
        Option(index,s)
    })

    @Serializable
    class TShirt : GameMode(listOf("0", "1", "2", "3", "5", "8", "13", "21", "34", "55", "89", "?").mapIndexed { index, s ->
        Option(index,s)
    })
    @Serializable
    class Custom(val list2: List<String> = list3) : GameMode(list2.mapIndexed { index, s ->
        Option(index, s)
    })
}

@Serializable
sealed class PlayerRequestEvent {

    @Serializable
    class CreateRoom(val gameConfig: GameConfig) : PlayerRequestEvent()

    @Serializable
    class JoinGameRequest(val playerName:String,val password:String) : PlayerRequestEvent()

    @Serializable
    class Voted(val voteId: Int) : PlayerRequestEvent()

    @Serializable
    class ShowVotes : PlayerRequestEvent()

    @Serializable
    class ResetRequest : PlayerRequestEvent()

    @Serializable
    class ChangeConfig(val gameConfig: GameConfig) : PlayerRequestEvent()
}

@Serializable
sealed class ServerRequest(val id: Int) {

    @Serializable
    class PlayerRequest(val playerRequestEvent: PlayerRequestEvent) : ServerRequest(
        ServerRequestTypes.PLAYEREVENT.ordinal
    )

    @Serializable
    class MessageRequest : ServerRequest(ServerRequestTypes.MESSAGE.ordinal)

}

