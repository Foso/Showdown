package showdown.web.ui.game.voting

import androidx.compose.runtime.Composable
import de.jensklingenberg.showdown.model.Member
import dev.petuska.kmdcx.icons.MDCIcon
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H2
import org.jetbrains.compose.web.dom.Text
import showdown.web.common.IconButton

@Composable
fun SpectatorsList(spectators: List<Member>) {
    Div {

        if (spectators.isNotEmpty()) {
            H2 {
                IconButton(MDCIcon.Group) {

                }
                Text("Spectators (${spectators.size})")
            }

            Div(attrs = {
                style {
                    height(10.pc)
                    overflowY("scroll")
                }
            }) {
                spectators.forEach {
                    Div { Text(it.playerName) }
                }
            }

        }


    }
}