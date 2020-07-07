package de.jensklingenberg.showdown.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration


@Serializable
enum class ServerRequestTypes {
    PLAYEREVENT,
    UNKNOWN
}

val list3: List<String> = listOf("Hund", "Katze", "Maus", "Bär", "Tiger")

@Serializable
sealed class VoteOptions(open val options: List<Option>) {
    @Serializable
    class Fibo : VoteOptions(listOf("0", "1", "2", "3", "5", "8", "13", "21", "34", "55", "89", "?").mapIndexed { index, s ->
        Option(index, s)
    })

    @Serializable
    class TShirt : VoteOptions(listOf("xxs", "xs", "s", "m", "l", "xl", "xxl", "?").mapIndexed { index, s ->
        Option(index, s)
    })

    @Serializable
    class Custom(val list2: List<String> = list3) : VoteOptions(list2.mapIndexed { index, s ->
        Option(index, s)
    })
}

@Serializable
sealed class PlayerRequestEvent {

    @Serializable
    class JoinGameRequest(val playerName: String, val roomPassword: String) : PlayerRequestEvent()

    @Serializable
    class Voted(val voteId: Int) : PlayerRequestEvent()

    @Serializable
    class ShowVotes : PlayerRequestEvent()

    @Serializable
    class RestartRequest : PlayerRequestEvent()

    @Serializable
    class ChangeConfig(val clientGameConfig: ClientGameConfig) : PlayerRequestEvent()
}

@Serializable
sealed class ServerRequest(val id: Int) {

    @Serializable
    class PlayerRequest(val playerRequestEvent: PlayerRequestEvent) : ServerRequest(ServerRequestTypes.PLAYEREVENT.ordinal)

}



fun getServerCommandType(json: String): ServerRequestTypes {
    return ServerRequestTypes.values().firstOrNull() {
        json.startsWith("{\"id\":${it.ordinal}")
    } ?: ServerRequestTypes.UNKNOWN
}

fun getServerRequest(json: String): ServerRequest? {
    val type = getServerCommandType(json)
    return when (type) {
        ServerRequestTypes.PLAYEREVENT -> {
            getPlayerRequest(json)
        }

        else -> {
            null
        }
    }
}


fun ServerRequest.PlayerRequest.toJson(): String {
    return Json(JsonConfiguration.Stable).stringify(ServerRequest.PlayerRequest.serializer(), this)
}
fun getPlayerRequest(jsonStr: String): ServerRequest.PlayerRequest {
    val json =
            Json(JsonConfiguration.Stable)
    return json.parse(ServerRequest.PlayerRequest.serializer(), jsonStr)

}


