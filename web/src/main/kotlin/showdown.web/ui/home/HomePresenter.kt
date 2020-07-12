package showdown.web.ui.home

import Application
import com.badoo.reaktive.completable.subscribe
import com.badoo.reaktive.observable.subscribe
import de.jensklingenberg.showdown.model.*
import showdown.web.game.GameDataSource
import kotlin.js.Date

class HomePresenter(private val view: HomeContract.View) : HomeContract.Presenter {

    private val gameDataSource: GameDataSource = Application.gameDataSource

    init {

    }

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
        )

        gameDataSource.observeErrors().subscribe(onNext = { error ->
            when (error) {
                is ShowdownError.NotAuthorizedError -> {
                    view.newState {
                        this.requestRoomPassword = true
                    }
                }
                null -> {
                    //Do nothing
                }
                is ShowdownError.NoConnectionError -> {
                    view.newState {
                        this.showConnectionError = true
                    }
                }
            }
        })

        gameDataSource.observeGameState().subscribe(onNext = { gameState ->
            when (gameState) {
                GameState.NotStarted -> {
                }
                is GameState.Started -> {
                    console.log("GameState.Started")
                    view.newState {
                        console.log("STARTED" + gameState.clientGameConfig.createdAt)
                        this.gameStartTime = Date(gameState.clientGameConfig.createdAt.toDouble())
                        this.results = emptyList()
                        this.selectedOptionId = -1
                        this.startEstimationTimer = true
                        this.requestRoomPassword = false
                        this.options = gameState.clientGameConfig.voteOptions

                    }

                }
                is GameState.MembersUpdate -> {
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
        })

    }

    override fun reset() {
        gameDataSource.requestReset()
        console.log("RESET")

    }

    override fun joinGame(playerName: String) {
        val password = view.getState().roomPassword
        console.log("Player " + playerName)
        gameDataSource.joinRoom(playerName, password)
    }


    override fun changeConfig(gameModeId: Int, gameOptions: String) {
        console.log("CHANGE" + gameModeId)
        val mode = when (gameModeId) {
            0 -> {
                fibo
            }
            1 -> {
                tshirtList
            }
            2->{
                modFibo
            }
            3->{
                powerOf2
            }
            4 -> {
                gameOptions.split(";")
            }
            else -> fibo
        }
        val config = NewGameConfig(voteOptions = mode)
        gameDataSource.changeConfig(config)
    }

    override fun changeRoomPassword(password: String) {
        gameDataSource.changeRoomPassword(password)
    }


    override fun showVotes() {
        gameDataSource.showVotes()
    }

    override fun onSelectedVote(voteId: Int) {
        gameDataSource.onSelectedVote(voteId)
    }

}