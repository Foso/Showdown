package showdown.web.ui.new

import challenge.ui.toolbar
import challenge.usecase.MessageUseCase
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import materialui.components.button.button
import materialui.components.button.enums.ButtonColor
import materialui.components.button.enums.ButtonVariant
import materialui.components.formcontrol.enums.FormControlVariant
import materialui.components.menuitem.menuItem
import materialui.components.textfield.textField
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import react.RBuilder
import react.RComponent
import react.RProps
import react.dom.a
import react.dom.div
import react.dom.p
import react.setState

class Navigation {
    companion object {
        fun navigateToGame(roomName: String) = "/#/game?room=${roomName}&pw=Hallo"

    }
}
val gameModeOptions: List<Pair<String, Int>>
    get() = listOf(
            "Fibonacci" to 0,
            "T-Shirt" to 1,
            "Custom" to 2
    )

val gameModes: List<Pair<String, Int>>
    get() = listOf(
        "Fibonacci" to 0,
        "T-Shirt" to 1,
        "Custom" to 2
    )
interface MyProps : RProps

class NewView : RComponent<MyProps, NewContract.HomeViewState>(), NewContract.View {

    private val messageUseCase = MessageUseCase()

    private val presenter: NewContract.Presenter by lazy {
        NewPresenter(this)
    }


    override fun NewContract.HomeViewState.init() {
        showSnackbar = false
        roomName = ""
        weightRange = 0

    }

    override fun componentDidMount() {
        presenter.onCreate()

    }
    private fun handleOnChange(prop: String): (Event) -> Unit = { event ->
        val value = event.target.asDynamic().value
        setState { asDynamic()[prop] = value }
    }

    override fun RBuilder.render() {
        toolbar()
        messageUseCase.showErrorSnackbar(this, state.errorMessage, snackbarVisibility())

        div("imagesGrid") {

            toolbar()




        }

        textField {
            attrs {
                variant = FormControlVariant.filled
                label {
                    +"Insert Roomname"
                }
                onChangeFunction = {
                    val target = it.target as HTMLInputElement
                    console.log(target.value)
                    setState {
                        this.roomName = target.value
                    }
                }
            }
        }

        div {
            textField {
                attrs {
                    variant = FormControlVariant.filled
                    label {
                        +"Insert a Password ☕"
                    }
                    onChangeFunction = {
                        val target = it.target as HTMLInputElement
                        console.log(target.value)
                        setState {
                            this.password = target.value
                        }
                    }
                }

            }
        }
        div {
            textField {
                attrs {
                    select = true
                    label { + "GameMode" }
                    // classes("$marginStyle $textFieldStyle")
                    inputProps {
                        attrs {
                            //  startAdornment(startAdornment("Kg"))
                        }
                    }
                    value(state.weightRange)
                    onChangeFunction = handleOnChange("weightRange")
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

        div {
            textField {
                attrs {
                    select = true
                    label { + "Who can show votes?" }
                    // classes("$marginStyle $textFieldStyle")
                    inputProps {
                        attrs {
                            //  startAdornment(startAdornment("Kg"))
                        }
                    }
                    value(state.weightRange)
                    onChangeFunction = handleOnChange("weightRange")
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


        p {
            button {
                attrs {
                    variant = ButtonVariant.contained
                    color = ButtonColor.primary
                    text("New")
                    onClickFunction = {
                        presenter.createNewRoom(state.roomName,state.weightRange)
                    }
                }
            }
        }

        a {
            +"linkItem.title"
            attrs {
                href = Navigation.navigateToGame(state.roomName)
            }
        }


    }


    private fun snackbarVisibility(): Boolean {
        return state.showSnackbar

    }


    override fun showError(error: String) {
        setState {
            showSnackbar = true
            errorMessage = error
        }
    }


}


fun RBuilder.home() = child(NewView::class) {
    this.attrs {

    }
}




