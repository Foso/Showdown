package showdown.web.ui.game

import androidx.compose.runtime.*
import de.jensklingenberg.mealapp.Page
import de.jensklingenberg.mealapp.common.HeightSpacer
import de.jensklingenberg.mealapp.common.IconButton
import de.jensklingenberg.mealapp.common.JKRaisedButton
import de.jensklingenberg.showdown.model.Member
import de.jensklingenberg.showdown.model.Result
import dev.petuska.kmdc.button.MDCButton
import dev.petuska.kmdc.button.MDCButtonType
import dev.petuska.kmdc.checkbox.MDCCheckbox
import dev.petuska.kmdc.core.KMDCInternalAPI
import dev.petuska.kmdc.list.MDCList
import dev.petuska.kmdc.list.item.ListItem
import dev.petuska.kmdcx.icons.MDCIcon
import kotlinx.browser.document
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import showdown.web.ui.Strings

fun getSpectators(members: List<Member>): List<Member> {
    return members.filter { it.isSpectator }
}

fun getPlayers(members: List<Member>): List<Member> {
    return members.filter { !it.isSpectator }
}

@OptIn(KMDCInternalAPI::class)
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
                backgroundColor(rgb(63,81,181))
            }
        }){
            JKRaisedButton("New Voting", MDCIcon.Add,onClick = {
                gameViewmodel.reset()
            })
            JKRaisedButton("Show Votes", MDCIcon.Visibility, onClick = {
                gameViewmodel.showVotes()
            })
            JKRaisedButton("Settings", MDCIcon.Settings, onClick = {
                openSettings = true
            })
        }

        Br {  }
        Br {  }

        if(!gameViewmodel.isSpectator.value){




            Div {
                if(gameViewmodel.options.value.isNotEmpty()){
                    H2 {
                        IconButton(MDCIcon.TouchApp) {}
                        Text("Select option")
                    }
                }

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

                           Div {
                               Text(option)

                           }
                       }

                    MDCButton(type = MDCButtonType.Text, attrs = {

                    }) {

                        Div {
                            Text("")

                        }
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



