package showdown.web.ui.game.field

import com.badoo.reaktive.disposable.CompositeDisposable
import com.badoo.reaktive.disposable.addTo
import com.badoo.reaktive.observable.subscribe
import com.badoo.reaktive.subject.behavior.BehaviorSubject
import de.jensklingenberg.showdown.model.GameState
import showdown.web.Application
import showdown.web.game.GameDataSource

class GameFieldViewmodel( private val gameDataSource: GameDataSource = Application.gameDataSource) :
    GameFieldContract.Viewmodel {

    private val compositeDisposable = CompositeDisposable()
    override val gameStateSubject: BehaviorSubject<GameState> = BehaviorSubject(GameState.NotStarted)
    override val spectatorSubject: BehaviorSubject<Boolean> = BehaviorSubject(false)


    override fun onCreate() {
        gameDataSource.observeGameState().subscribe(onNext = {
            gameStateSubject.onNext(it)
        }).addTo(compositeDisposable)
        observeSpectatorStatus()
    }

    private fun observeSpectatorStatus() {
        gameDataSource.observeSpectatorStatus().subscribe(onNext = {
            spectatorSubject.onNext(it)
        }).addTo(compositeDisposable)
    }

    override fun onDestroy() {
        compositeDisposable.clear()
    }



    override fun setSpectatorStatus(b: Boolean) {
        gameDataSource.setSpectatorStatus(b)
    }


    override fun onSelectedVote(voteId: Int) {
        gameDataSource.onSelectedVote(voteId)
    }

}