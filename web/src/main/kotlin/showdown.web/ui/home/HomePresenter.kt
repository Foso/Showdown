package showdown.web.ui.home

import Application
import com.badoo.reaktive.observable.subscribe
import com.soywiz.klock.DateTime
import de.jensklingenberg.showdown.model.ClientGameConfig
import de.jensklingenberg.showdown.model.VoteOptions
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

        gameDataSource.observeGameState().subscribe(onNext = { gameState ->
            when (gameState) {
                GameState.NotConnected -> {
                }
                GameState.Started -> {
                    view.newState {
                        this.timerStart = DateTime.now()
                        this.results = emptyList()
                        this.selectedOptionId=-1
                    }
                }

                is GameState.VoteUpdate -> {
                    view.newState {
                        this.players = gameState.clientVotes
                    }
                }

                is GameState.GameConfigUpdate -> {
                    view.newState {
                        this.results = emptyList()
                        this.selectedOptionId=-1
                        this.options = gameState.clientGameConfig.voteOptions.options
                        gameState.clientGameConfig.voteOptions.options.forEach {
                            console.log("HIIII "+it.text)
                        }

                        this.timerStart= DateTime.fromString(gameState.clientGameConfig.createdAt).utc
                    }
                }
                is GameState.Showdown -> {
                    view.newState {
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

    override fun joinGame() {
       val playerName=  view.getState().playerName
        val password=  view.getState().roomPassword

        console.log("HALLLLOOOOO")
        console.log("Player+" +password )
        gameDataSource.joinRoom(playerName,password,"jens")
    }



    override fun changeConfig(gameModeId: Int, gameOptions: String) {
        console.log("CHANGE"+gameModeId)
        val mode: VoteOptions = when (gameModeId) {
            0 -> {
                VoteOptions.Fibo()
            }
            1 -> {
                VoteOptions.TShirt()
            }
            2 -> {
                val options = gameOptions.split(";")
                VoteOptions.Custom(options)
            }
            else -> VoteOptions.Fibo()
        }
       val config = ClientGameConfig(voteOptions = mode,createdAt = DateTime.now().utc.toString())
        gameDataSource.changeConfig(config)
    }


    override fun showVotes() {
        gameDataSource.showVotes()
    }

    override fun onSelectedVote(voteId: Int) {
        gameDataSource.onSelectedVote(voteId)
    }

}