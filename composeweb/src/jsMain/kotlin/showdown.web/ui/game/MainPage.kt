package showdown.web.ui.game

import androidx.compose.runtime.*
import de.jensklingenberg.mealapp.Page
import de.jensklingenberg.mealapp.common.*
import de.jensklingenberg.mealapp.mainpage.InfoDialog
import de.jensklingenberg.mealapp.mainpage.MainPageViewModel
import de.jensklingenberg.mealapp.mainpage.MainpageUiState
import dev.petuska.kmdc.button.MDCButton
import dev.petuska.kmdc.button.MDCButtonType
import dev.petuska.kmdc.checkbox.MDCCheckbox
import dev.petuska.kmdc.chips.grid.InputChip
import dev.petuska.kmdc.chips.grid.MDCChipsGrid
import dev.petuska.kmdc.chips.grid.PrimaryAction
import dev.petuska.kmdc.circular.progress.MDCCircularProgress
import dev.petuska.kmdc.tooltip.MDCTooltip
import dev.petuska.kmdcx.icons.MDCIcon
import org.jetbrains.compose.web.attributes.AttrsScope
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H2
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.HTMLDivElement


@Composable
fun GameView(gameViewmodel: GameViewmodel, onChangePage: (Page) -> Unit) {

    var openDialog: Boolean by remember { mutableStateOf(false) }
    var selectedOption: Int by remember { mutableStateOf(-1) }

    LaunchedEffect(Unit){
        gameViewmodel.onCreate()
    }

    if(gameViewmodel.showEntryPopup.value){
        JoinGameDialog{
            gameViewmodel.joinGame(it.playerName,it.roomPassword,it.isSpectator)
            gameViewmodel.showEntryPopup.value=false
        }
    }


    Div {
        InfoDialog(openDialog) {
            openDialog = false
        }
        Div(attrs = {
            this.style {

            }
        }){
            JKRaisedButton("New Voting", onClick = {

            })
            JKRaisedButton("Show Votes", onClick = {

            })
            JKRaisedButton("Settings", onClick = {

            })
        }


        Div {
            H2 {
                IconButton(MDCIcon.TouchApp) {

                }
                Text("Select option")
            }

        }

        Div {
            (0..5).forEachIndexed { index, i ->
                MDCButton(type = MDCButtonType.Outlined, attrs = {
                        style {
                            val clr = if(index == selectedOption){
                                Color.red
                            }else{
                                Color.blue
                            }
                            this.color(clr)
                        }
                        onClick {
                            gameViewmodel.onSelectedVote(index)
                            selectedOption= index
                        }
                }) {

                    Text(index.toString())
                }

            }

        }

        Div(attrs = fun AttrsScope<HTMLDivElement>.() {
            onClick {

            }
            style {
                textAlign("center")
              //  display(DisplayStyle.Flex)
                justifyContent(JustifyContent.Center)
                alignItems(AlignItems.Center)
                property("width", "fit-content")
            }
        }) {
            MDCCheckbox(false)
            Text("I'm a spectator")
        }

        Div {
            H2 {
                IconButton(MDCIcon.Group) {

                }
                Text("Voters voted")
            }

        }

        gameViewmodel.players.value.forEach {
            Text(it.playerName)
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


