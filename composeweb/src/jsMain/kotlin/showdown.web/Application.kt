package showdown.web


import app.softwork.routingcompose.HashRouter
import kotlinx.browser.document
import kotlinx.browser.window
import org.jetbrains.compose.web.renderComposable
import showdown.web.game.GameDataSource
import showdown.web.game.GameRepository
import showdown.web.network.GameApiClient
import showdown.web.ui.game.onboarding.OnboardingScreen
import showdown.web.ui.game.voting.GameView
import showdown.web.ui.game.voting.GameViewmodel

class Application {

    companion object {

        private val gameApiHandler = GameApiClient()
        val gameDataSource: GameDataSource = GameRepository(gameApiHandler)
        const val PARAM_UNAME = "uname"
        const val DEBUG = true

    }

    private val rootElement = "root"

    init {

        renderComposable(rootElementId = rootElement) {
            HashRouter("") {
                route("/room") {
                    val roomName =
                        window.location.toString().substringAfter("/room/").substringBefore("/").substringBefore("?")

                    document.title = "Showdown - $roomName"
                    GameView(GameViewmodel(gameDataSource))
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