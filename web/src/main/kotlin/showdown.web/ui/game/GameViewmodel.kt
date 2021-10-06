package showdown.web.ui.game

import com.badoo.reaktive.completable.subscribe
import com.badoo.reaktive.disposable.CompositeDisposable
import com.badoo.reaktive.disposable.addTo
import com.badoo.reaktive.observable.subscribe
import com.badoo.reaktive.subject.behavior.BehaviorSubject
import de.jensklingenberg.showdown.model.GameState
import de.jensklingenberg.showdown.model.ShowdownError
import showdown.web.Application
import showdown.web.debugLog
import showdown.web.game.GameDataSource

class GameViewmodel(
    private val view: GameContract.View,
    private val gameDataSource: GameDataSource = Application.gameDataSource
) : GameContract.Viewmodel {

    private val compositeDisposable = CompositeDisposable()
    private var playerName: String = ""
    override val starEstimationTimerSubject: BehaviorSubject<Boolean> = BehaviorSubject(false)

    override fun onCreate() {

        observeErrors()
        observeMessage()
        observeGameState()

    }

    private fun observeGameState() {
        gameDataSource.observeGameState().subscribe(onNext = { gameState ->
            when (gameState) {
                GameState.NotStarted, is GameState.PlayerListUpdate -> {
                }
                is GameState.Started -> {
                    starEstimationTimerSubject.onNext(true)
                    view.newState {
                        this.requestRoomPassword = false
                    }

                }

                is GameState.ShowVotes -> {
                    starEstimationTimerSubject.onNext(false)
                }

            }
        }).addTo(compositeDisposable)
    }


    private fun observeMessage() {
        gameDataSource.observeMessage().subscribe(onNext = {
            view.showInfoPopup(it)
        }).addTo(compositeDisposable)
    }

    private fun observeErrors() {
        gameDataSource.observeErrors().subscribe(onNext = { error ->
            when (error) {
                ShowdownError.NotAuthorizedError -> {
                    view.newState {
                        this.requestRoomPassword = true
                    }
                }
                ShowdownError.NoConnectionError -> {
                    debugLog("No Connection")
                    view.newState {
                        this.showEntryPopup = true
                        this.showConnectionError = false
                    }
                }
            }
        }).addTo(compositeDisposable)
    }

    private fun connectToServer(playerName: String, password: String) {
        gameDataSource.connectToServer().subscribe(
            onComplete = {
                debugLog("ConnectToServer onComplete")
                gameDataSource.joinRoom(playerName, password)
                view.newState {
                    this.showEntryPopup = false
                    this.showConnectionError = false
                }

            },
            onError = {
                debugLog("connectToServer: onError" + it.message)
                view.newState {
                    this.showEntryPopup = true
                }
            }
        ).addTo(compositeDisposable)
    }

    override fun onDestroy() {
        compositeDisposable.clear()
    }


    override fun joinGame(playerName: String, password: String) {
        debugLog("JoinGame")
        this.playerName = playerName
        connectToServer(playerName, password)

    }


    override fun changeRoomPassword(password: String) {
        gameDataSource.changeRoomPassword(password)
    }


}