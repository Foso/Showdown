package showdown.web.game

import com.badoo.reaktive.observable.Observable
import com.badoo.reaktive.subject.behavior.BehaviorSubject
import de.jensklingenberg.showdown.model.*


sealed class GameEvent{
    class Reveal(message:String): GameEvent()
}

class GameRepository(private val gameApiHandler: GameApiHandler) : GameDataSource, NetworkApiObserver {

    private var activePlayerId: Player? = null
    private val warriorSubject: BehaviorSubject<List<Warrior>> = BehaviorSubject(emptyList())

    private val gameStateSubject: BehaviorSubject<GameState> = BehaviorSubject(GameState.NotConnected)
    private val playerSubject: BehaviorSubject<Int> = BehaviorSubject(-1)

    override fun getPlayer(): Player? = activePlayerId

    override fun prepareGame() {
        gameApiHandler.start(this)
    }

    override fun createNewRoom(roomName: String) {
        val jsonData = ServerRequest.PlayerRequest(PlayerRequestEvent.CreateRoom(GameMode.Fibo(),roomName)).toJson()
        gameApiHandler.sendMessage(jsonData)
    }

    override fun revealCards() {
        val jsonData = ServerRequest.PlayerRequest(PlayerRequestEvent.ShowVotes()).toJson()
        gameApiHandler.sendMessage(jsonData)
    }

    override fun onSelectedVote(i: Int) {
        val jsonData = ServerRequest.PlayerRequest(PlayerRequestEvent.Voted(i)).toJson()
        gameApiHandler.sendMessage(jsonData)
    }

    override fun observeGameState(): Observable<GameState> = gameStateSubject
    override fun observePlayer(): Observable<Int> = playerSubject
    override fun observeMap(): Observable<List<Warrior>> = warriorSubject



    override fun join() {
        val jsonData = ServerRequest.PlayerRequest(PlayerRequestEvent.JoinGameRequest("Jens")).toJson()
        gameApiHandler.sendMessage(jsonData)
    }

    override fun requestReset() {
        val jsonData = ServerRequest.ResetRequest().toJson()
        gameApiHandler.sendMessage(jsonData)
    }


    override fun onGameStateChanged(gameState: GameState) {
        gameStateSubject.onNext(gameState)
    }

    override fun onPlayerEventChanged(gameResponse: PlayerResponseEvent) {
        when (gameResponse) {
            is PlayerResponseEvent.JOINED -> {
                activePlayerId = gameResponse.yourPlayer
                playerSubject.onNext(gameResponse.yourPlayer.id)
            }
        }
    }

    override fun onError(gameJoined: ServerResponse.ErrorEvent) {

    }

    override fun onPlayerCardRevealed(gameState: ServerResponse.PlayerVotes) {
        gameStateSubject.onNext(GameState.Showdown(gameState.message))
    }
}

