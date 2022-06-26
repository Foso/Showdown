package de.jensklingenberg.mealapp.mainpage

import androidx.compose.runtime.mutableStateOf
import de.jensklingenberg.mealapp.MealDataSource
import de.jensklingenberg.mealapp.network.model.Meal
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

sealed class MainpageUiState {
    class Success(val meals: List<Meal>) : MainpageUiState()
    object Loading : MainpageUiState()
    object Error : MainpageUiState()
}

class MainPageViewModel(private val mealDataSource: MealDataSource) {
    val mealsState = mutableStateOf<MainpageUiState>(MainpageUiState.Loading)
    val categories = mutableStateOf<List<de.jensklingenberg.mealdbapi.Category>>(emptyList())

    private var searchResults = listOf<Meal>()

    private val countries = listOf(
        "American",
        "British",
        "Canadian",
        "Chinese",
        "Croatian",
        "Dutch",
        "Egyptian",
        "French",
        "Greek",
        "Indian",
        "Irish",
        "Italian",
        "Jamaican",
        "Japanese",
        "Kenyan",
        "Malaysian",
        "Mexican",
        "Moroccan",
        "Polish",
        "Portuguese",
        "Russian",
        "Spanish",
        "Thai",
        "Tunisian",
        "Turkish",
        "Unknown",
        "Vietnamese"
    )

    val filters = mutableStateOf<List<Filter>>(countries.map { Filter(it, false) })


    init {
        getMeals()
        loadCategories()
    }

    private fun getMeals() {
        mealsState.value = MainpageUiState.Loading
        GlobalScope.launch {
            val meals = mealDataSource.getMeals()
            console.log("SIZE" + meals.size)
            if (meals.isEmpty()) {
                mealsState.value = MainpageUiState.Error
            } else {
                val results = mealDataSource.getMeals()
                searchResults = results
                mealsState.value = MainpageUiState.Success((results))
            }
        }
    }

    private fun loadCategories() {

        GlobalScope.launch {
            categories.value = mealDataSource.getCategories()
        }
    }

    fun onSearch(name: String) {
        mealsState.value = MainpageUiState.Loading
        GlobalScope.launch {
            val meals = mealDataSource.getMealsByName(name)

            if (meals.isEmpty()) {
                mealsState.value = MainpageUiState.Error
            } else {
                searchResults = meals
                mealsState.value = MainpageUiState.Success((meals))
            }
        }
    }

    fun onCategorySelected(idCategory: String) {
        mealsState.value = MainpageUiState.Loading
        GlobalScope.launch {
            mealsState.value = MainpageUiState.Success((mealDataSource.getMealsByCategory(idCategory)))
        }
    }

    fun onFilterSelected(it: Int) {
        val checked = filters.value[it].checked

        filters.value = filters.value.mapIndexed { index, filter ->
            if (it == index) {
                Filter(filter.name, !checked)
            } else {
                Filter(filter.name, filter.checked)
            }
        }
        updateSearchResults()
    }

    private fun updateSearchResults() {
        mealsState.value = if (filters.value.any { it.checked }) {
            val results =
                searchResults.filter { result -> filters.value.filter { it.checked }.any { it.name == result.strArea } }
            MainpageUiState.Success((results))
        } else {
            MainpageUiState.Success((searchResults))
        }
    }
}