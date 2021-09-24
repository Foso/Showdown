package showdown.web.ui.game

import kotlinx.html.js.onChangeFunction
import materialui.components.dialog.dialog
import materialui.components.formcontrol.enums.FormControlVariant
import materialui.components.textfield.textField
import org.w3c.dom.HTMLInputElement
import react.RBuilder
import react.dom.attrs
import react.dom.div

fun RBuilder.insertPasswordDialog(roomPassword: String, onJoinClicked: () -> Unit, onTextChanged: (String) -> Unit) {
    dialog {
        attrs {
            this.open = true
        }

        div {
            textField {
                attrs {
                    variant = FormControlVariant.filled
                    value(roomPassword)
                    label {
                        +"A room password is required"
                    }
                    onChangeFunction = {
                        val target = it.target as HTMLInputElement

                        onTextChanged(target.value)
                    }
                }

            }
        }

        joinGameButton {
            onJoinClicked()
        }

    }
}