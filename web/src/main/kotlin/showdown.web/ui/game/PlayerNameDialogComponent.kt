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
import react.RProps
import react.State
import react.dom.attrs
import react.dom.div
import react.fc
import react.setState
import react.useState

external interface PlayerNameDialogComponentState : State {
    var playerName: String
    var onJoinClicked: (String) -> Unit
}

external interface PlayerNameDialogComponentProps : RProps {
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
val PlayerNameDialogComponent = fc<PlayerNameDialogComponentProps> { props ->

    val (playerName, setplayerName) = useState("User" + (0..1000).random().toString())


    println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")
    dialog {
        attrs {
            this.open = true
        }

        div {
            textField {
                attrs {
                    variant = FormControlVariant.filled
                    label {
                        +"Insert a Name"
                    }
                    value(playerName)
                    onChangeFunction = {
                        val target = it.target as HTMLInputElement

                        setplayerName(target.value)

                    }
                }

            }

        }

        joinGameButton(onClick = {
            props.onJoinClicked(playerName)
        })

    }
}


fun RBuilder.playerNameDialog(onJoinClicked: (String) -> Unit) {
    child(PlayerNameDialogComponent) {
        attrs {
            this.onJoinClicked = onJoinClicked
        }
    }
}