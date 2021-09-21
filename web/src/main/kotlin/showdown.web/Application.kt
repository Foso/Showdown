package showdown.web

import kotlinext.js.requireAll
import kotlinx.browser.document
import kotlinx.browser.window
import react.Component
import react.Props
import react.dom.render
import react.react
import react.router.dom.HashRouter
import react.router.dom.Route
import react.router.dom.Switch
import showdown.web.game.GameDataSource
import showdown.web.game.GameRepository
import showdown.web.network.GameApiClient
import showdown.web.ui.game.GameView
import showdown.web.ui.onboarding.onboardingScreen
import kotlin.reflect.KClass

class Application {
    companion object {
        private val gameApiHandler = GameApiClient()
        val gameDataSource: GameDataSource = GameRepository(gameApiHandler)

    }

    private val rootElement = "root"




    init {
        window.onload = {

            requireAll(kotlinext.js.require.context("kotlin", true, js("/\\.css$/")))
            render(document.getElementById(rootElement)) {
                HashRouter {
                        Switch {

                            Route {
                                attrs {
                                    this.path = arrayOf("/room")
                                    this.strict=false
                                    this.exact = false
                                    this.component = (GameView::class as KClass<out Component<Props, *>>).react
                                }
                            }
                            Route {
                                attrs {
                                    this.path = arrayOf("/")
                                    this.strict=true
                                    this.exact = true
                                    this.component = onboardingScreen()
                                }
                            }
                    }
                }

                // game()
            }
        }
    }

}
