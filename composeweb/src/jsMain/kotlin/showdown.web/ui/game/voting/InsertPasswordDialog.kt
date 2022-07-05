package showdown.web.ui.game.voting

import androidx.compose.runtime.*
import de.jensklingenberg.showdown.model.api.clientrequest.JoinGame
import dev.petuska.kmdc.checkbox.MDCCheckbox
import dev.petuska.kmdc.dialog.Content
import dev.petuska.kmdc.dialog.MDCDialog
import dev.petuska.kmdc.dialog.onClosed
import org.jetbrains.compose.web.dom.Div
import showdown.web.common.JKRaisedButton
import showdown.web.common.JKTextField
import showdown.web.ui.Strings
import showdown.web.ui.Strings.Companion.JOIN_GAME

@Composable
fun InsertPasswordDialog(onJoinClicked: (String) -> Unit) {
    var roomPassword by remember { mutableStateOf("") }

    MDCDialog(open = true, attrs = {
        this.onClosed {
            onJoinClicked(roomPassword)
        }
    }) {

        this.Content {

            Div {
                JKTextField(value = roomPassword, label = Strings.PW_REQUIRED, onTextChange = {
                    roomPassword = it
                }, onEnterPressed = {
                    onJoinClicked(roomPassword)
                })
            }
            Div {
                JKRaisedButton(JOIN_GAME) {
                    onJoinClicked(roomPassword)
                }

            }
        }


    }
}