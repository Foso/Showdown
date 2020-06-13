package showdown.web.ui.home

import Application
import com.badoo.reaktive.observable.subscribe
import de.jensklingenberg.showdown.model.GameState
import de.jensklingenberg.showdown.model.ClientVote
import showdown.web.game.GameDataSource

class HomePresenter(private val view: HomeContract.View) : HomeContract.Presenter {

    private var playerId = -1
    private val gameDataSource: GameDataSource = Application.gameDataSource
    private var clientVotes : List<ClientVote> = mutableListOf()

    override fun onCreate() {
        gameDataSource.prepareGame()

        gameDataSource.observePlayer().subscribe {
            playerId = it
        }

        gameDataSource.observeGameState().subscribe(onNext = { state ->
            when (state) {

                is GameState.Showdown -> {
                    view.setHidden(false)
                    view.setResults(state.results)
                }
                is GameState.VoteUpdate -> {
                    clientVotes= state.clientVotes
                    view.setPlayerId(state.clientVotes)
                }
                GameState.NotConnected -> {}
                GameState.Lobby -> {}
                GameState.Started -> {}

                is GameState.OptionsUpdate -> {
                    view.setOptions(state.options)
                }

            }
        })

    }

    override fun reset() {
        gameDataSource.requestReset()
    }

    override fun joinGame() {
        gameDataSource.join()
    }



    override fun startGame() {
        gameDataSource.startGame()
    }

    override fun revealCards() {
        gameDataSource.revealCards()
    }

    override fun onSelectedCard(i: Int) {
        gameDataSource.onSelectedCard(i)
    }

}