package showdown.web.ui.game.toolbar

import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import materialui.components.button.button
import materialui.components.button.enums.ButtonColor
import materialui.components.button.enums.ButtonVariant
import materialui.components.formcontrol.enums.FormControlVariant
import materialui.components.menuitem.menuItem
import materialui.components.textfield.textField
import org.w3c.dom.HTMLInputElement
import react.Props
import react.RBuilder
import react.RComponent
import react.State
import react.dom.attrs
import react.dom.br
import react.dom.div
import react.dom.key
import react.dom.p
import react.dom.setProp
import react.setState
import showdown.web.ui.game.Strings.Companion.CHANGE_MODE
import showdown.web.ui.game.Strings.Companion.GAME_MODE


val gameModeOptions: List<Pair<String, Int>>
    get() = listOf(
        "Fibonacci" to 0,
        "T-Shirt" to 1,
        "Modified Fibonacci" to 2,
        "Power of 2" to 3,

        "Custom" to 4
    )

external interface SettingsProps : Props {
    var onSave: (Int, String) -> Unit
    var gameModeId: Int
}

external interface SettingsState : State {
    var onClick: (Int, String) -> Unit
    var gameModeId: Int
    var customOptions: String
}

/**
 * Shows the settings where the player can choose a game mode
 */
class GameSettingsComponent(prps: SettingsProps) : RComponent<SettingsProps, SettingsState>(prps) {

    val CUSTOM_MODE = 4

    override fun SettingsState.init(props: SettingsProps) {
        onClick = props.onSave
        gameModeId = props.gameModeId
        customOptions = ""
    }

    override fun RBuilder.render() {
        br { }
        div {
            textField {
                attrs {
                    select = true
                    label { +GAME_MODE }
                    inputProps {}
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

        if (state.gameModeId == CUSTOM_MODE) {
            div {
                textField {
                    attrs {
                        variant = FormControlVariant.filled
                        fullWidth = true

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
                    text(CHANGE_MODE)
                    onClickFunction = {
                        state.onClick(state.gameModeId, state.customOptions)
                    }
                }
            }
        }


    }
}

fun RBuilder.gameModeSettings(gameModeId: Int, onSave: (Int, String) -> Unit) {
    child(GameSettingsComponent::class) {
        this.attrs.onSave = onSave
        this.attrs.gameModeId = gameModeId

    }
}
