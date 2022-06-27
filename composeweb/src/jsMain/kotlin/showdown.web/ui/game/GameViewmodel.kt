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
import de.jensklingenberg.showdown.model.ShowdownError
import showdown.web.Application
import showdown.web.debugLog
import showdown.web.game.GameDataSource

class GameViewmodel(
   // private val view: GameContract.View,
    private val gameDataSource: GameDataSource = Application.gameDataSource
) : GameContract.Viewmodel {

    private var showConnectionError: Boolean = false
    private var requestRoomPassword: Boolean = false
    override var showEntryPopup: MutableState<Boolean> = mutableStateOf(true)
    private val compositeDisposable = CompositeDisposable()
    private var playerName: String = ""
    var players: MutableState<List<Member>> = mutableStateOf(emptyList())

    override val starEstimationTimerSubject: BehaviorSubject<Boolean> = BehaviorSubject(false)
    val gameStateSubject: MutableState<GameState> = mutableStateOf(GameState.NotStarted)

    override fun onCreate() {

        observeErrors()
        observeMessage()
        observeGameState()

    }

    private fun observeGameState() {
        gameDataSource.observeGameState().subscribe(onNext = { gameState ->
            when (gameState) {
                GameState.NotStarted->{}
                is GameState.PlayerListUpdate -> {
                    players.value = gameState.members
                }
                is GameState.Started -> {
                    starEstimationTimerSubject.onNext(true)

                        this.requestRoomPassword = false


                }

                is GameState.ShowVotes -> {
                    starEstimationTimerSubject.onNext(false)
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
        gameDataSource.onSelectedVote(voteId)
    }


}