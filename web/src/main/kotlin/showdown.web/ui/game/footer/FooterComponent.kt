package showdown.web.ui.game.footer

import kotlinx.css.*
import kotlinx.html.DIV
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import materialui.components.button.button
import materialui.components.button.enums.ButtonColor
import materialui.components.button.enums.ButtonVariant
import materialui.components.dialog.DialogElementBuilder
import materialui.components.dialog.dialog
import materialui.components.formcontrol.enums.FormControlVariant
import materialui.components.iconbutton.iconButton
import materialui.components.textfield.textField
import materialui.components.tooltip.tooltip
import org.w3c.dom.HTMLInputElement
import react.*
import react.dom.div
import showdown.web.ui.game.StateView
import showdown.web.wrapper.material.icons.LockIcon
import showdown.web.wrapper.material.icons.LockOpenIcon
import styled.StyledDOMBuilder
import styled.css
import styled.styledDiv

/*

style = kotlinext.js.js {
                        this.textAlign = "right"
                    }
 */

interface FooterProps : RProps

interface RoomPasswordDialogState : RState {
    var roomPassword: String
    var roomHasPassword: Boolean
    var showChangePassword: Boolean

}

interface FooterState : RState, RoomPasswordDialogState

interface FooterContract {
    interface View :
        StateView<FooterState> {
        fun showRoomPasswordDialogClicked()
    }

    interface Presenter {
        fun onCreate()
        fun onDestroy()
        fun onShowSetPasswordDialogClicked()
        fun changeRoomPassword(roomPassword: String)
    }
}

class FooterComponent(prps: FooterProps) : RComponent<FooterProps, FooterState>(prps),
    FooterContract.View {

    private val presenter: FooterContract.Presenter by kotlin.lazy {
        FooterPresenter(this)
    }

    override fun componentDidMount() {
        presenter.onCreate()
    }

    override fun componentWillUnmount() {
        presenter.onDestroy()
    }

    override fun FooterState.init(props: FooterProps) {
        this.roomHasPassword = false
    }

    /**
     * On this dialog the user can set/remove the password of the room
     */
    private fun RBuilder.setRoomPasswordDialog(open: Boolean) {

        dialog {
            attrs {
                this.open = open
            }

            div {
                textField {
                    attrs {
                        variant = FormControlVariant.filled
                        value(state.roomPassword)
                        label {
                            +"Set a new room password:"
                        }

                        onChangeFunction = {
                            val target = it.target as HTMLInputElement
                            setState {
                                this.roomPassword = target.value
                            }
                        }
                    }

                }
            }
            removePasswordButton()
            savePasswordButton()
            closeDialogButton()

        }
    }

    private fun DialogElementBuilder.closeDialogButton() {
        button {
            attrs {
                text("Close")
                variant = ButtonVariant.contained
                color = ButtonColor.primary
                onClickFunction = {
                    setState {
                        this.showChangePassword = false
                    }
                }
            }
        }
    }

    private fun DialogElementBuilder.savePasswordButton() {
        button {
            attrs {
                text("Save Password")
                variant = ButtonVariant.contained
                color = ButtonColor.primary
                onClickFunction = {
                    setState {
                        this.showChangePassword = false
                    }

                    presenter.changeRoomPassword(state.roomPassword)
                }
            }
        }
    }

    private fun DialogElementBuilder.removePasswordButton() {
        button {
            attrs {
                text("Remove Password")
                variant = ButtonVariant.contained
                color = ButtonColor.primary
                onClickFunction = {
                    presenter.changeRoomPassword("")
                    setState {
                        this.showChangePassword = false
                    }

                }
            }
        }
    }

    override fun RBuilder.render() {
        setRoomPasswordDialog(state.showChangePassword)
        styledDiv {
            css {
                position = Position.fixed
                bottom = LinearDimension("0")
                width = LinearDimension.fillAvailable
                textAlign = TextAlign.end
            }
            roomPasswordDialogButton()


        }


    }

    private fun StyledDOMBuilder<DIV>.roomPasswordDialogButton() {
        iconButton {
            attrs {
                color = ButtonColor.default
                onClickFunction = {
                    presenter.onShowSetPasswordDialogClicked()
                }
                styledDiv {
                    css {
                        this.color = if (state.roomHasPassword) {
                            Color.green
                        } else {
                            Color.red
                        }
                        this.display = Display.inlineBlock
                    }
                    tooltip {
                        attrs {
                            this.title { +"Room Password" }
                        }
                        if (state.roomHasPassword) {
                            LockIcon {}
                        } else {
                            LockOpenIcon {}
                        }
                    }

                }

            }

        }
    }

    override fun showRoomPasswordDialogClicked() {
        setState {
            this.showChangePassword = true
        }
    }

    override fun newState(buildState: FooterState.(FooterState) -> Unit) {
        setState {
            buildState(this)
        }
    }

    override fun getState(): FooterState {
        return state
    }
}

fun RBuilder.myfooter(): ReactElement {
    return child(FooterComponent::class) {}
}
