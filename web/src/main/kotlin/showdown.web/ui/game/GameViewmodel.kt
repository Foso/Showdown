package showdown.web.ui.game

import com.badoo.reaktive.completable.subscribe
import com.badoo.reaktive.disposable.CompositeDisposable
import com.badoo.reaktive.disposable.addTo
import com.badoo.reaktive.observable.subscribe
import de.jensklingenberg.showdown.model.GameState
import de.jensklingenberg.showdown.model.ShowdownError
import showdown.web.Application
import showdown.web.game.GameDataSource

class GameViewmodel(
    private val view: GameContract.View,
    private val gameDataSource: GameDataSource = Application.gameDataSource
) : GameContract.Viewmodel {

    private val compositeDisposable = CompositeDisposable()
    private var playerName: String = ""
    private var wasConnected = false
    override fun onCreate() {
        connectToServer()
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
                    view.newState {
                        this.startEstimationTimer = true
                        this.requestRoomPassword = false
                    }

                }

                is GameState.ShowVotes -> {
                    view.newState {
                        this.startEstimationTimer = false
                    }
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
                    connectToServer()
                    view.newState {
                        this.showConnectionError = true
                    }
                }
            }
        }).addTo(compositeDisposable)
    }

    private fun connectToServer() {
        gameDataSource.connectToServer().subscribe(
            onComplete = {
                if (wasConnected) {
                    joinGame(gameDataSource.getPlayerName())
                }
                view.newState {
                    this.showEntryPopup = !wasConnected
                    this.showConnectionError = false
                }
                wasConnected = true
            },
            onError = {

                view.newState {
                    this.showConnectionError = true
                }
            }
        ).addTo(compositeDisposable)
    }

    override fun onDestroy() {
        compositeDisposable.clear()
    }


    override fun joinGame(playerName: String, password: String) {
        this.playerName = playerName
        gameDataSource.joinRoom(playerName, password)
    }


    override fun changeRoomPassword(password: String) {
        gameDataSource.changeRoomPassword(password)
    }


}