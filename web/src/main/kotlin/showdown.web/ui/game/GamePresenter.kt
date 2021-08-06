package showdown.web.ui.game

import showdown.web.Application
import com.badoo.reaktive.completable.subscribe
import com.badoo.reaktive.disposable.CompositeDisposable
import com.badoo.reaktive.disposable.addTo
import com.badoo.reaktive.observable.subscribe
import de.jensklingenberg.showdown.model.GameState
import de.jensklingenberg.showdown.model.ShowdownError
import showdown.web.game.GameDataSource
import kotlin.js.Date

class GamePresenter(private val view: GameContract.View) : GameContract.Presenter {

    private val gameDataSource: GameDataSource = Application.gameDataSource
    private val compositeDisposable = CompositeDisposable()
    override fun onCreate() {
        gameDataSource.connectToServer().subscribe(
            onComplete = {
                view.newState {
                    this.showEntryPopup = true
                }
            },
            onError = {
                view.newState {
                    this.showConnectionError = true
                }
            }
        ).addTo(compositeDisposable)

        gameDataSource.observeErrors().subscribe(onNext = { error ->
            if (error is ShowdownError.NotAuthorizedError) {
                view.newState {
                    this.requestRoomPassword = true
                }
            } else if (error is ShowdownError.NoConnectionError) {
                view.newState {
                    this.showConnectionError = true
                }
            }
        }).addTo(compositeDisposable)

        gameDataSource.observeMessage().subscribe(onNext = {
            view.showInfoPopup(it)
        }).addTo(compositeDisposable)

        gameDataSource.observeSpectatorStatus().subscribe(onNext = {
            view.setSpectatorStatus(it)
        }).addTo(compositeDisposable)

        gameDataSource.observeRoomConfig().subscribe(onNext = { conf ->
            console.log("ROOM: "+conf?.anonymResults)
            conf?.let {
                view.newState {
                    this.anonymResults=conf.anonymResults
                    this.autoReveal = conf.autoReveal
                }
            }


        }).addTo(compositeDisposable)

        gameDataSource.observeGameState().subscribe(onNext = { gameState ->
            when (gameState) {
                GameState.NotStarted -> {
                }
                is GameState.Started -> {
                    view.newState {
                        val conf = gameState.clientGameConfig

                        this.gameStartTime = Date(conf.createdAt.toDouble())
                        this.results = emptyList()
                        this.selectedOptionId = -1
                        this.startEstimationTimer = true
                        this.requestRoomPassword = false
                        this.options = conf.voteOptions
                        this.autoReveal = conf.autoReveal
                        this.anonymResults = conf.anonymResults

                    }

                }
                is GameState.PlayerListUpdate -> {
                    view.newState {
                        this.players = gameState.members
                    }
                }

                is GameState.ShowVotes -> {
                    view.newState {
                        this.startEstimationTimer = false
                        this.results = gameState.results
                    }
                }

            }
        }).addTo(compositeDisposable)

    }

    override fun onDestroy() {
        compositeDisposable.clear()
    }


    override fun joinGame(playerName: String) {
        val password = view.getState().roomPassword
        gameDataSource.joinRoom(playerName, password)
    }


    override fun changeRoomPassword(password: String) {
        gameDataSource.changeRoomPassword(password)
    }

    override fun setSpectatorStatus(b: Boolean) {

        gameDataSource.setSpectatorStatus(b)

    }


    override fun onSelectedVote(voteId: Int) {
        gameDataSource.onSelectedVote(voteId)
    }

}