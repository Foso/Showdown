package showdown.web.ui.game.voting

import androidx.compose.runtime.*
import de.jensklingenberg.showdown.model.api.clientrequest.JoinGame
import dev.petuska.kmdc.checkbox.MDCCheckbox
import dev.petuska.kmdc.dialog.Content
import dev.petuska.kmdc.dialog.MDCDialog
import dev.petuska.kmdc.dialog.onClosed
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H2
import org.jetbrains.compose.web.dom.Text
import showdown.web.common.JKRaisedButton
import showdown.web.common.JKTextField
import showdown.web.ui.Strings
import showdown.web.ui.Strings.Companion.JOIN_GAME

@Composable
fun JoinGameDialog(onJoinClicked: (JoinGame) -> Unit) {
    var isSpec by remember { mutableStateOf(false) }
    var playerName by remember { mutableStateOf("User" + (0..1000).random().toString()) }

    MDCDialog(open = true, attrs = {
        this.onClosed {
            onJoinClicked(JoinGame(playerName, "", isSpec))
        }
    }) {
        H2 {
            Text(Strings.SHOWDOWN)
        }

        Div {
            JKTextField(value = playerName, label = Strings.INSERT_NAME, onTextChange = {
                playerName = it
            }, onEnterPressed = {
                onJoinClicked(JoinGame(playerName, "", isSpec))
            })
        }
        Content {

            Div {
                MDCCheckbox(isSpec, label = Strings.IMSPECTATOR, attrs = {
                    onClick { isSpec = !isSpec }
                })
            }
            Div {
                JKRaisedButton(JOIN_GAME) {
                    onJoinClicked(JoinGame(playerName, "", isSpec))
                }

            }
        }


    }
}