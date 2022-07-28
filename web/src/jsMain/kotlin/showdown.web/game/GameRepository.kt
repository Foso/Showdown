package showdown.web.game

import com.badoo.reaktive.completable.Completable
import com.badoo.reaktive.observable.Observable
import com.badoo.reaktive.subject.behavior.BehaviorSubject
import de.jensklingenberg.showdown.model.*
import de.jensklingenberg.showdown.model.api.clientrequest.JoinGame
import de.jensklingenberg.showdown.model.api.clientrequest.NewGameConfig
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
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
        val req = Json.encodeToString(Request(PATHS.SHOWVOTES.path))
        gameApiClient.sendMessage(req)
    }

    override fun onSelectedVote(voteId: Int) {
        val req = Json.encodeToString(Request(PATHS.VOTEPATH.path, voteId.toString()))
        gameApiClient.sendMessage(req)
    }

    override fun changeConfig(newGameConfig: NewGameConfig) {
        val req =
            Json.encodeToString(Request(PATHS.ROOMCONFIGUPDATE.path, Json.encodeToString(newGameConfig)))
        gameApiClient.sendMessage(req)
    }

    override fun changeRoomPassword(password: String) {
        val req = Json.encodeToString(Request(PATHS.SETROOMPASSSWORDPATH.path, password))
        gameApiClient.sendMessage(req)
    }

    override fun getPlayerName(): String {
        return playerName
    }

    override fun getRoomPassword(): String {
        return roomPassword
    }

    override fun setAutoReveal(enabled: Boolean) {
        val req = Json.encodeToString(Request(PATHS.SETAUTOREVEALPATH.path, Json.encodeToString(enabled)))
        gameApiClient.sendMessage(req)
    }

    override fun setSpectatorStatus(enabled: Boolean) {
        val req = Json.encodeToString(Request(PATHS.SPECTATORPATH.path, Json.encodeToString(enabled)))
        gameApiClient.sendMessage(req)
    }

    override fun setAnonymVote(enabled: Boolean) {
        val req = Json.encodeToString(Request(PATHS.SETANONYMVOTES.path, Json.encodeToString(enabled)))
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

    override fun joinRoom(name: String, password: String, isSpectator: Boolean) {
        playerName = name
        roomPassword = password
        val req = Request(
            PATHS.JOINROOM.path, Json.encodeToString(JoinGame(
                name,
                password,
                isSpectator
            ))
        )

        gameApiClient.sendMessage(Json.encodeToString(req))
    }


    override fun requestReset() {
        val req = Json.encodeToString(Request(PATHS.RESTARTPATH.path, ""))
        gameApiClient.sendMessage(req)
    }

    override fun observeErrors(): Observable<ShowdownError?> = errorSubject


    override fun onGameStateChanged(gameState: GameState) {
        gameStateSubject.onNext(gameState)
    }


    override fun onError(errorEvent: ShowdownError) {

        errorSubject.onNext(errorEvent)
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

