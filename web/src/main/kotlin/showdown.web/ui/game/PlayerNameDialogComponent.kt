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
import react.*
import react.dom.div

interface PlayerNameDialogComponentState : RState {
    var showEntryPopup: Boolean
    var playerName: String
    var onJoinClicked: (String) -> Unit
}

interface MyProps : RProps {
    var onJoinClicked: (String) -> Unit
    var showEntryPopup: Boolean

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
class PlayerNameDialogComponent(props: MyProps) : RComponent<MyProps, PlayerNameDialogComponentState>(props) {

    override fun PlayerNameDialogComponentState.init(props: MyProps) {
        this.playerName = "Jens"
        this.onJoinClicked = props.onJoinClicked
        this.showEntryPopup = props.showEntryPopup
    }

    override fun componentWillReceiveProps(nextProps: MyProps) {
        setState {
            this.showEntryPopup = nextProps.showEntryPopup
        }
    }


    override fun RBuilder.render() {
        dialog {
            attrs {
                this.open = state.showEntryPopup
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

fun RBuilder.playerNameDialog(showEntryPopup: Boolean, onJoinClicked: (String) -> Unit): ReactElement {
    return child(PlayerNameDialogComponent::class) {
        attrs {
            this.showEntryPopup = showEntryPopup
            this.onJoinClicked = onJoinClicked
        }
    }
}