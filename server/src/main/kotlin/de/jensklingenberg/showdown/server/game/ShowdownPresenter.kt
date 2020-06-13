package de.jensklingenberg.showdown.server.game


import de.jensklingenberg.showdown.model.*

data class Room(val name: String, val password: String)

data class TempVote(val voteId:Int,val playerId:Int)

sealed class GameMode(open val list: List<String>) {
    class Fibo : GameMode(listOf("0", "1", "2", "3", "5", "8", "13", "21", "34", "55", "89", "?"))
    class Custom(override val list: List<String> = listOf("Hund", "Katze", "Maus", "Bär", "Tiger")) : GameMode(list)
}

class ShowdownPresenter(private val server: ShowdownContract.RpsGameServer) : ShowdownContract.Presenter {

    private val playerList = mutableListOf<Player>()
    private var gameMode: GameMode = GameMode.Custom()
    private val tempVoteMap = mutableMapOf<Int, Int>()
    private val tempVotes = arrayListOf<TempVote>()

    override fun onReset() {
        sendGameStateChanged(GameState.Started)
    }

    override fun onAddPlayer(sessionId: String, name: String) {
        val newPlayerID = playerList.size
        val player = Player(newPlayerID, name)
        playerList.add(player)

        server.onPlayerAdded(sessionId, player)

        val json2 = ServerResponse.PlayerEvent(PlayerResponseEvent.JOINED(player)).toJson()
        server.sendData(player.id, json2)

        sendMembers()
        sendOptions()
    }



    override fun onPlayerVoted(playerId: Int, voteId: Int) {
        tempVotes.add(TempVote(voteId,playerId))
        tempVoteMap[playerId] = voteId
        sendMembers()
    }

    override fun onPlayerRejoined(sessionId: String, name: String) {
        sendMembers()
        sendOptions()
    }

    private fun sendMembers() {
        val votesList = playerList.map {

            val voted = tempVoteMap.containsKey(it.id)

            val symbol = if (voted) {
                "Voted"
            } else {
                "Not voted"
            }

            ClientVote(it.name, symbol)
        }

        val json2 = ServerResponse.GameStateChanged(GameState.VoteUpdate(votesList)).toJson()
        server.sendBroadcast(json2)
    }

    override fun showVotes() {

      val t =  tempVotes.groupBy { it.voteId }.map {
          val votedId = it.key
          val tempVotesList = it.value
          val voteText = gameMode.list[votedId]
          val voters = tempVotesList.joinToString(separator = ", ") {temp->
              playerList.find { it.id == temp.playerId}?.name?:""}+ " (${tempVotesList.size})"
          Result(voteText,voters)
      }

       // val res = listOf(Result("Bär", "Jens, Marco"), Result("Maus", "Bert"))

        val json2 = ServerResponse.PlayerVotes(t).toJson()
        server.sendBroadcast(json2)
        sendGameStateChanged(GameState.Showdown(t))

    }

    private fun sendOptions() {
        val symbol = when (gameMode) {
            is GameMode.Fibo -> {
                gameMode.list.mapIndexed { index, s ->
                    Option(index, s)
                }
            }
            is GameMode.Custom -> {
                gameMode.list.mapIndexed { index, s ->
                    Option(index, "$index: $s")
                }
            }
        }

        val json2 = ServerResponse.GameStateChanged(GameState.OptionsUpdate(symbol)).toJson()
        server.sendBroadcast(json2)
    }

    private fun sendGameStateChanged(gameState: GameState) {
        val json2 = ServerResponse.GameStateChanged(gameState).toJson()
        server.sendBroadcast(json2)
    }


}

