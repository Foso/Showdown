package showdown.web.game

import de.jensklingenberg.showdown.model.*
import de.jensklingenberg.showdown.model.api.clientrequest.JoinGame
import de.jensklingenberg.showdown.model.api.clientrequest.NewGameConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import showdown.web.network.Either
import showdown.web.network.GameApiClient
import showdown.web.network.NetworkApiObserver


class GameRepository(private val gameApiClient: GameApiClient) : GameDataSource, NetworkApiObserver {

    private val gameStateFlow = MutableStateFlow<GameState>(GameState.NotStarted)
    private val errorFlow = MutableStateFlow<ShowdownError?>(null)

    private val messageFlow = MutableStateFlow<String>("")

    private val configUpdateFlow = MutableStateFlow<ClientGameConfig?>(null)

    private val spectatorStatusFlow = MutableStateFlow(false)

    private var playerName: String = ""
    private var roomPassword: String = ""

    override fun connectToServer(): Flow<Either> {
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


    override fun observeGameState(): StateFlow<GameState> = gameStateFlow
    override fun observeMessage(): StateFlow<String> {
        return messageFlow
    }

    override fun observeRoomConfig(): StateFlow<ClientGameConfig?> = configUpdateFlow
    override fun observeSpectatorStatus(): StateFlow<Boolean> {
        return spectatorStatusFlow
    }

    override fun joinRoom(name: String, password: String, isSpectator: Boolean) {
        playerName = name
        roomPassword = password
        val req = Request(
            PATHS.JOINROOM.path, Json.encodeToString(
                JoinGame(
                    name,
                    password,
                    isSpectator
                )
            )
        )

        gameApiClient.sendMessage(Json.encodeToString(req))
    }


    override fun requestReset() {
        val req = Json.encodeToString(Request(PATHS.RESTARTPATH.path, ""))
        gameApiClient.sendMessage(req)
    }

    override fun observeErrors(): StateFlow<ShowdownError?> = errorFlow


    override fun onGameStateChanged(gameState: GameState) {
        gameStateFlow.value = gameState
    }


    override fun onError(errorEvent: ShowdownError) {
        errorFlow.value = errorEvent
    }

    override fun onMessageEvent(message: String) {
        messageFlow.value = message
    }

    override fun onConfigUpdated(clientGameConfig: ClientGameConfig) {
        configUpdateFlow.value = (clientGameConfig)
    }

    override fun onSpectatorStatusChanged(isSpectator: Boolean) {
        //spectatorStatusSubject.onNext(isSpectator)
        spectatorStatusFlow.value = isSpectator
    }

}

