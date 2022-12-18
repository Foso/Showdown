package showdown.web.ui.game.voting

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.badoo.reaktive.completable.subscribe
import com.badoo.reaktive.disposable.CompositeDisposable
import com.badoo.reaktive.disposable.addTo
import de.jensklingenberg.showdown.model.GameState
import de.jensklingenberg.showdown.model.Member
import de.jensklingenberg.showdown.model.Result
import de.jensklingenberg.showdown.model.ShowdownError
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import showdown.web.Application
import showdown.web.debugLog
import showdown.web.game.GameDataSource


class GameViewmodel(
    private val gameDataSource: GameDataSource = Application.gameDataSource
) : GameViewmodelItf {
    private val scope = MainScope()

    override var isRoomPasswordNeeded: MutableState<Boolean> = mutableStateOf(false)
    override var isSpectator: MutableState<Boolean> = mutableStateOf(false)
    override var options: MutableState<List<String>> = mutableStateOf(emptyList())
    override var results: MutableState<List<Result>> = mutableStateOf(emptyList())
    override var isRegistration: MutableState<Boolean> = mutableStateOf(true)
    override var members: MutableState<List<Member>> = mutableStateOf(emptyList())
    override var selectedOption: MutableState<Int> = mutableStateOf(-1)
    override var isConnectionError: MutableState<Boolean> = mutableStateOf(false)

    private val compositeDisposable = CompositeDisposable()
    private var playerName: String = ""


    override fun onCreate() {

        observeErrors()
        observeMessage()
        observeGameState()
        observeSpectatorStatus()

    }

    private fun observeSpectatorStatus() {
        scope.launch {
            gameDataSource.observeSpectatorStatus().collect{
                isSpectator.value = it
            }
        }

    }

    private fun observeGameState() {

        scope.launch {
            gameDataSource.observeGameState().collect { newState->
                isConnectionError.value = false
                when (newState) {
                    GameState.NotStarted -> {}
                    is GameState.PlayerListUpdate -> {
                        members.value = newState.members
                    }
                    is GameState.Started -> {
                        val config = newState.clientGameConfig
                        selectedOption.value = -1
                        options.value = config.voteOptions
                        results.value = emptyList()

                        isRoomPasswordNeeded.value = false

                    }

                    is GameState.ShowVotes -> {
                        results.value = newState.results
                    }

                }
            }
        }

    }


    private fun observeMessage() {
       scope.launch {
           gameDataSource.observeMessage().collect{
               // view.showInfoPopup(it) //TODO:
           }
       }
    }

    private fun observeErrors() {

        scope.launch {
            gameDataSource.observeErrors().collect{ error ->
                when (error) {
                    ShowdownError.NotAuthorizedError -> {
                        isRoomPasswordNeeded.value = true
                    }
                    ShowdownError.NoConnectionError -> {
                        debugLog("No Connection")
                        isConnectionError.value = true
                    }
                    else -> {}
                }
            }
        }
    }

    private fun connectToServer(playerName: String, password: String, isSpectator: Boolean) {
        gameDataSource.connectToServer().subscribe(
            onComplete = {
                debugLog("ConnectToServer onComplete")
                gameDataSource.joinRoom(playerName, password, isSpectator)

                isRegistration.value = false
                this.isConnectionError.value = false


            },
            onError = {
                debugLog("connectToServer: onError" + it.message)

                this.isRegistration.value = true

            }
        ).addTo(compositeDisposable)
    }

    override fun onDestroy() {
        compositeDisposable.clear()
    }


    override fun joinGame(playerName: String, password: String, isSpectator: Boolean) {
        debugLog("JoinGame")
        if (this.playerName.isEmpty()) {
            this.playerName = playerName
        }

        connectToServer(this.playerName, password, isSpectator)

    }

    override fun onEntryPopupClosed() {
        isRegistration.value = false
    }

    override fun onSelectedVote(voteId: Int) {
        val votedOption = if (voteId >= options.value.size) {
            options.value.indices.random()
        } else {
            voteId
        }
        selectedOption.value = voteId
        gameDataSource.onSelectedVote(votedOption)
    }

    override fun setSpectatorStatus(enable: Boolean) {
        gameDataSource.setSpectatorStatus(enable)
    }


}