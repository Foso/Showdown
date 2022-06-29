package showdown.web.ui.game

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.badoo.reaktive.completable.subscribe
import com.badoo.reaktive.disposable.CompositeDisposable
import com.badoo.reaktive.disposable.addTo
import com.badoo.reaktive.observable.subscribe
import com.badoo.reaktive.subject.behavior.BehaviorSubject
import de.jensklingenberg.showdown.model.GameState
import de.jensklingenberg.showdown.model.Member
import de.jensklingenberg.showdown.model.Result

import de.jensklingenberg.showdown.model.ShowdownError
import showdown.web.Application
import showdown.web.debugLog
import showdown.web.game.GameDataSource
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

    private var showConnectionError: Boolean = false
    private var requestRoomPassword: Boolean = false
    override var isSpectator: MutableState<Boolean> = mutableStateOf(false)
    override var options: MutableState<List<String>> = mutableStateOf(emptyList())

    var results: MutableState<List<Result>> = mutableStateOf(emptyList())
    override var showEntryPopup: MutableState<Boolean> = mutableStateOf(true)
    private val compositeDisposable = CompositeDisposable()
    private var playerName: String = ""
    var members: MutableState<List<Member>> = mutableStateOf(emptyList())
    override var selectedOption: MutableState<Int> = mutableStateOf(-1)


    override val starEstimationTimerSubject: BehaviorSubject<Boolean> = BehaviorSubject(false)
    val gameStateSubject: MutableState<GameState> = mutableStateOf(GameState.NotStarted)


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
    }

    private fun observeSpectatorStatus() {
        gameDataSource.observeSpectatorStatus().subscribe(onNext = {
            isSpectator.value = it
        }).addTo(compositeDisposable)
    }

    private fun observeGameState() {
        gameDataSource.observeGameState().subscribe(onNext = { newState ->
            when (newState) {
                GameState.NotStarted -> {}
                is GameState.PlayerListUpdate -> {
                    members.value = newState.members
                }
                is GameState.Started -> {
                    val config = newState.clientGameConfig
                    selectedOption.value = -1
                    this.options.value = config.voteOptions
                    this.results.value = emptyList()
                    //this.selectedOptionId = -1

                    starEstimationTimerSubject.onNext(true)

                    this.requestRoomPassword = false


                }

                is GameState.ShowVotes -> {
                    starEstimationTimerSubject.onNext(false)
                    results.value = newState.results
                }

            }
        }).addTo(compositeDisposable)
    }


    private fun observeMessage() {
        gameDataSource.observeMessage().subscribe(onNext = {
            // view.showInfoPopup(it)
        }).addTo(compositeDisposable)
    }

    private fun observeErrors() {
        gameDataSource.observeErrors().subscribe(onNext = { error ->
            when (error) {
                ShowdownError.NotAuthorizedError -> {

                    this.requestRoomPassword = true

                }
                ShowdownError.NoConnectionError -> {
                    debugLog("No Connection")

                    this.showEntryPopup.value = true
                    this.showConnectionError = false

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
                this.showConnectionError = false


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
        this.playerName = playerName
        connectToServer(playerName, password, isSpectator)

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