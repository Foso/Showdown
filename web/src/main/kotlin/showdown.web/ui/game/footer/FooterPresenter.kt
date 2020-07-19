package showdown.web.ui.game.footer

import Application
import com.badoo.reaktive.disposable.CompositeDisposable
import com.badoo.reaktive.disposable.addTo
import com.badoo.reaktive.observable.subscribe
import de.jensklingenberg.showdown.model.GameState
import showdown.web.game.GameDataSource

class FooterPresenter(private val view: FooterContract.View) :
    FooterContract.Presenter {

    private val gameDataSource: GameDataSource =
        Application.gameDataSource
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate() {
        gameDataSource.observeGameState().subscribe(onNext = { gameState ->
            if (gameState is GameState.Started) {
                view.newState {
                    this.roomHasPassword = gameState.clientGameConfig.roomHasPassword
                }

            }

        }).addTo(compositeDisposable)

        gameDataSource.observeRoomConfig().subscribe(onNext = {conf->
            conf?.let {
                view.newState {
                    this.roomHasPassword = conf.roomHasPassword
                }
            }


        }).addTo(compositeDisposable)
    }

    override fun onDestroy() {
        compositeDisposable.clear()
    }

    override fun onShowSetPasswordDialogClicked() {
        view.showRoomPasswordDialogClicked()
    }

    override fun changeRoomPassword(roomPassword: String) {
        gameDataSource.changeRoomPassword(roomPassword)
    }

}