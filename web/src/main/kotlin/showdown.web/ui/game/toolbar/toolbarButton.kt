package showdown.web.ui.game.toolbar

import kotlinx.html.DIV
import kotlinx.html.js.onClickFunction
import materialui.components.button.button
import materialui.components.button.enums.ButtonColor
import materialui.components.button.enums.ButtonVariant
import react.ComponentClass
import react.RClass
import react.dom.RDOMBuilder
import react.dom.attrs


fun RDOMBuilder<DIV>.toolbarButton(text: String, icon: ComponentClass<*>, onClick: () -> Unit) {
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