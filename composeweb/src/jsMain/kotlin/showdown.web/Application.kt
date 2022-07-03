package showdown.web


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import app.softwork.routingcompose.HashRouter
import de.jensklingenberg.mealapp.Page
import org.jetbrains.compose.web.renderComposable
import showdown.web.game.GameDataSource
import showdown.web.game.GameRepository
import showdown.web.network.GameApiClient
import showdown.web.ui.OnboardingScreen
import showdown.web.ui.game.GameView
import showdown.web.ui.game.GameViewmodel

class Application {

    companion object {

            private val gameApiHandler = GameApiClient()
            val gameDataSource: GameDataSource = GameRepository(gameApiHandler)
            val PARAM_UNAME = "uname"
            const val DEBUG = true

    }

    private val rootElement = "root"

    init {

        renderComposable(rootElementId = rootElement) {
            HashRouter("") {
                route("/room") {
                    GameView( GameViewmodel(gameDataSource))
                }
                noMatch {
                    OnboardingScreen()
                }
            }
        }
    }


}


fun debugLog(text: String) {
    if (Application.DEBUG) {
        println("DEBUG: $text")
    }
}