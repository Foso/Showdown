package showdown.web.ui.home

import challenge.usecase.MessageUseCase
import kotlinx.css.Color
import kotlinx.css.backgroundColor
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import materialui.components.button.button
import materialui.components.button.enums.ButtonColor
import materialui.components.button.enums.ButtonVariant
import materialui.components.dialog.dialog
import materialui.components.formcontrol.enums.FormControlVariant
import materialui.components.menuitem.menuItem
import materialui.components.textfield.textField
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import react.RBuilder
import react.RComponent
import react.RProps
import react.dom.div
import react.dom.h2
import react.dom.key
import react.dom.p
import react.setState
import showdown.web.ui.new.gameModeOptions
import showdown.web.wrapper.material.QrCode
import styled.css
import styled.styledDiv


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
        options = listOf()
        results = emptyList()
        gameModeId = 0
        playerName = "Jens"
        customOptions = ""
        showEntryPopup = true
        showShareDialog = false
        selectedOptionId = -1
        timerStart = 0


    }

    private fun handleOnChange(prop: String): (Event) -> Unit = { event ->
        val value = event.target.asDynamic().value
        setState { asDynamic()[prop] = value }
    }

    override fun componentDidMount() {
        presenter.onCreate()
    }

    override fun RBuilder.render() {

        entryDialog()
        shareDialog()

        messageUseCase.showErrorSnackbar(this, state.errorMessage, snackbarVisibility())

        toolbar()

        optionsList()
        participants()
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


        if (admin) {
            adminMenu()
        }
    }

    private fun RBuilder.entryDialog() {
        dialog {
            attrs {
                this.open = state.showEntryPopup
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
            button {
                attrs {
                    text("Join Game")
                    variant = ButtonVariant.contained
                    color = ButtonColor.primary
                    onClickFunction = {
                        console.log("HALLLLOOOOO")
                        setState {
                            this.showEntryPopup = false
                        }
                        presenter.joinGame()
                    }
                }
            }

        }
    }

    private fun RBuilder.shareDialog() {
        dialog {
            attrs {
                this.open = state.showShareDialog
            }
            QrCode {
                attrs {
                    value = "http://localhost:3001/#/game?room=hans"
                }
            }

            button {
                attrs {
                    text("Okay")
                    variant = ButtonVariant.contained
                    color = ButtonColor.primary
                    onClickFunction = {
                        setState {
                            this.showShareDialog = false
                        }

                    }
                }
            }

        }
    }

    private fun RBuilder.participants() {
        h2 {
            +"Members"
        }
        div {
            state.players.forEach {

                p {
                    attrs {
                        text("Player: " + it.playerName + " Status:" + it.voteValue + "\n")
                        onClickFunction = {

                        }

                    }
                }

            }

            h2 {
                +"Result:"
            }

            state.results.forEachIndexed { index, result ->
                p {
                    +"${index + 1}) \"${result.name}\" ${result.voters}"
                }

            }

        }


    }

    private fun RBuilder.optionsList() {
        h2 {
            +"Options"
        }
        state.options.forEachIndexed { index, option ->

            button {
                attrs {
                    this.color = if (index != state.selectedOptionId) {
                        ButtonColor.primary
                    } else {
                        ButtonColor.secondary
                    }
                    variant = ButtonVariant.outlined
                    onClickFunction = {
                        setState {
                            this.selectedOptionId = index
                        }
                        presenter.onSelectedVote(option.id)
                    }

                }
                +option.text

            }

        }
    }

    private fun RBuilder.adminMenu() {


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
    }

    private fun RBuilder.toolbar() {


        styledDiv {
            css {
                backgroundColor = Color.tomato
            }


            button {
                attrs {
                    variant = ButtonVariant.contained
                    color = ButtonColor.primary
                    text("Share")
                    onClickFunction = {
                        setState {
                            this.showShareDialog = true
                        }
                        //presenter.createNewRoom("hans", 0)
                    }
                }
            }

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




