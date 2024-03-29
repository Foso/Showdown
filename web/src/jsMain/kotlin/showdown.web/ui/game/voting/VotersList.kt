package showdown.web.ui.game.voting

import androidx.compose.runtime.Composable
import de.jensklingenberg.showdown.model.Member
import dev.petuska.kmdc.list.Divider
import dev.petuska.kmdc.list.MDCList
import dev.petuska.kmdc.list.MDCListScope
import dev.petuska.kmdc.list.item.ListItem
import dev.petuska.kmdcx.icons.MDCIcon
import org.jetbrains.compose.web.css.JustifyContent
import org.jetbrains.compose.web.css.justifyContent
import org.jetbrains.compose.web.css.textAlign
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H2
import org.jetbrains.compose.web.dom.H3
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.HTMLUListElement
import showdown.web.common.IconButton
import showdown.web.ui.Strings.Companion.CONNECTION_LOST
import showdown.web.ui.Strings.Companion.VOTED
import showdown.web.ui.Strings.Companion.WAITING_FOR

@Composable
fun VotersList(voters: List<Member>) {
    Div {

        H2 {

            IconButton(MDCIcon.Group) {}
            Text("Voters (${voters.size}) ")
        }

        MDCList {
            val votersNotVoted = voters.filter { !it.voted }
            val votersVoted = voters.filter { it.voted }

            if (votersNotVoted.isNotEmpty()) {
                ListItem(disabled = true, selected = false, attrs = {
                    style {
                        textAlign("center")
                        justifyContent(JustifyContent.Center)
                    }
                }) {
                    H3 {
                        Text(WAITING_FOR)
                    }
                }
            }

            votersNotVoted.forEach {
                VotersListItem(it)
            }

            if (votersVoted.isNotEmpty()) {
                ListItem(disabled = true, selected = false, attrs = {
                    style {
                        textAlign("center")
                        justifyContent(JustifyContent.Center)
                    }
                }) {
                    H3 {
                        Text("100%".takeIf { voters.all { it.voted } }+VOTED)
                    }
                }
            }

            votersVoted.forEach {
                VotersListItem(it)
            }
        }
    }
}

@Composable
private fun MDCListScope<HTMLUListElement>.VotersListItem(it: Member) {
    ListItem(disabled = true, selected = false, attrs = {
        style {
            textAlign("center")
            justifyContent(JustifyContent.Center)
        }
    }) {
        Text(it.playerName)

        if (!it.isConnected) {
            Text(" $CONNECTION_LOST")
        }
    }
    Divider { }
}