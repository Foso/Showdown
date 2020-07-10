package showdown.web.ui.home

import Application
import com.badoo.reaktive.completable.subscribe
import com.badoo.reaktive.observable.subscribe
import com.soywiz.klock.DateTime
import de.jensklingenberg.showdown.model.ClientGameConfig
import de.jensklingenberg.showdown.model.VoteOptions
import de.jensklingenberg.showdown.model.GameState
import de.jensklingenberg.showdown.model.ShowdownError
import showdown.web.game.GameDataSource

class HomePresenter(private val view: HomeContract.View) : HomeContract.Presenter {

    private val gameDataSource: GameDataSource = Application.gameDataSource

    init {

    }

    override fun onCreate() {
        gameDataSource.connectToServer().subscribe (
            onComplete = {
                view.newState {
                    this.showEntryPopup=true
                }
            },
            onError = {
                view.newState {
                    this.showConnectionError=true
                }
            }
        )

        gameDataSource.observeErrors().subscribe(onNext = {error->
            when(error){
                is ShowdownError.NotAuthorizedError -> {
                    view.newState {
                        this.requestRoomPassword=true
                    }
                }
                null-> {
                    //Do nothing
                }
                is ShowdownError.NoConnectionError -> {
                    view.newState {
                        this.showConnectionError=true
                    }
                }
            }
        })

        gameDataSource.observeGameState().subscribe(onNext = { gameState ->
            when (gameState) {
                GameState.NotStarted -> {
                }
                GameState.Started -> {
                    console.log("GameState.Started")
                    view.newState {
                        this.timerStart = DateTime.now()
                        this.results = emptyList()
                        this.selectedOptionId=-1
                        this.startTimer=true
                        this.requestRoomPassword=false
                    }
                }

                is GameState.MembersUpdate -> {
                    view.newState {
                        this.members = gameState.members
                    }
                }

                is GameState.GameConfigUpdate -> {
                    console.log("GameConfigUpdate")
                    view.newState {
                        this.startTimer=true
                        this.results = emptyList()
                        this.selectedOptionId=-1
                        this.options = gameState.clientGameConfig.voteOptions.options
                        this.timerStart= DateTime.fromString(gameState.clientGameConfig.createdAt).utc
                    }
                }
                is GameState.ShowVotes -> {
                    view.newState {
                        this.results = gameState.results
                    }
                }
                is GameState.NewStarted -> {
                    console.log("GameState.Started")
                    view.newState {
                        this.timerStart = DateTime.now()
                        this.results = emptyList()
                        this.selectedOptionId=-1
                        this.startTimer=true
                        this.requestRoomPassword=false
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

        gameDataSource.joinRoom(playerName,password)
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