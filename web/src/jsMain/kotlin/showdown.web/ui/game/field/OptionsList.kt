package showdown.web.ui.game.field

import kotlinx.html.js.onClickFunction
import materialui.components.button.button
import materialui.components.button.enums.ButtonColor
import materialui.components.button.enums.ButtonSize
import materialui.components.button.enums.ButtonVariant
import react.RBuilder
import react.dom.attrs
import react.dom.h2
import showdown.web.wrapper.material.icons.TouchAppIcon

/**
 * Shows the list with options, that the use can vote
 */
fun RBuilder.optionsList(selectedOptionId: Int, options: List<String>, onOptionClicked: (Int) -> Unit) {
    h2 {
        TouchAppIcon {}

        +"Select an option:"
    }
    options.forEachIndexed { index, option ->

        OptionsButton(index, selectedOptionId, onOptionClicked, option)

        button {
            attrs {
                this.size = ButtonSize.small
            }
            +""
        }
    }
}

private fun RBuilder.OptionsButton(
    index: Int,
    selectedOptionId: Int,
    onOptionClicked: (Int) -> Unit,
    optionName: String
) {
    button {
        attrs {
            this.size = ButtonSize.large
            this.color = if (index != selectedOptionId) {
                ButtonColor.primary
            } else {
                ButtonColor.secondary
            }
            variant = ButtonVariant.outlined
            onClickFunction = {
                onOptionClicked(index)
            }

        }
        +optionName

    }
}