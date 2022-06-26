package de.jensklingenberg.mealapp

import de.jensklingenberg.mealapp.network.model.Meal
import de.jensklingenberg.mealdbapi.Category

interface MealDataSource {
    suspend fun getMeals(): List<Meal>
    fun getIngredientImageUrl(name: String): String
    suspend fun getCategories(): List<Category>
    suspend fun getMealsByCategory(categoryName: String): List<Meal>
    suspend fun getMealsByName(categoryName: String): List<Meal>
    suspend fun getMealsById(categoryName: Int): Meal?
}