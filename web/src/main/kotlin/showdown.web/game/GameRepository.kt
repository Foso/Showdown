package showdown.web.game

import com.badoo.reaktive.completable.Completable
import com.badoo.reaktive.observable.Observable
import com.badoo.reaktive.subject.behavior.BehaviorSubject
import de.jensklingenberg.showdown.model.*
import showdown.web.network.GameApiHandler
import showdown.web.network.NetworkApiObserver

enum class WebSocketConnectionType{
    INIT,OPEN,CLOSED
}

class GameRepository(private val gameApiHandler: GameApiHandler) : GameDataSource, NetworkApiObserver {

    private val gameStateSubject: BehaviorSubject<GameState> = BehaviorSubject(GameState.NotStarted)
    private val errorSubject: BehaviorSubject<ShowdownError?> = BehaviorSubject(null)

    override fun connectToServer(): Completable {
       return gameApiHandler.start(this)
    }

    override fun showVotes() {
        val jsonData = ServerRequest.ShowVotes.toJson()
        gameApiHandler.sendMessage(jsonData)
    }

    override fun onSelectedVote(voteId: Int) {
        val jsonData = ServerRequest.Voted(voteId).toJson()
        gameApiHandler.sendMessage(jsonData)
    }

    override fun changeConfig(clientGameConfig: ClientGameConfig) {

        //val jsonData = ServerRequest.ChangeConfig(clientGameConfig).toJson()
        val hallo= Hallo("Jens")
     val json=   JSON.stringify(hallo)

        val req = Request("/hallo",json).toJson()

        gameApiHandler.sendMessage(req)
    }

    override fun changeRoomPassword(password: String) {

        val req = Request(SETROOMPASSSWORDPATH,password).toJson()
        gameApiHandler.sendMessage(req)
    }

    override fun observeGameState(): Observable<GameState> = gameStateSubject

    override fun joinRoom(name:String,password:String) {
        val jsonData = ServerRequest.JoinGameRequest(name, password).toJson()
        gameApiHandler.sendMessage(jsonData)
    }

    override fun requestReset() {
        val jsonData = ServerRequest.RestartRequest.toJson()
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

