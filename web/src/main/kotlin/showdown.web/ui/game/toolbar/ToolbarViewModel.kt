package showdown.web.ui.game.toolbar

import com.badoo.reaktive.disposable.CompositeDisposable
import com.badoo.reaktive.disposable.addTo
import com.badoo.reaktive.observable.subscribe
import com.badoo.reaktive.subject.behavior.BehaviorSubject
import de.jensklingenberg.showdown.model.ClientGameConfig
import de.jensklingenberg.showdown.model.api.clientrequest.NewGameConfig
import de.jensklingenberg.showdown.model.fibo
import de.jensklingenberg.showdown.model.modFibo
import de.jensklingenberg.showdown.model.powerOf2
import de.jensklingenberg.showdown.model.tshirtSizesList
import kotlinx.browser.window
import showdown.web.Application
import showdown.web.game.GameDataSource
import kotlin.js.Date

class ToolbarViewModel(private val gameDataSource: GameDataSource = Application.gameDataSource) :
    ToolContract.ViewModel {

    private val compositeDisposable = CompositeDisposable()
    override val roomConfigSubject: BehaviorSubject<ClientGameConfig?> = BehaviorSubject(null)
    override val timerSubject: BehaviorSubject<Double> = BehaviorSubject(0.0)

    private var gameStartTime = Date()

    override fun reset() {
        gameDataSource.requestReset()
    }

    override fun showVotes() {
        gameDataSource.showVotes()
    }

    override fun changeConfig(gameModeId: Int, gameOptions: String) {
        val mode = when (gameModeId) {
            0 -> {
                fibo
            }
            1 -> {
                tshirtSizesList
            }
            2 -> {
                modFibo
            }
            3 -> {
                powerOf2
            }
            4 -> {
                gameOptions.split(";")
            }
            else -> fibo
        }
        val config =
            NewGameConfig(voteOptions = mode)
        gameDataSource.changeConfig(config)
    }

    override fun onDestroy() {
        compositeDisposable.clear()
    }

    override fun onCreate() {
        observeRoomConfig()
        window.setInterval({
            val startDate = gameStartTime
            val endDate = Date()

            val diffSecs = (endDate.getTime() - startDate.getTime()) / 1000
            timerSubject.onNext(diffSecs)
        }, 1000)
    }

    override fun resetTimer() {
        gameStartTime = Date()
    }

    private fun observeRoomConfig() {
        gameDataSource.observeRoomConfig().subscribe(onNext = { conf ->
            conf?.let {
                gameStartTime = Date(conf.createdAt.toDouble())
                roomConfigSubject.onNext(conf)
            }
        }).addTo(compositeDisposable)
    }

}