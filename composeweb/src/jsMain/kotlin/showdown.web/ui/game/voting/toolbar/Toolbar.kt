package showdown.web.ui.game.voting.toolbar

import androidx.compose.runtime.*
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
import showdown.web.ui.game.voting.settings.SettingsDialog
import showdown.web.ui.game.voting.settings.SettingsViewModel


@Composable
fun Toolbar(
    toolBarViewModel: ToolBarViewModel = ToolBarViewModel()
) {

    LaunchedEffect(Unit) {
        toolBarViewModel.onCreate()
    }

    var openSettings by remember { mutableStateOf(false) }


    MDCElevation(8) {
        Div(attrs = {
            this.style {
                backgroundColor(jkBLue)
            }
        }) {
            JKRaisedButton(Strings.NEW_VOTING, MDCIcon.Add, onClick = {
                toolBarViewModel.reset()
            })

            if (openSettings) {
                SettingsDialog(SettingsViewModel()) {
                    openSettings = false
                }
            }

            JKRaisedButton(Strings.SHOW_VOTES, MDCIcon.Visibility, onClick = {
                toolBarViewModel.showVotes()
            })
            JKRaisedButton(Strings.SETTINGS, MDCIcon.Settings, onClick = {
                openSettings = true
            })

            Span(attrs = {
                style {
                    color(Color.white)
                }
            }) {
                Text("Estimation time: ${toolBarViewModel.estimationTimer.value} seconds.")
            }

        }
    }

}