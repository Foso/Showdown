package de.jensklingenberg.mealapp.network.model

import de.jensklingenberg.mealdbapi.ApiMeal
import de.jensklingenberg.mealdbapi.Ingredient
import de.jensklingenberg.mealdbapi.getIngredients
import de.jensklingenberg.mealdbapi.tags

data class Meal(
    var strMeal: String,
    var idMeal: Int,
    var strMealThumb: String,
    val ingredients: List<Ingredient>,
    var strTags: List<String> = emptyList(),
    var strCategory: String = "",
    var strInstructions: String = "",
    var strArea: String? = "",
)

fun ApiMeal.mapToMeal(): Meal {
    val ingredients = this.getIngredients()
    val tags = this.tags()
    return Meal(
        strMeal = strMeal,
        ingredients = ingredients,
        idMeal = idMeal,
        strMealThumb = strMealThumb,
        strTags = tags,
        strInstructions = strInstructions,
        strArea = strArea
    )
}