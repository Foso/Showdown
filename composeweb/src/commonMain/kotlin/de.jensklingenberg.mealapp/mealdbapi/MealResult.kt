package de.jensklingenberg.mealdbapi

import kotlinx.serialization.Serializable

data class Ingredient(val name: String, val measure: String = "")

fun ApiMeal.tags(): List<String> {
    return strTags?.split(",") ?: emptyList()
}

fun ApiMeal.getIngredients(): List<Ingredient> {
    val ingredientsList = mutableListOf<Ingredient>()
    strIngredient1?.let {
        if (it.isNotBlank()) {
            ingredientsList.add(Ingredient(it, strMeasure1 ?: ""))
        }
    }

    strIngredient2?.let {
        if (it.isNotBlank()) {
            ingredientsList.add(Ingredient(it, strMeasure2 ?: ""))

        }
    }
    strIngredient3?.let {
        if (it.isNotBlank()) {
            ingredientsList.add(Ingredient(it, strMeasure3 ?: ""))

        }
    }
    strIngredient4?.let {
        if (it.isNotBlank()) {
            ingredientsList.add(Ingredient(it, strMeasure4 ?: ""))

        }
    }
    strIngredient5?.let {
        if (it.isNotBlank()) {
            ingredientsList.add(Ingredient(it, strMeasure5 ?: ""))

        }
    }
    strIngredient6?.let {
        if (it.isNotBlank()) {
            ingredientsList.add(Ingredient(it, strMeasure6 ?: ""))

        }
    }
    strIngredient7?.let {
        if (it.isNotBlank()) {
            ingredientsList.add(Ingredient(it, strMeasure7 ?: ""))

        }
    }
    strIngredient8?.let {
        if (it.isNotBlank()) {
            ingredientsList.add(Ingredient(it, strMeasure8 ?: ""))

        }
    }
    strIngredient9?.let {
        if (it.isNotBlank()) {
            ingredientsList.add(Ingredient(it, strMeasure9 ?: ""))

        }
    }
    strIngredient10?.let {
        if (it.isNotBlank()) {
            ingredientsList.add(Ingredient(it, strMeasure10 ?: ""))

        }
    }
    strIngredient11?.let {
        if (it.isNotBlank()) {
            ingredientsList.add(Ingredient(it, strMeasure11 ?: ""))

        }
    }
    strIngredient12?.let {
        if (it.isNotBlank()) {
            ingredientsList.add(Ingredient(it, strMeasure12 ?: ""))

        }
    }
    strIngredient13?.let {
        if (it.isNotBlank()) {
            ingredientsList.add(Ingredient(it, strMeasure13 ?: ""))

        }
    }
    strIngredient14?.let {
        if (it.isNotBlank()) {
            ingredientsList.add(Ingredient(it, strMeasure14 ?: ""))

        }
    }
    strIngredient15?.let {
        if (it.isNotBlank()) {
            ingredientsList.add(Ingredient(it, strMeasure15 ?: ""))

        }
    }
    strIngredient16?.let {
        if (it.isNotBlank()) {
            ingredientsList.add(Ingredient(it, strMeasure16 ?: ""))

        }
    }
    strIngredient17?.let {
        if (it.isNotBlank()) {
            ingredientsList.add(Ingredient(it, strMeasure17 ?: ""))

        }
    }
    strIngredient18?.let {
        if (it.isNotBlank()) {
            ingredientsList.add(Ingredient(it, strMeasure18 ?: ""))

        }
    }
    strIngredient19?.let {
        if (it.isNotBlank()) {
            ingredientsList.add(Ingredient(it, strMeasure19 ?: ""))

        }
    }
    strIngredient20?.let {
        if (it.isNotBlank()) {
            ingredientsList.add(Ingredient(it, strMeasure20 ?: ""))

        }
    }
    return ingredientsList
}

/**
 * Dto from meal api
 */
@kotlinx.serialization.Serializable

data class ApiMeal(
    var strMeal: String,
    var idMeal: Int,
    var strMealThumb: String,
    var strIngredient1: String? = "",
    var strIngredient2: String? = "",
    var strIngredient3: String? = "",
    var strIngredient4: String? = "",
    var strIngredient5: String? = "",
    var strIngredient6: String? = "",
    var strIngredient7: String? = "",
    var strIngredient8: String? = "",
    var strIngredient9: String? = "",
    var strIngredient10: String? = "",
    var strIngredient11: String? = "",
    var strIngredient12: String? = "",
    var strIngredient13: String? = "",
    var strIngredient14: String? = "",
    var strIngredient15: String? = "",
    var strIngredient16: String? = "",
    var strIngredient17: String? = "",
    var strIngredient18: String? = "",
    var strIngredient19: String? = "",
    var strIngredient20: String? = "",
    var strMeasure1: String? = "",
    var strMeasure2: String? = "",
    var strMeasure3: String? = "",
    var strMeasure4: String? = "",
    var strMeasure5: String? = "",
    var strMeasure6: String? = "",
    var strMeasure7: String? = "",
    var strMeasure8: String? = "",
    var strMeasure9: String? = "",
    var strMeasure10: String? = "",
    var strMeasure11: String? = "",
    var strMeasure12: String? = "",
    var strMeasure13: String? = "",
    var strArea: String? = "",

    var strMeasure14: String? = "",
    var strMeasure15: String? = "",
    var strMeasure16: String? = "",
    var strMeasure17: String? = "",
    var strMeasure18: String? = "",
    var strMeasure19: String? = "",
    var strMeasure20: String? = "",
    var strTags: String? = "",
    var strCategory: String = "",
    var strInstructions: String = ""
)


@Serializable
data class MealResult(
    val meals: List<ApiMeal> = emptyList(),
)

@Serializable
data class CategoryResult(
    val categories: List<Category>,
)

@Serializable
data class Category(
    var idCategory: String,
    var strCategory: String,
    var strCategoryThumb: String,
    var strCategoryDescription: String,
)