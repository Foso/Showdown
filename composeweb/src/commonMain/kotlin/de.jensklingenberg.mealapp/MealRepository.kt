package de.jensklingenberg.mealapp

import de.jensklingenberg.mealapp.mealdbapi.MealApiService
import de.jensklingenberg.mealapp.network.model.Meal
import de.jensklingenberg.mealapp.network.model.mapToMeal
import de.jensklingenberg.mealdbapi.Category


class MealRepository(private val api: MealApiService) : MealDataSource {


    override suspend fun getMeals(): List<Meal> = api.getMeals().meals.map { it.mapToMeal() } ?: emptyList()
    override fun getIngredientImageUrl(name: String): String =
        "https://www.themealdb.com/images/ingredients/$name.png"

    override suspend fun getCategories(): List<Category> = api.getCategories().categories
    override suspend fun getMealsByCategory(categoryName: String): List<Meal> =
        api.getMealsByCategory(categoryName).meals.map { it.mapToMeal() } ?: emptyList()

    override suspend fun getMealsByName(categoryName: String): List<Meal> {
        return try {
            api.getMealsByName(categoryName)?.meals ?: emptyList()
        } catch (exception: Exception) {
            emptyList()
        }.map { it.mapToMeal() }
    }

    override suspend fun getMealsById(categoryName: Int): Meal? {
        return api.getMealsById(categoryName).meals.firstOrNull()
            ?.mapToMeal()
    }

}

