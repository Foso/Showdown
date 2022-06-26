package de.jensklingenberg.mealapp.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import de.jensklingenberg.mealapp.Page
import de.jensklingenberg.mealapp.common.AppBar
import de.jensklingenberg.mealapp.common.JKImage
import de.jensklingenberg.mealapp.common.JKSnackbar
import de.jensklingenberg.mealdbapi.Ingredient
import dev.petuska.kmdc.chips.grid.InputChip
import dev.petuska.kmdc.chips.grid.MDCChipsGrid
import dev.petuska.kmdc.chips.grid.PrimaryAction
import dev.petuska.kmdc.circular.progress.MDCCircularProgress
import dev.petuska.kmdc.data.table.*
import dev.petuska.kmdc.icon.button.Icon
import dev.petuska.kmdc.icon.button.MDCIconButton
import dev.petuska.kmdc.icon.button.onChange
import dev.petuska.kmdc.top.app.bar.ActionButton
import dev.petuska.kmdc.typography.MDCH1
import dev.petuska.kmdc.typography.MDCH2
import dev.petuska.kmdcx.icons.MDCIcon
import dev.petuska.kmdcx.icons.mdcIcon
import org.jetbrains.compose.web.attributes.AttrsScope
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.HTMLDivElement

@Composable
fun DetailPage(detailViewModel: DetailViewModel, mealId: Int, onChangePage: (Page) -> Unit) {
    LaunchedEffect(Unit) {
        detailViewModel.loadMeal(mealId)
    }

    Div(attrs = {
        style {
            textAlign("center")
            //display(DisplayStyle.Flex)
            alignItems(AlignItems.Center)
            justifyContent(JustifyContent.Center)
        }
    }) {

        when (val uiState = detailViewModel.mealsState.value) {
            DetailUiState.Loading -> {
                Div {
                    MDCCircularProgress()
                }
            }
            is DetailUiState.Success -> {
                val meal = uiState.meal
                if (detailViewModel.showFavAddedSnackbar.value) {
                    JKSnackbar("Recipe added")
                }
                AppBar(
                    navigationIcon = {
                        ActionButton(attrs = {
                            mdcIcon()
                            onClick {
                                onChangePage(Page.Main)
                            }
                        }) { Text(MDCIcon.ArrowBack.type) }
                    },
                    title = meal.strMeal
                )

                Div {
                    MDCH1(meal.strMeal)
                    JKImage(meal.strMealThumb, alt = meal.strMeal, height = 200.px)
                    MDCChipsGrid(attrs = {
                        style {
                            justifyContent(JustifyContent.Center)
                        }
                    }) {
                        listOfNotNull(meal.strCategory, meal.strArea).filter { it.isNotEmpty() }
                            .forEachIndexed { index, category ->

                                InputChip(index.toString()) {
                                    PrimaryAction() {
                                        Text(category)
                                    }
                                }
                            }
                    }
                }


                MDCIconButton(detailViewModel.isFav.value, attrs = {
                    onChange { detailViewModel.addFav(it.detail.isOn) }
                }) {
                    if (detailViewModel.isFav.value) {
                        Icon(on = true, attrs = {

                            mdcIcon()
                        }) {
                            Text(MDCIcon.Favorite.type)
                        }
                    } else {
                        Icon(on = false, attrs = { mdcIcon() }) {
                            Text(MDCIcon.FavoriteBorder.type)
                        }
                    }


                }
                IngredientTable(meal.ingredients)
                MDCH2("Ingredients")


                Div(attrs = fun AttrsScope<HTMLDivElement>.() {
                    style {
                        display(DisplayStyle.Flex)
                        marginLeft(10.percent)
                        marginRight(10.percent)

                    }
                }) {
                    Text(meal.strInstructions)
                }


            }
        }

    }


}

@Composable
fun IngredientTable(it: List<Ingredient>) {
    MDCDataTable {
        Container {
            MDCDataTableHeader {
                Cell("Ingredient")
                Cell("Amount")
            }
            Body {
                it.forEach {
                    Row {
                        Cell(it.name)
                        Cell(it.measure)
                    }
                }

            }
        }
    }
}