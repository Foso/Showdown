package showdown.web.ui.game

import androidx.compose.runtime.*
import de.jensklingenberg.mealapp.mainpage.JKTextField
import de.jensklingenberg.showdown.model.api.clientrequest.JoinGame
import dev.petuska.kmdc.button.MDCButton
import dev.petuska.kmdc.button.MDCButtonType
import dev.petuska.kmdc.checkbox.MDCCheckbox
import dev.petuska.kmdc.dialog.Content
import dev.petuska.kmdc.dialog.MDCDialog
import dev.petuska.kmdc.dialog.onClosed
import org.jetbrains.compose.web.dom.Div

@Composable
fun JoinGameDialog(onJoinClicked: (JoinGame) -> Unit) {
    var isSpec by remember { mutableStateOf(false) }
    var playerName by remember { mutableStateOf("User" + (0..1000).random().toString()) }

    MDCDialog(open = true, attrs = {
        this.onClosed {
            onJoinClicked(JoinGame(playerName, "", isSpec))
        }
    }) {
        Div {
            JKTextField(value = playerName, label = "Insert a name", onTextChange = {
                playerName = it
            }, onEnterPressed = {})
        }
        this.Content {

            Div {
                MDCCheckbox(isSpec, label = "I'm a spectator", attrs = {
                    onClick { isSpec = !isSpec }
                })
            }
            Div {
                MDCButton(text = "Join Game", type = MDCButtonType.Raised) {
                    onClick { onJoinClicked(JoinGame(playerName, "", isSpec)) }

                }
            }
        }


    }
}