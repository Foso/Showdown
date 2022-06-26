package de.jensklingenberg.mealapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import de.jensklingenberg.ktorfit.Ktorfit
import de.jensklingenberg.ktorfit.create
import de.jensklingenberg.mealapp.detail.DetailPage
import de.jensklingenberg.mealapp.detail.DetailViewModel
import de.jensklingenberg.mealapp.mainpage.MainPage
import de.jensklingenberg.mealapp.mainpage.MainPageViewModel
import de.jensklingenberg.mealapp.mealdbapi.MealApiService
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.jetbrains.compose.web.renderComposable

class App {

    companion object {

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

            when (selectedPage) {
                is Page.Detail -> {

                    DetailPage(DetailViewModel(mealDataSource), (selectedPage as Page.Detail).mealId) {
                        selectedPage = it
                    }
                }
                is Page.Main -> {
                    MainPage(MainPageViewModel(mealDataSource)) {
                        selectedPage = it
                    }
                }
            }

        }
    }
}