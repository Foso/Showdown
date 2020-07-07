import kotlinext.js.requireAll
import react.Component
import react.RProps
import react.dom.render
import react.router.dom.hashRouter
import react.router.dom.route
import react.router.dom.switch
import showdown.web.network.GameApiHandler
import showdown.web.game.GameDataSource
import showdown.web.game.GameRepository
import showdown.web.model.Route
import showdown.web.ui.home.HomeView
import showdown.web.ui.start.StartPage
import kotlin.browser.document
import kotlin.browser.window
import kotlin.reflect.KClass

//http://localhost:23567/room/hans
class Application {

    private val rootElement= "root"

    private val routeList = listOf(
            Route("/", StartPage::class, true),
            Route("/room", HomeView::class, false)

    )

    companion object{
        private val gameApiHandler = GameApiHandler()
        val gameDataSource: GameDataSource = GameRepository(gameApiHandler)

    }
    init {
        window.onload = {
            requireAll(kotlinext.js.require.context("kotlin", true, js("/\\.css$/")))
            render(document.getElementById(rootElement)) {

                hashRouter {
                    switch {
                        routeList.forEach {
                            route(it.path, it.kClass as KClass<out Component<RProps, *>>, it.exact)
                        }
                    }
                }
               // home()
            }
        }
    }

}
