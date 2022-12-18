package showdown.web.ui.game.voting.toolbar

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import de.jensklingenberg.showdown.model.GameState
import kotlinx.browser.window
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import showdown.web.Application
import showdown.web.game.GameDataSource
import kotlin.js.Date
import kotlin.math.floor

class ToolBarViewModel(private val gameDataSource: GameDataSource = Application.gameDataSource) {
    private var gameStartTime = Date()
    var estimationTimer: MutableState<Int> = mutableStateOf(0)
    private val scope = MainScope()


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
        scope.launch {
            gameDataSource.observeGameState().collect {newState ->
                if (newState is GameState.Started) {
                    val config = newState.clientGameConfig
                    gameStartTime = Date(config.createdAt.toDouble())
                }
            }
        }


    }

    fun showVotes() {
        gameDataSource.showVotes()
    }

    fun reset() {
        gameDataSource.requestReset()
    }
}