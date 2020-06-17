package showdown.web.ui.home

import Application
import com.badoo.reaktive.observable.subscribe
import de.jensklingenberg.showdown.model.GameConfig
import de.jensklingenberg.showdown.model.GameMode
import de.jensklingenberg.showdown.model.GameState
import de.jensklingenberg.showdown.model.ShowdownError
import showdown.web.game.GameDataSource

class HomePresenter(private val view: HomeContract.View) : HomeContract.Presenter {

    private val gameDataSource: GameDataSource = Application.gameDataSource

    override fun onCreate() {
        gameDataSource.prepareGame()

        gameDataSource.observeErrors().subscribe(onNext = {error->
            when(error){
                is ShowdownError.NotAuthorizedError -> {

                }
                null-> {
                    //Do nothing
                }
            }
        })

        gameDataSource.observeGameState().subscribe(onNext = { state ->
            when (state) {
                GameState.NotConnected -> {
                }
                GameState.Started -> {
                    view.newState {
                        this.results = emptyList()
                        this.selectedOptionId=-1
                    }
                }

                is GameState.VoteUpdate -> {
                    view.newState {
                        this.players = state.clientVotes
                    }
                }

                is GameState.GameConfigUpdate -> {
                    view.newState {
                        this.results = emptyList()
                        this.selectedOptionId=-1
                        this.options = state.gameConfig.gameMode.options
                    }
                }
                is GameState.Showdown -> {
                    view.newState {
                        this.results = state.results
                    }
                }
            }
        })

    }

    override fun reset() {
        gameDataSource.requestReset()
    }

    override fun joinGame() {
       val playerName=  view.getState().playerName
        val password=  view.getState().roomPassword

        console.log("HALLLLOOOOO")
        console.log("Player+" +password )
        gameDataSource.joinRoom(playerName,password)
    }



    override fun changeConfig() {
        val state = view.getState()
        val mode: GameMode = when (state.gameModeId) {
            0 -> {
                GameMode.Fibo()
            }
            1 -> {
                GameMode.TShirt()
            }
            2 -> {
                val options = state.customOptions.split(";")
                GameMode.Custom(options)
            }
            else -> GameMode.Fibo()
        }
       val config = GameConfig(gameMode = mode)
        gameDataSource.changeConfig(config)
    }


    override fun showVotes() {
        gameDataSource.showVotes()
    }

    override fun onSelectedVote(voteId: Int) {
        gameDataSource.onSelectedVote(voteId)
    }

}