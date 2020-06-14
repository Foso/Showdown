package showdown.web.ui.new

import challenge.ui.toolbar
import challenge.usecase.MessageUseCase
import com.ccfraser.muirwik.components.MColor
import com.ccfraser.muirwik.components.button.MButtonVariant
import com.ccfraser.muirwik.components.button.mButton
import de.jensklingenberg.showdown.model.ClientVote
import de.jensklingenberg.showdown.model.Option
import de.jensklingenberg.showdown.model.Result

import kotlinx.html.DIV
import kotlinx.html.classes
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import kotlinx.html.onChange
import materialui.components.button.enums.ButtonVariant
import org.w3c.dom.HTMLInputElement
import react.RBuilder
import react.RComponent
import react.RProps
import react.dom.*
import react.setState
import materialui.components.button.button
import materialui.components.button.enums.ButtonColor
import materialui.components.button.enums.ButtonStyle

class Navigation {
    companion object {
        fun navigateToGame(roomName:String) = "/#/game?room=${roomName}&pw=Hallo"

    }
}

interface MyProps : RProps

class NewView : RComponent<MyProps, NewContract.HomeViewState>(), NewContract.View {

    private val messageUseCase = MessageUseCase()

    private val presenter: NewContract.Presenter by lazy {
        NewPresenter(this)
    }



    override fun NewContract.HomeViewState.init() {
        showSnackbar = false
        roomName=""
    }

    override fun componentDidMount() {
        presenter.onCreate()

    }

    override fun RBuilder.render() {
        toolbar()
        messageUseCase.showErrorSnackbar(this, state.errorMessage, snackbarVisibility())

        div("imagesGrid") {

            toolbar()

            button {
                attrs {
                    text("New")
                    onClickFunction = {
                        presenter.createNewRoom(state.roomName)
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

        label { +"ROOMNAME" }
        input {
            attrs {
                this.onChangeFunction={
                    val target = it.target as HTMLInputElement
                    console.log(target.value)
                   setState {
                       this.roomName = target.value
                   }
                }
            }
        }
        button {
            attrs {
                variant = ButtonVariant.contained
               color = ButtonColor.primary
            }

            +"Default"
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




