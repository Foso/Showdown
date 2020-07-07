package showdown.web.game

import com.badoo.reaktive.observable.Observable
import com.badoo.reaktive.subject.behavior.BehaviorSubject
import de.jensklingenberg.showdown.model.*
import showdown.web.network.GameApiHandler
import showdown.web.network.NetworkApiObserver

class GameRepository(private val gameApiHandler: GameApiHandler) : GameDataSource, NetworkApiObserver {

    private val gameStateSubject: BehaviorSubject<GameState> = BehaviorSubject(GameState.NotConnected)
    private val errorSubject: BehaviorSubject<ShowdownError?> = BehaviorSubject(null)

    override fun prepareGame() {
        gameApiHandler.start(this)
    }

    override fun showVotes() {
        val jsonData = ServerRequest.PlayerRequest(PlayerRequestEvent.ShowVotes()).toJson()
        gameApiHandler.sendMessage(jsonData)
    }

    override fun onSelectedVote(voteId: Int) {
        val jsonData = ServerRequest.PlayerRequest(PlayerRequestEvent.Voted(voteId)).toJson()
        gameApiHandler.sendMessage(jsonData)
    }

    override fun changeConfig(clientGameConfig: ClientGameConfig) {
        val jsonData = ServerRequest.PlayerRequest(PlayerRequestEvent.ChangeConfig(clientGameConfig)).toJson()
        gameApiHandler.sendMessage(jsonData)
    }

    override fun observeGameState(): Observable<GameState> = gameStateSubject

    override fun joinRoom(name:String,password:String) {
        val jsonData = ServerRequest.PlayerRequest(PlayerRequestEvent.JoinGameRequest(name, "")).toJson()
        gameApiHandler.sendMessage(jsonData)
    }

    override fun requestReset() {
        val jsonData = ServerRequest.PlayerRequest(PlayerRequestEvent.RestartRequest()).toJson()
        gameApiHandler.sendMessage(jsonData)
    }

    override fun observeErrors(): Observable<ShowdownError?> = errorSubject


    override fun onGameStateChanged(gameState: GameState) {
        gameStateSubject.onNext(gameState)
    }

    override fun onPlayerEventChanged(gameResponse: PlayerResponseEvent) {
        when (gameResponse) {
            is PlayerResponseEvent.JOINED -> {

            }
        }
    }

    override fun onError(errorEvent: ServerResponse.ErrorEvent) {
        errorSubject.onNext(errorEvent.error)
    }

}

