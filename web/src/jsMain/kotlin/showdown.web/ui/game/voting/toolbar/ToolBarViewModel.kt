package showdown.web.ui.game.voting.toolbar

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.badoo.reaktive.observable.subscribe
import de.jensklingenberg.showdown.model.GameState
import kotlinx.browser.window
import showdown.web.Application
import showdown.web.game.GameDataSource
import kotlin.js.Date
import kotlin.math.floor

class ToolBarViewModel(private val gameDataSource: GameDataSource = Application.gameDataSource) {
    private var gameStartTime = Date()
    var estimationTimer: MutableState<Int> = mutableStateOf(0)


    fun onCreate() {
        observeGameState()
        window.setInterval({
            val startDate = gameStartTime
            val endDate = Date()

            val diffSecs = (endDate.getTime() - startDate.getTime()) / 1000
            estimationTimer.value = (floor(diffSecs).toInt())
        }, 1000)
    }


    private fun observeGameState() {
        gameDataSource.observeGameState().subscribe(onNext = { newState ->
            if (newState is GameState.Started) {
                val config = newState.clientGameConfig
                gameStartTime = Date(config.createdAt.toDouble())
            }
        })
    }

    fun showVotes() {
        gameDataSource.showVotes()
    }

    fun reset() {
        gameDataSource.requestReset()
    }
}