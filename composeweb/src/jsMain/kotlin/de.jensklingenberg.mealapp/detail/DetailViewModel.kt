package de.jensklingenberg.mealapp.detail

import androidx.compose.runtime.mutableStateOf
import de.jensklingenberg.mealapp.MealDataSource
import de.jensklingenberg.mealapp.network.model.Meal
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

sealed class DetailUiState() {
    object Loading : DetailUiState()
    class Success(val meal: Meal) : DetailUiState()
}

class DetailViewModel(private val mealDataSource: MealDataSource) {

    val mealsState = mutableStateOf<DetailUiState>(DetailUiState.Loading)
    val isFav = mutableStateOf<Boolean>(false)
    val showFavAddedSnackbar = mutableStateOf<Boolean>(false)
    fun loadMeal(mealid: Int) {
        GlobalScope.launch {
            mealDataSource.getMealsById(mealid)?.let {
                mealsState.value = DetailUiState.Success(it)
            }
        }
    }

    fun addFav(on: Boolean) {
        isFav.value = on
        showFavAddedSnackbar.value = on
    }
}