package showdown.web.ui.game.toolbar

import kotlinx.html.DIV
import kotlinx.html.js.onClickFunction
import materialui.components.button.button
import materialui.components.button.enums.ButtonColor
import materialui.components.button.enums.ButtonVariant
import react.RClass
import react.dom.RDOMBuilder


fun RDOMBuilder<DIV>.toolbarButton(text: String, icon: RClass<*>, onClick: () -> Unit) {
    button {
        attrs {
            variant = ButtonVariant.contained
            color = ButtonColor.primary
            text(text)
            onClickFunction = {
                onClick()
            }
            startIcon {
                icon {}
            }
        }
    }
}