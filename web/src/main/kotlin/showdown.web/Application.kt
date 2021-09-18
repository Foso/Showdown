package showdown.web

import kotlinext.js.requireAll
import kotlinx.browser.document
import kotlinx.browser.window
import react.Component
import react.RProps
import react.dom.render
import react.router.dom.hashRouter
import react.router.dom.route
import react.router.dom.switch
import showdown.web.game.GameDataSource
import showdown.web.game.GameRepository
import showdown.web.model.Route
import showdown.web.network.GameApiClient
import showdown.web.ui.game.GameView
import showdown.web.ui.onboarding.OnboardingClass
import kotlin.reflect.KClass

class Application {
    companion object {
        private val gameApiHandler = GameApiClient()
        val gameDataSource: GameDataSource = GameRepository(gameApiHandler)

    }

    private val rootElement = "root"

    private val routeList = listOf(
        Route("/", OnboardingClass::class, true),
        Route("/room", GameView::class, false)

    )


    init {
        window.onload = {

            requireAll(kotlinext.js.require.context("kotlin", true, js("/\\.css$/")))
            render(document.getElementById(rootElement)) {

                hashRouter {
                    switch {
                        route(path= arrayOf("/"), component = OnboardingClass::class, exact = true)
                        route(path= arrayOf("/room"), component = GameView::class, exact = false)

                    }
                }
                // game()
            }
        }
    }

}
