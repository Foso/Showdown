package showdown.web

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import app.softwork.routingcompose.HashRouter
import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.create
import de.jensklingenberg.mealapp.MealDataSource
import de.jensklingenberg.mealapp.MealRepository
import de.jensklingenberg.mealapp.OnboardingScreen
import de.jensklingenberg.mealapp.Page
import showdown.web.ui.game.GameView
import de.jensklingenberg.mealapp.mainpage.MainPageViewModel
import de.jensklingenberg.mealapp.mealdbapi.MealApiService
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.jetbrains.compose.web.renderComposable
import showdown.web.game.GameDataSource
import showdown.web.game.GameRepository
import showdown.web.network.GameApiClient
import showdown.web.ui.game.GameViewmodel

class Application {

    companion object {

            private val gameApiHandler = GameApiClient()
            val gameDataSource: GameDataSource = GameRepository(gameApiHandler)
            val PARAM_UNAME = "uname"
            const val DEBUG = true

        private val client = HttpClient {
            install(ContentNegotiation) {
                json(Json { isLenient = true; ignoreUnknownKeys = true })
            }
        }

        private val api = Ktorfit(MealApiService.baseUrl, client).create<MealApiService>()
        val mealDataSource: MealDataSource = MealRepository(api)

    }

    private val rootElement = "root"
    private var selectedPage: Page by mutableStateOf(Page.Main)



    init {

        renderComposable(rootElementId = rootElement) {
            HashRouter("") {
                route("/room") {
                    GameView( GameViewmodel(gameDataSource)) {
                        selectedPage = it
                    }
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