package de.jensklingenberg.mealapp.mainpage

import androidx.compose.runtime.Composable
import de.jensklingenberg.mealapp.common.JKImage
import de.jensklingenberg.mealapp.network.model.Meal
import dev.petuska.kmdc.card.*
import org.jetbrains.compose.web.attributes.AttrsScope
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.HTMLDivElement

@Composable
fun MealCard(
    meal: Meal,
    onCardClicked: (Meal) -> Unit,
) {
    MDCCard(attrs = {
        style {
            height(50.px)
        }
    }) {
    PrimaryAction {
            Media(type = MDCCardMediaType.Cinema) {
                MediaContent {
                    //justifyContent(JustifyContent.Center)
                    Div(attrs = fun AttrsScope<HTMLDivElement>.() {
                        onClick { onCardClicked(meal) }
                        style {
                            display(DisplayStyle.Flex)
                            alignItems(AlignItems.Center)
                            //justifyContent(JustifyContent.Center)
                        }
                    }) {
                        JKImage(meal.strMealThumb, 50.px)
                        Text(meal.strMeal)
                    }
                }
            }
        }


    }
}