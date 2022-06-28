package showdown.web.ui.game

import androidx.compose.runtime.*
import de.jensklingenberg.mealapp.Page
import de.jensklingenberg.mealapp.common.*
import de.jensklingenberg.mealapp.mainpage.InfoDialog
import de.jensklingenberg.mealapp.mainpage.MainPageViewModel
import de.jensklingenberg.showdown.model.Member
import de.jensklingenberg.showdown.model.Result
import dev.petuska.kmdc.button.MDCButton
import dev.petuska.kmdc.button.MDCButtonType
import dev.petuska.kmdc.checkbox.MDCCheckbox
import dev.petuska.kmdc.chips.grid.InputChip
import dev.petuska.kmdc.chips.grid.MDCChipsGrid
import dev.petuska.kmdc.chips.grid.PrimaryAction
import dev.petuska.kmdc.circular.progress.MDCCircularProgress
import dev.petuska.kmdc.icon.button.Icon
import dev.petuska.kmdc.icon.button.MDCIconButton
import dev.petuska.kmdc.icon.button.onChange
import dev.petuska.kmdc.list.MDCList
import dev.petuska.kmdc.list.item.ListItem
import dev.petuska.kmdc.tooltip.MDCTooltip
import dev.petuska.kmdcx.icons.MDCIcon
import dev.petuska.kmdcx.icons.mdcIcon
import kotlinx.browser.document
import org.jetbrains.compose.web.attributes.AttrsScope
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import org.w3c.dom.HTMLDivElement
import showdown.web.ui.Strings

fun getSpectators(members: List<Member>): List<Member> {
    return members.filter { it.isSpectator }
}

fun getPlayers(members: List<Member>): List<Member> {
    return members.filter { !it.isSpectator }
}

@Composable
fun GameView(gameViewmodel: GameViewmodel, onChangePage: (Page) -> Unit) {

  //  var selectedOption: Int by remember { mutableStateOf(-1) }
    var openSettings by remember { mutableStateOf(false) }

    LaunchedEffect(Unit){
        gameViewmodel.onCreate()
    }
    document.title= "Hallo"

    if(openSettings){
        SettingsDialog {
            openSettings = false
        }
    }

    if(gameViewmodel.showEntryPopup.value){
        JoinGameDialog{
            gameViewmodel.joinGame(it.playerName,it.roomPassword,it.isSpectator)
            gameViewmodel.showEntryPopup.value=false
        }
    }


    Div {

        Div(attrs = {
            this.style {

            }
        }){
            JKRaisedButton("New Voting", onClick = {
                gameViewmodel.reset()
            })
            JKRaisedButton("Show Votes", onClick = {
                gameViewmodel.showVotes()
            })
            JKRaisedButton("Settings", onClick = {
                openSettings = true
            })
        }


        if(true){
            Div {
                MDCCircularProgress(
                    determinate = true,
                    size = 20,
                    label = "5%",
                    progress = 0.5,

                )
                H2 {
                    IconButton(MDCIcon.TouchApp) {}
                    Text("Select option")
                }

            }

            Div {
                gameViewmodel.options.value.forEachIndexed { index, option ->
                    MDCButton(type = MDCButtonType.Outlined, attrs = {
                        style {
                            val clr = if(index == gameViewmodel.selectedOption.value){
                                Color.red
                            }else{
                                Color.blue
                            }
                            this.color(clr)
                        }
                        onClick {
                            gameViewmodel.onSelectedVote(index)

                        }
                    }) {

                        Text(option)
                    }

                }

            }
        }


        HeightSpacer(40.px)
        Div(attrs = {
            onClick {

            }
            style {
                textAlign("center")
              //  display(DisplayStyle.Flex)
                justifyContent(JustifyContent.Center)
                alignItems(AlignItems.Center)
                //property("width", "fit-content")
            }
        }) {
            MDCCheckbox(gameViewmodel.isSpectator.value, attrs = {
                onClick { gameViewmodel.setSpectatorStatus(!gameViewmodel.isSpectator.value) }
            })
            Text(Strings.IMSPECTATOR)
        }

        Div {
            if(gameViewmodel.results.value.isNotEmpty()){
                resultsList(gameViewmodel.results.value)
            }
        }

        Div {
            val players =   getPlayers(gameViewmodel.members.value)
            H2 {
                IconButton(MDCIcon.Group) {}
                Text("Voters (${players.size}) Voted:")
            }

            MDCList() {
                players.forEach {
                    ListItem(disabled = true, selected = false, attrs = {
                        style { textAlign("center")
                            justifyContent(JustifyContent.Center)}
                    }) {
                        Text(("Voter: " + it.playerName + " Voted:"))

                        if(it.voted){
                            IconButton(MDCIcon.CheckCircle, style = {
                                color(Color.green)
                            }){}
                        }else{
                            IconButton(MDCIcon.Cancel, style = {
                                color(Color.red)
                            }){}
                        }
                    }


                }
            }

        }

        Div {
            val spectators = getSpectators(gameViewmodel.members.value)

            if(spectators.isNotEmpty()){
                H2 {
                    IconButton(MDCIcon.Group) {

                    }
                    Text("Spectators (${spectators.size})")
                }
            }

            spectators.forEach {
                Text(it.playerName)
            }
        }

    }
}

@Composable
fun resultsList(results: List<Result>) {

    H2 {
        Text("Result:")
    }

    H2 {
        val groupedResults = results.groupingBy { it.optionName }.eachCount()
        val firsttopCount = groupedResults.entries.maxByOrNull { it.value }?.value
        val tops = groupedResults.entries.filter { it.value == firsttopCount }

        P{
            Text("Top Voted Answer: ${tops.joinToString(separator = " | ") { it.key }}")
        }
    }

    results.groupBy { it.optionName }.forEach {
        val optionName = it.key
        val optionVoters = it.value.joinToString(separator = ", ") { it.voterName }

        H3 {
            Text("$optionName: voted by: $optionVoters")
        }
    }
    Hr { }

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


