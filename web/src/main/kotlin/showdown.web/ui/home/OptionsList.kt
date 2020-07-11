package showdown.web.ui.home

import kotlinx.html.js.onClickFunction
import materialui.components.button.button
import materialui.components.button.enums.ButtonColor
import materialui.components.button.enums.ButtonVariant
import react.RBuilder
import react.dom.h2

fun RBuilder.optionsList(options: List<String>,selectedOptionIndex :Int, onOptionClicked: (Int) -> Unit) {
    h2 {
        +"Select an option:"
    }
    options.forEachIndexed { index, option ->

        button {
            attrs {
                this.color = if (index != selectedOptionIndex) {
                    ButtonColor.primary
                } else {
                    ButtonColor.secondary
                }
                variant = ButtonVariant.outlined
                onClickFunction = {
                    onOptionClicked(index)
                }

            }
            +option

        }

    }
}