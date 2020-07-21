package showdown.web.ui.game

import kotlinx.html.js.onClickFunction
import materialui.components.button.button
import materialui.components.button.enums.ButtonColor
import materialui.components.button.enums.ButtonVariant
import materialui.components.dialog.dialog
import materialui.components.textfield.textField
import org.w3c.dom.events.Event
import react.RBuilder
import showdown.web.wrapper.material.QrCode
import kotlin.browser.window

/**
 * Shows a dialog with a QR Code of the link to the room
 */
fun RBuilder.shareDialog(show: Boolean, onAcceptClick: (Event) -> Unit) {

    dialog {
        attrs {
            this.open = show
        }
        QrCode {
            attrs {
                value = window.location.toString()
            }
        }

        textField {
            attrs {
                value = window.location.toString()
            }
        }

        button {
            attrs {
                text("Okay")
                variant = ButtonVariant.contained
                color = ButtonColor.primary
                onClickFunction = onAcceptClick
            }
        }

    }
}