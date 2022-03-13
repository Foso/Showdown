package showdown.web.ui.game

import de.jensklingenberg.showdown.model.api.clientrequest.JoinGame
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import kotlinx.html.js.onKeyDownFunction
import materialui.components.button.button
import materialui.components.button.enums.ButtonColor
import materialui.components.button.enums.ButtonVariant
import materialui.components.checkbox.checkbox
import materialui.components.dialog.DialogElementBuilder
import materialui.components.dialog.dialog
import materialui.components.formcontrol.enums.FormControlVariant
import materialui.components.textfield.textField
import org.w3c.dom.HTMLInputElement
import react.Props
import react.RBuilder
import react.dom.attrs
import react.dom.div
import react.fc
import react.useState
import showdown.web.ui.game.Strings.Companion.JOIN_GAME


external interface PlayerNameDialogComponentProps : Props {
    var onJoinClicked: (JoinGame) -> Unit
}

fun DialogElementBuilder.joinGameButton(onClick: () -> Unit) {
    button {
        attrs {
            text(JOIN_GAME)
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

    val (playerName, setPlayerName) = useState("User" + (0..1000).random().toString())
    val (specState, setSpec) = useState(false)

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
                    onKeyDownFunction = {
                        if (it.type == "keydown" && it.asDynamic()["key"] == "Enter") {
                            props.onJoinClicked(JoinGame(playerName, "", specState))
                        }

                    }
                    onChangeFunction = {
                        val target = it.target as HTMLInputElement

                        setPlayerName(target.value)

                    }
                }

            }
            div {
                checkbox {
                    attrs {
                        checked = specState
                        onClickFunction = {
                            setSpec(!specState)
                        }
                    }
                }
                +"I'm a spectator"
            }
        }

        joinGameButton(onClick = {
            props.onJoinClicked(JoinGame(playerName, "", specState))

        })

    }
}


fun RBuilder.playerNameDialog(onJoinClicked: (JoinGame) -> Unit) {
    child(PlayerNameDialogComponent) {
        attrs {
            this.onJoinClicked = onJoinClicked
        }
    }
}