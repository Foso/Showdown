package showdown.web.game

import com.badoo.reaktive.completable.Completable
import com.badoo.reaktive.observable.Observable
import com.badoo.reaktive.subject.behavior.BehaviorSubject
import de.jensklingenberg.showdown.model.ClientGameConfig
import de.jensklingenberg.showdown.model.GameState
import de.jensklingenberg.showdown.model.JOINROOMPATH
import de.jensklingenberg.showdown.model.PATHS
import de.jensklingenberg.showdown.model.RESTARTPATH
import de.jensklingenberg.showdown.model.Request
import de.jensklingenberg.showdown.model.SETANONYMVOTES
import de.jensklingenberg.showdown.model.SETAUTOREVEALPATH
import de.jensklingenberg.showdown.model.SETROOMPASSSWORDPATH
import de.jensklingenberg.showdown.model.SHOWVOTESPATH
import de.jensklingenberg.showdown.model.ServerResponse
import de.jensklingenberg.showdown.model.ShowdownError
import de.jensklingenberg.showdown.model.VOTEPATH
import de.jensklingenberg.showdown.model.api.clientrequest.JoinGame
import de.jensklingenberg.showdown.model.api.clientrequest.NewGameConfig
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import showdown.web.common.stringify
import showdown.web.network.GameApiClient
import showdown.web.network.NetworkApiObserver


class GameRepository(private val gameApiClient: GameApiClient) : GameDataSource, NetworkApiObserver {

    private val gameStateSubject: BehaviorSubject<GameState> = BehaviorSubject(GameState.NotStarted)
    private val errorSubject: BehaviorSubject<ShowdownError?> = BehaviorSubject(null)
    private val messageSubject: BehaviorSubject<String> = BehaviorSubject("")
    private val configUpdateSubject: BehaviorSubject<ClientGameConfig?> = BehaviorSubject(null)
    private val spectatorStatusSubject: BehaviorSubject<Boolean> = BehaviorSubject(false)

    private var playerName: String = ""
    private var roomPassword: String = ""

    override fun connectToServer(): Completable {
        return gameApiClient.start(this)
    }

    override fun showVotes() {
        val req = Request(SHOWVOTESPATH).stringify()
        gameApiClient.sendMessage(req)
    }

    override fun onSelectedVote(voteId: Int) {
        val req = Request(VOTEPATH, voteId.toString()).stringify()
        gameApiClient.sendMessage(req)
    }

    override fun changeConfig(newGameConfig: NewGameConfig) {
        val req = Request(PATHS.ROOMCONFIGUPDATE.path, newGameConfig.stringify()).stringify()
        gameApiClient.sendMessage(req)
    }

    override fun changeRoomPassword(password: String) {
        val req = Request(SETROOMPASSSWORDPATH, password).stringify()
        gameApiClient.sendMessage(req)
    }

    override fun getPlayerName(): String {
        return playerName
    }

    override fun getRoomPassword(): String {
        return roomPassword
    }

    override fun setAutoReveal(enabled: Boolean) {
        val req = Request(SETAUTOREVEALPATH, enabled.stringify()).stringify()
        gameApiClient.sendMessage(req)
    }

    override fun setSpectatorStatus(enabled: Boolean) {
        val req = Request(PATHS.SPECTATORPATH.path, enabled.stringify()).stringify()
        gameApiClient.sendMessage(req)
    }

    override fun setAnonymVote(enabled: Boolean) {
        val req = Request(SETANONYMVOTES, enabled.stringify()).stringify()
        gameApiClient.sendMessage(req)
    }


    override fun observeGameState(): Observable<GameState> = gameStateSubject
    override fun observeMessage(): Observable<String> {
        return messageSubject
    }

    override fun observeRoomConfig(): Observable<ClientGameConfig?> = configUpdateSubject
    override fun observeSpectatorStatus(): Observable<Boolean> {
        return spectatorStatusSubject
    }

    override fun joinRoom(name: String, password: String) {
        playerName = name
        roomPassword = password
        val req = Request(
            JOINROOMPATH, JoinGame(
                name,
                password
            ).stringify()
        )

        gameApiClient.sendMessage(JSON.stringify(req))
    }

    override fun requestReset() {
        val req = Request(RESTARTPATH).stringify()
        gameApiClient.sendMessage(req)
    }

    override fun observeErrors(): Observable<ShowdownError?> = errorSubject


    override fun onGameStateChanged(gameState: GameState) {
        gameStateSubject.onNext(gameState)
    }


    override fun onError(errorEvent: ServerResponse.ErrorEvent) {
        errorSubject.onNext(errorEvent.error)
    }

    override fun onMessageEvent(message: String) {
        messageSubject.onNext(message)
    }

    override fun onConfigUpdated(clientGameConfig: ClientGameConfig) {
        configUpdateSubject.onNext(clientGameConfig)

    }

    override fun onSpectatorStatusChanged(isSpectator: Boolean) {
        spectatorStatusSubject.onNext(isSpectator)
    }

}

