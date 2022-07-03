package showdown.web.ui.game.voting

import androidx.compose.runtime.Composable
import dev.petuska.kmdc.list.MDCList
import dev.petuska.kmdc.list.item.ListItem
import dev.petuska.kmdcx.icons.MDCIcon
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H2
import org.jetbrains.compose.web.dom.Text
import showdown.web.common.IconButton

@Composable
fun VotersList(gameViewmodel: GameContract.Viewmodel) {
    Div {
        val players = getPlayers(gameViewmodel.members.value)
        H2 {
            IconButton(MDCIcon.Group) {}
            Text("Voters (${players.size}) Voted:")
        }

        MDCList() {
            players.forEach {
                ListItem(disabled = true, selected = false, attrs = {
                    style {
                        textAlign("center")
                        justifyContent(JustifyContent.Center)
                    }
                }) {
                    Text(("Voter: " + it.playerName + " Voted:"))

                    if (it.voted) {
                        IconButton(MDCIcon.CheckCircle, style = {
                            color(Color.green)
                        }) {}
                    } else {
                        IconButton(MDCIcon.Cancel, style = {
                            color(Color.red)
                        }) {}
                    }
                }
            }
        }

    }
}