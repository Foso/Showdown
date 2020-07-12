package showdown.web.ui.home

import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import materialui.components.button.button
import materialui.components.button.enums.ButtonColor
import materialui.components.button.enums.ButtonVariant
import materialui.components.formcontrol.enums.FormControlVariant
import materialui.components.menuitem.menuItem
import materialui.components.textfield.textField
import org.w3c.dom.HTMLInputElement
import react.*
import react.dom.div
import react.dom.key
import react.dom.p

/*

style = kotlinext.js.js {
                        this.textAlign = "right"
                    }
 */
val gameModeOptions: List<Pair<String, Int>>
    get() = listOf(
        "Fibonacci" to 0,
        "T-Shirt" to 1,
        "Custom" to 2
    )

interface SettingsProps : RProps {
    var startFrom: (Int, String) -> Unit
}

interface TTickerState : RState {
    var onClick: (Int, String) -> Unit
    var gameModeId: Int
    var customOptions: String
}

class SettingsComponent(prps: SettingsProps) : RComponent<SettingsProps, TTickerState>(prps) {

    override fun TTickerState.init(props: SettingsProps) {
        onClick = props.startFrom
        gameModeId = 0
        customOptions = ""
    }


    override fun RBuilder.render() {
        div {
            textField {
                attrs {
                    select = true
                    label { +"GameMode" }
                    // classes("$marginStyle $textFieldStyle")
                    inputProps {
                        attrs {
                            //  startAdornment(startAdornment("Kg"))
                        }
                    }
                    value(state.gameModeId)
                    onChangeFunction = { event ->
                        val value = event.target.asDynamic().value
                        setState {
                            this.gameModeId = value
                        }
                    }
                }
                gameModeOptions.forEach {
                    menuItem {
                        attrs {
                            key = it.first
                            setProp("value", it.second)
                        }
                        +it.first
                    }
                }
            }
        }

        if (state.gameModeId == 2) {
            div {
                textField {
                    attrs {
                        variant = FormControlVariant.filled
                        label {
                            +"Insert Custom options separate with semicolon (;)"
                        }
                        value(state.customOptions)
                        onChangeFunction = {
                            val target = it.target as HTMLInputElement

                            setState {
                                this.customOptions = target.value
                            }
                        }
                    }

                }
            }
        }

        p {
            button {
                attrs {
                    variant = ButtonVariant.contained
                    color = ButtonColor.primary
                    text("Save")
                    onClickFunction = {
                        state.onClick(state.gameModeId, state.customOptions)
                    }
                }
            }
        }


    }
}

fun RBuilder.adminMenu(gameModeId: Int, onSave: (Int, String) -> Unit): ReactElement {
    return child(SettingsComponent::class) {
        attrs.startFrom = onSave

    }
}
