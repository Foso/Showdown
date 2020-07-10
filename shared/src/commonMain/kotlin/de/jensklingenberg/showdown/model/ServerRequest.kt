package de.jensklingenberg.showdown.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration




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
enum class ServerRequestTypes {
    PLAYEREVENT,
    JoinGameRequest,
    Voted,
    RestartRequest,
    ChangeConfig,
    ShowVotes,
    UNKNOWN
}

@Serializable
sealed class ServerRequest(val id: Int) {

    @Serializable
    class JoinGameRequest(val playerName: String, val roomPassword: String) : ServerRequest(ServerRequestTypes.JoinGameRequest.ordinal)

    @Serializable
    class Voted(val voteId: Int) : ServerRequest(ServerRequestTypes.Voted.ordinal)

    /**
     * A request to restart the game
     */
    @Serializable
    object RestartRequest : ServerRequest(ServerRequestTypes.RestartRequest.ordinal)

    /**
     * Send a new [ClientGameConfig] to the server
     */
    @Serializable
    class ChangeConfig(val clientGameConfig: ClientGameConfig) : ServerRequest(ServerRequestTypes.ChangeConfig.ordinal)

    /**
     * Request to view the Votes
     */
    @Serializable
    object ShowVotes : ServerRequest(ServerRequestTypes.ShowVotes.ordinal)
}




fun getServerCommandType(json: String): ServerRequestTypes {
    return ServerRequestTypes.values().firstOrNull() {
        json.contains("\"id\":${it.ordinal}")
    } ?: ServerRequestTypes.UNKNOWN
}

fun getServerRequest(json: String): ServerRequest? {
    val type = getServerCommandType(json)
    return if(type==ServerRequestTypes.UNKNOWN){
        null
    }else{
        getPlayerRequest(json)
    }
}


fun ServerRequest.toJson(): String {
    return Json(JsonConfiguration.Stable).stringify(ServerRequest.serializer(), this)
}
fun getPlayerRequest(jsonStr: String): ServerRequest {
    val json =
            Json(JsonConfiguration.Stable)
    return json.parse(ServerRequest.serializer(), jsonStr)

}


