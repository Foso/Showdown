package showdown.web.ui.game.voting

import androidx.compose.runtime.Composable
import dev.petuska.kmdcx.icons.MDCIcon
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H2
import org.jetbrains.compose.web.dom.Text
import showdown.web.common.IconButton

@Composable
fun SpectatorsList(gameViewmodel: GameContract.Viewmodel) {
    Div {
        val spectators = getSpectators(gameViewmodel.members.value)

        if (spectators.isNotEmpty()) {
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