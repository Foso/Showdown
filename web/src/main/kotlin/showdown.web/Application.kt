import kotlinext.js.requireAll
import kotlinx.browser.document
import kotlinx.browser.window
import react.*
import react.dom.render
import react.router.dom.browserRouter
import react.router.dom.hashRouter
import react.router.dom.route
import react.router.dom.switch
import showdown.web.game.GameDataSource
import showdown.web.game.GameRepository
import showdown.web.model.Route
import showdown.web.network.GameApiClient
import showdown.web.ui.game.GameView
import showdown.web.ui.game.home
import showdown.web.ui.onboarding.OnboardingPage
import kotlin.reflect.KClass

class Application {
    companion object {
        private val gameApiHandler = GameApiClient()
        val gameDataSource: GameDataSource = GameRepository(gameApiHandler)

    }

    private val rootElement = "root"

    private val routeList = listOf(
        Route("/", OnboardingPage::class, true),
        Route("/room", GameView::class, false)

    )
    val Home = fc<RProps> {

    }

    init {
        window.onload = {
            requireAll(kotlinext.js.require.context("kotlin", true, js("/\\.css$/")))
            render(document.getElementById(rootElement)) {

                this.browserRouter {
                    this.switch {

                        route(path= "",Home,true,true)
                        routeList.forEach {

                        }
                           // route(path="it.path", it.kClass as ComponentType<RProps>, exact = it.exact,true)

                    }
                }
                // game()
            }
        }
    }



}
