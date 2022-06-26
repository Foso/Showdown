package de.jensklingenberg.mealapp.mainpage

import androidx.compose.runtime.*
import de.jensklingenberg.mealapp.Page
import de.jensklingenberg.mealapp.common.*
import dev.petuska.kmdc.chips.grid.InputChip
import dev.petuska.kmdc.chips.grid.MDCChipsGrid
import dev.petuska.kmdc.chips.grid.PrimaryAction
import dev.petuska.kmdc.circular.progress.MDCCircularProgress
import dev.petuska.kmdc.tooltip.MDCTooltip
import org.jetbrains.compose.web.attributes.AttrsScope
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.HTMLDivElement


@Composable
fun MainPage(mainPageViewModel: MainPageViewModel, onChangePage: (Page) -> Unit) {

    var openDialog: Boolean by remember { mutableStateOf(false) }


    Div {
        InfoDialog(openDialog) {
            openDialog = false
        }
        AppBar(onInfoIconClicked = {
            openDialog = !openDialog
        }, hasInfoIcon = true)
        Div(attrs = {
            style {
                textAlign("center")
                display(DisplayStyle.Inline)
                alignItems(AlignItems.Center)
                justifyContent(JustifyContent.Center)
            }
        }) {
            Search(mainPageViewModel)
        }
        Div(attrs = fun AttrsScope<HTMLDivElement>.() {
            style {
                width(100.percent)
                display(DisplayStyle.Flex)
                justifyContent(JustifyContent.Center)
            }
        }) {
            ChipsGrid(mainPageViewModel)
        }
        when (val state = mainPageViewModel.mealsState.value) {
            MainpageUiState.Loading -> {
                Div {
                    MDCCircularProgress()
                }
            }
            is MainpageUiState.Success -> {
                state.meals.forEach { meal ->
                    MealCard(meal) {
                        onChangePage(Page.Detail(meal.idMeal))
                    }
                    HeightSpacer(20.px)
                }
            }
            MainpageUiState.Error -> {
                Text("Nothing found")
            }
        }
    }
}

@Composable
fun ChipsGrid(mainPageViewModel: MainPageViewModel) {
    MDCChipsGrid {
        mainPageViewModel.categories.value.forEachIndexed { index, category ->
            val toolId = "tooltip_$index"
            MDCTooltip(toolId, text = category.strCategoryDescription)
            InputChip(index.toString(), attrs = {
                ariaDescribedBy(toolId)
            }) {
                PrimaryAction(attrs = {
                    onClick { mainPageViewModel.onCategorySelected(category.strCategory) }
                }) {
                    Text(category.strCategory)
                }
            }
        }
    }
}


