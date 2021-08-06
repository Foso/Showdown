package showdown.web.ui.game

import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import materialui.components.button.button
import materialui.components.button.enums.ButtonColor
import materialui.components.button.enums.ButtonVariant
import materialui.components.dialog.DialogElementBuilder
import materialui.components.dialog.dialog
import materialui.components.formcontrol.enums.FormControlVariant
import materialui.components.textfield.textField
import org.w3c.dom.HTMLInputElement
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.ReactElement
import react.dom.attrs
import react.dom.div
import react.setState

interface PlayerNameDialogComponentState : RState {
    var playerName: String
    var onJoinClicked: (String) -> Unit
}

interface PlayerNameDialogComponentProps : RProps {
    var onJoinClicked: (String) -> Unit
}

fun DialogElementBuilder.joinGameButton(onClick: () -> Unit) {
    button {
        attrs {
            text("Join Game")
            variant = ButtonVariant.contained
            color = ButtonColor.primary
            onClickFunction = {
                onClick()
            }
        }
    }
}

/**
 * On this dialog the user has to choose a player name
 */
class PlayerNameDialogComponent(props: PlayerNameDialogComponentProps) :
    RComponent<PlayerNameDialogComponentProps, PlayerNameDialogComponentState>(props) {

    override fun PlayerNameDialogComponentState.init(props: PlayerNameDialogComponentProps) {
        this.playerName = "User" + (0..1000).random().toString()
        this.onJoinClicked = props.onJoinClicked
    }

    override fun RBuilder.render() {
        dialog {
            attrs {
                this.open = true
            }
            playerNameDialogContent()
        }
    }


    private fun DialogElementBuilder.playerNameDialogContent() {
        div {
            textField {
                attrs {
                    variant = FormControlVariant.filled
                    label {
                        +"Insert a Name"
                    }
                    value(state.playerName)
                    onChangeFunction = {
                        val target = it.target as HTMLInputElement

                        setState {
                            this.playerName = target.value
                        }
                    }
                }

            }

        }

        joinGameButton(onClick = {
            state.onJoinClicked(state.playerName)
        })
    }
}

fun RBuilder.playerNameDialog(onJoinClicked: (String) -> Unit) {
    child(PlayerNameDialogComponent::class) {
        attrs {
            this.onJoinClicked = onJoinClicked
        }
    }
}