package showdown.web.ui.home

import challenge.usecase.MessageUseCase
import de.jensklingenberg.showdown.model.Option
import kotlinx.css.*
import kotlinx.html.DIV
import kotlinx.html.TD
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import materialui.components.button.button
import materialui.components.button.enums.ButtonColor
import materialui.components.button.enums.ButtonVariant
import materialui.components.formcontrol.enums.FormControlVariant
import materialui.components.menuitem.menuItem
import materialui.components.modal.modal
import materialui.components.paper.paper
import materialui.components.textfield.textField
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import react.RBuilder
import react.RComponent
import react.RProps
import react.dom.*
import react.setState
import showdown.web.ui.new.gameModeOptions
import showdown.web.wrapper.material.QrCode
import styled.css
import styled.styledDiv
import styled.styledP


interface MyProps : RProps

class HomeView : RComponent<MyProps, HomeContract.HomeViewState>(), HomeContract.View {

    private val messageUseCase = MessageUseCase()
    val admin = true
    private val presenter: HomeContract.Presenter by lazy {
        HomePresenter(this)
    }

    override fun HomeContract.HomeViewState.init() {
        showSnackbar = false
        players = emptyList()
        hidden = true
        options = listOf()
        results = emptyList()
        gameModeId = 0
        playerName = "Jens"
        customOptions = ""

    }

    private fun handleOnChange(prop: String): (Event) -> Unit = { event ->
        val value = event.target.asDynamic().value
        setState { asDynamic()[prop] = value }
    }

    override fun componentDidMount() {
        presenter.onCreate()
    }

    override fun RBuilder.render() {

        modal{
            attrs {
                open=true

            }


              styledDiv {
                    css {
                        //backgroundColor= Color.green
                        //height=LinearDimension("100%")
                    }

                      +"Hallo"

              }



        }

        messageUseCase.showErrorSnackbar(this, state.errorMessage, snackbarVisibility())

        div {

            toolbar()

            table("myTable") {
                tbody {

                    tr {
                        td("mytd") {
                            leftSide()
                        }
                        td("mytd") {
                            rightSide()
                        }
                    }
                }
            }
        }

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
        div {
            textField {
                attrs {
                    variant = FormControlVariant.filled
                    label {
                        +"Insert a Password ☕"
                    }
                    onChangeFunction = {
                        val target = it.target as HTMLInputElement

                        setState {

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
                    text("New")
                    onClickFunction = {
                        presenter.createNewRoom("hans", 0)
                    }
                }
            }
        }
        QrCode {
            attrs {
                value = "http://localhost:3001/#/game?room=hans"
            }
        }


        if (admin) {
            adminMenu()
        }
    }

    private fun RDOMBuilder<TD>.rightSide() {
        div {
            state.players.forEach {
                p {
                    attrs {
                        text("Player: " + it.playerName + " Status:" + it.voteValue)
                        onClickFunction = {

                        }
                    }
                }
            }
        }


        table("myTable2") {
            thead {

                +"Result"

            }
            tbody {
                state.results.forEachIndexed { index, result ->
                    tr {
                        td {
                            +"${index + 1} \"${result.name}\""
                        }
                        td {
                            +result.voters
                        }
                    }
                }
            }
        }
    }

    private fun RDOMBuilder<TD>.leftSide() {
        state.options.forEach { option ->
            div {
                button {
                    attrs {
                        variant = ButtonVariant.contained
                        color = ButtonColor.primary
                        text(option.text)
                        onClickFunction = {
                            presenter.onSelectedVote(option.id)
                        }
                    }
                }
            }
        }
    }

    private fun RBuilder.adminMenu() {
        button {
            attrs {
                text("Join Game")
                variant = ButtonVariant.contained
                color = ButtonColor.primary
                onClickFunction = {
                    console.log("HALLLLOOOOO")

                    presenter.joinGame()
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
                        presenter.changeConfig()
                    }
                }
            }
        }

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
                            +"Insert Custom options"
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
    }

    private fun RDOMBuilder<DIV>.toolbar() {


        button {
            attrs {
                variant = ButtonVariant.contained
                color = ButtonColor.primary
                text("Reset")
                onClickFunction = {
                    presenter.reset()
                }
            }
        }
        button {
            attrs {
                variant = ButtonVariant.contained
                color = ButtonColor.primary
                text("Show Votes")
                onClickFunction = {
                    presenter.showVotes()
                }
            }
        }
        ticker(0)
    }


    private fun snackbarVisibility(): Boolean = state.showSnackbar

    override fun newState(buildState: HomeContract.HomeViewState.(HomeContract.HomeViewState) -> Unit) {
        setState {
            buildState(this)
        }
    }

    override fun getState(): HomeContract.HomeViewState {
        return state
    }
}


fun RBuilder.home() = child(HomeView::class) {
    this.attrs {

    }
}




