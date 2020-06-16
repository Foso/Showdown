package showdown.web.game

import com.badoo.reaktive.observable.Observable
import com.badoo.reaktive.subject.behavior.BehaviorSubject
import de.jensklingenberg.showdown.model.*



class GameRepository(private val gameApiHandler: GameApiHandler) : GameDataSource, NetworkApiObserver {

    private val gameStateSubject: BehaviorSubject<GameState> = BehaviorSubject(GameState.NotConnected)


    override fun prepareGame() {
        gameApiHandler.start(this)
    }

    override fun createNewRoom(roomName: String) {
        val jsonData = ServerRequest.PlayerRequest(PlayerRequestEvent.CreateRoom(GameConfig( GameMode.Fibo()))).toJson()
        gameApiHandler.sendMessage(jsonData)
    }

    override fun showVotes() {
        val jsonData = ServerRequest.PlayerRequest(PlayerRequestEvent.ShowVotes()).toJson()
        gameApiHandler.sendMessage(jsonData)
    }

    override fun onSelectedVote(i: Int) {
        val jsonData = ServerRequest.PlayerRequest(PlayerRequestEvent.Voted(i)).toJson()
        gameApiHandler.sendMessage(jsonData)
    }

    override fun changeConfig(gameConfig: GameConfig) {
        val jsonData = ServerRequest.PlayerRequest(PlayerRequestEvent.ChangeConfig(gameConfig)).toJson()
        gameApiHandler.sendMessage(jsonData)
    }

    override fun observeGameState(): Observable<GameState> = gameStateSubject

    override fun joinRoom(name:String) {
        console.log("joinRoom+"+name)

        val jsonData = ServerRequest.PlayerRequest(PlayerRequestEvent.JoinGameRequest(name,"geheim")).toJson()
        gameApiHandler.sendMessage(jsonData)
    }

    override fun requestReset() {
        val jsonData = ServerRequest.PlayerRequest(PlayerRequestEvent.ResetRequest()).toJson()
        gameApiHandler.sendMessage(jsonData)
    }


    override fun onGameStateChanged(gameState: GameState) {
        gameStateSubject.onNext(gameState)
    }

    override fun onPlayerEventChanged(gameResponse: PlayerResponseEvent) {
        when (gameResponse) {
            is PlayerResponseEvent.JOINED -> {

            }
        }
    }

    override fun onError(gameJoined: ServerResponse.ErrorEvent) {

    }

}

