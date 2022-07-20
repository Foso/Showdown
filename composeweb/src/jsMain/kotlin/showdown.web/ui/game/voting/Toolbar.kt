package showdown.web.ui.game.voting

import androidx.compose.runtime.Composable
import dev.petuska.kmdc.elevation.MDCElevation
import dev.petuska.kmdcx.icons.MDCIcon
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.backgroundColor
import org.jetbrains.compose.web.css.color
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text
import showdown.web.common.JKRaisedButton
import showdown.web.common.jkBLue
import showdown.web.ui.Strings


@Composable
fun Toolbar(onNewVotingClicked: () -> Unit, onShowVotesClicked: () -> Unit, onOpenSettings: () -> Unit, seconds: Int) {

    MDCElevation(8) {
        Div(attrs = {
            this.style {
                backgroundColor(jkBLue)
            }
        }) {
            JKRaisedButton(Strings.NEW_VOTING, MDCIcon.Add, onClick = {
                onNewVotingClicked()
            })

            JKRaisedButton(Strings.SHOW_VOTES, MDCIcon.Visibility, onClick = {
                onShowVotesClicked()
            })
            JKRaisedButton(Strings.SETTINGS, MDCIcon.Settings, onClick = {
                onOpenSettings()
            })

            Span(attrs = {
                style {
                    color(Color.white)
                }
            }) {
                Text("Estimation time: $seconds seconds.")
            }

        }
    }

}