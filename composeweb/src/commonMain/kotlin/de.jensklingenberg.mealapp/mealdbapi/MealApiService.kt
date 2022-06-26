package de.jensklingenberg.mealapp.mealdbapi

import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Path
import de.jensklingenberg.mealdbapi.CategoryResult
import de.jensklingenberg.mealdbapi.MealResult


interface MealApiService {

    companion object {
        const val baseUrl = "https://www.themealdb.com/api/json/v1/1/"

    }

    @GET("search.php?s=")
    suspend fun getMeals(): MealResult

    @GET("filter.php?c={categoryName}")
    suspend fun getMealsByCategory(@Path("categoryName") categoryName: String): MealResult

    @GET("categories.php")
    suspend fun getCategories(): CategoryResult

    @GET("search.php?s={mealName}")
    suspend fun getMealsByName(@Path("mealName") mealName: String): MealResult?

    @GET("lookup.php?i={mealId}")
    suspend fun getMealsById(@Path("mealId") mealName: Int): MealResult
}
