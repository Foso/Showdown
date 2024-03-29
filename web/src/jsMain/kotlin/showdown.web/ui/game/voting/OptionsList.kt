package showdown.web.ui.game.voting

import androidx.compose.runtime.Composable
import dev.petuska.kmdc.button.MDCButton
import dev.petuska.kmdc.button.MDCButtonType
import dev.petuska.kmdc.tooltip.MDCTooltip
import dev.petuska.kmdcx.icons.MDCIcon
import org.jetbrains.compose.web.attributes.AttrsScope
import org.jetbrains.compose.web.attributes.disabled
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.color
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.H2
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.HTMLElement
import showdown.web.common.IconButton
import showdown.web.ui.Strings
import showdown.web.ui.Strings.Companion.RANDOM_BUTTON_TOOLTIP

fun <T : HTMLElement> AttrsScope<T>.ariaDescribedBy(id: String) {
    attr("aria-describedby", id)
}

@Composable
fun OptionsList(options: List<String>, selectedOption: Int, onSelectedVote: (Int) -> Unit) {
    Div {
        if (options.isNotEmpty()) {
            H2 {
                IconButton(MDCIcon.TouchApp) {}
                Text(Strings.SELECT_OPTION)
            }


            options.forEachIndexed { index, option ->

                MDCButton(type = MDCButtonType.Outlined, attrs = {
                    style {
                        val clr = if (index == selectedOption) {
                            Color.red
                        } else {
                            Color.blue
                        }
                        this.color(clr)
                    }
                    onClick {
                        onSelectedVote(index)
                    }
                }) {

                    Div {
                        Text(option)

                    }
                }

                MDCButton(type = MDCButtonType.Text, attrs = {
                    this.disabled()
                }) {

                    Div {
                        Text("")
                    }
                }
            }


            RandomButton(options, selectedOption, onSelectedVote)
        }
    }
}

@Composable fun RandomButton(
    options: List<String>,
    selectedOption: Int,
    onSelectedVote: (Int) -> Unit
) {
    val toolId = "tooltip_random"
    MDCTooltip(toolId, text = RANDOM_BUTTON_TOOLTIP)

    MDCButton(type = MDCButtonType.Outlined, attrs = {
        ariaDescribedBy(toolId)
        style {
            val clr = if ((options.size + 1) == selectedOption) {
                Color.red
            } else {
                Color.blue
            }
            this.color(clr)
        }
        onClick {
            onSelectedVote(options.size + 1)
        }
    }) {

        IconButton(MDCIcon.Casino) {}

    }
}