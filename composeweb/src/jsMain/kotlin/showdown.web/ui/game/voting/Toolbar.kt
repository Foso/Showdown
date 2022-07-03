package showdown.web.ui.game.voting

import androidx.compose.runtime.Composable
import dev.petuska.kmdc.elevation.MDCElevation
import dev.petuska.kmdcx.icons.MDCIcon
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text
import showdown.web.common.JKRaisedButton
import showdown.web.ui.Strings


val jkBLue = rgb(63, 81, 181)

@Composable
fun Toolbar(onNewVotingClicked: () -> Unit, onShowVotesClicked: () -> Unit, onOpenSettings: () -> Unit, seconds:Int) {

    MDCElevation(8){
        Div(attrs = {
            this.style {
                backgroundColor(jkBLue)
            }
        }) {
            JKRaisedButton(Strings.NEW_VOTING, MDCIcon.Add, onClick = {
                onNewVotingClicked()
            })

            JKRaisedButton("Show Votes", MDCIcon.Visibility, onClick = {
                onShowVotesClicked()
            })
            JKRaisedButton("Settings", MDCIcon.Settings, onClick = {
                onOpenSettings()
            })

            Span(attrs = {
                style {
                    color(Color.white)
                }
            }) {
                Text("Estimation time: ${seconds} seconds.")
            }

        }
    }

}