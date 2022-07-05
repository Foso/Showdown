package showdown.web.ui.game.voting

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.badoo.reaktive.completable.subscribe
import com.badoo.reaktive.disposable.CompositeDisposable
import com.badoo.reaktive.disposable.addTo
import com.badoo.reaktive.observable.subscribe
import de.jensklingenberg.showdown.model.*
import de.jensklingenberg.showdown.model.api.clientrequest.NewGameConfig
import kotlinx.browser.window
import showdown.web.Application
import showdown.web.debugLog
import showdown.web.game.GameDataSource
import kotlin.js.Date
import kotlin.math.floor

val gameModeOptions: List<Pair<String, Int>>
    get() = listOf(
        "Fibonacci" to 0,
        "T-Shirt" to 1,
        "Modified Fibonacci" to 2,
        "Power of 2" to 3,

        "Custom" to 4
    )

class GameViewmodel(
    // private val view: GameContract.View,
    private val gameDataSource: GameDataSource = Application.gameDataSource
) : GameContract.Viewmodel {

    override var requestRoomPassword: MutableState<Boolean> = mutableStateOf(false)
    override var isSpectator: MutableState<Boolean> = mutableStateOf(false)
    override var options: MutableState<List<String>> = mutableStateOf(emptyList())
    override var results: MutableState<List<Result>> = mutableStateOf(emptyList())
    override var showEntryPopup: MutableState<Boolean> = mutableStateOf(true)
    private val compositeDisposable = CompositeDisposable()
    private var playerName: String = ""
    override var members: MutableState<List<Member>> = mutableStateOf(emptyList())
    override var selectedOption: MutableState<Int> = mutableStateOf(-1)
    override var autoReveal: MutableState<Boolean> = mutableStateOf(false)
    override var anonymResults: MutableState<Boolean> = mutableStateOf(false)
    override var showConnectionError: MutableState<Boolean> = mutableStateOf(false)
    private var gameStartTime = Date()
    override var timer: MutableState<Int> = mutableStateOf(0)
    override var showRoomPasswordSettingsDialog: MutableState<Boolean> = mutableStateOf(false)


    override fun reset() {
        gameDataSource.requestReset()
    }

    override fun showVotes() {
        gameDataSource.showVotes()
    }

    override fun onCreate() {

        observeErrors()
        observeMessage()
        observeGameState()
        observeSpectatorStatus()
        gameDataSource.observeRoomConfig().subscribe {
            it?.let {
                autoReveal.value = it.autoReveal
                anonymResults.value = it.anonymResults
            }
        }
        window.setInterval({
            val startDate = gameStartTime
            val endDate = Date()

            val diffSecs = (endDate.getTime() - startDate.getTime()) / 1000
            timer.value = (floor(diffSecs).toInt())
        }, 1000)
    }

    private fun observeSpectatorStatus() {
        gameDataSource.observeSpectatorStatus().subscribe(onNext = {
            isSpectator.value = it
        }).addTo(compositeDisposable)
    }

    private fun observeGameState() {
        gameDataSource.observeGameState().subscribe(onNext = { newState ->
            showConnectionError.value = false
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

                    this.requestRoomPassword.value = false

                    gameStartTime = Date(config.createdAt.toDouble())
                }

                is GameState.ShowVotes -> {
                    results.value = newState.results
                }

            }
        }).addTo(compositeDisposable)
    }


    private fun observeMessage() {
        gameDataSource.observeMessage().subscribe(onNext = {
            // view.showInfoPopup(it) //TODO:
        }).addTo(compositeDisposable)
    }

    private fun observeErrors() {
        gameDataSource.observeErrors().subscribe(onNext = { error ->
            when (error) {
                ShowdownError.NotAuthorizedError -> {

                    this.requestRoomPassword.value = true

                }
                ShowdownError.NoConnectionError -> {
                    debugLog("No Connection")

                    this.showEntryPopup.value = true
                    this.showConnectionError.value = false

                }
                else -> {}
            }
        }).addTo(compositeDisposable)
    }

    private fun connectToServer(playerName: String, password: String, isSpectator: Boolean) {
        gameDataSource.connectToServer().subscribe(
            onComplete = {
                debugLog("ConnectToServer onComplete")
                gameDataSource.joinRoom(playerName, password, isSpectator)

                showEntryPopup.value = false
                this.showConnectionError.value = false


            },
            onError = {
                debugLog("connectToServer: onError" + it.message)

                this.showEntryPopup.value = true

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

    override fun changeConfig(gameModeId: Int, gameOptions: String) {
        val mode = when (gameModeId) {
            0 -> {
                fibo
            }
            1 -> {
                tshirtSizesList
            }
            2 -> {
                modFibo
            }
            3 -> {
                powerOf2
            }
            4 -> {
                gameOptions.split(";")
            }
            else -> fibo
        }
        val config =
            NewGameConfig(voteOptions = mode)
        gameDataSource.changeConfig(config)
    }

    override fun setAutoReveal(any: Boolean) {
        gameDataSource.setAutoReveal(any)
    }

    override fun setAnonymVote(any: Boolean) {
        gameDataSource.setAnonymVote(any)
    }

    override fun onEntryPopupClosed() {
        showEntryPopup.value = false
    }

    override fun changeRoomPassword(password: String) {
        gameDataSource.changeRoomPassword(password)
    }

    override fun onSelectedVote(voteId: Int) {
        selectedOption.value = voteId
        gameDataSource.onSelectedVote(voteId)
    }

    override fun setSpectatorStatus(b: Boolean) {
        gameDataSource.setSpectatorStatus(b)
    }


}