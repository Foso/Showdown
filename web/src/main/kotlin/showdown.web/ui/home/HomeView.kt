package showdown.web.ui.home


import com.soywiz.klock.DateTime
import kotlinx.css.Color
import kotlinx.css.backgroundColor
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import kotlinx.html.style
import materialui.components.button.button
import materialui.components.button.enums.ButtonColor
import materialui.components.button.enums.ButtonVariant
import materialui.components.dialog.dialog
import materialui.components.formcontrol.enums.FormControlVariant
import materialui.components.list.list
import materialui.components.textfield.textField
import org.w3c.dom.HTMLInputElement
import react.RBuilder
import react.RComponent
import react.RProps
import react.dom.div
import react.dom.h2
import react.dom.h3
import react.setState
import showdown.web.wrapper.material.QrCode
import showdown.web.wrapper.material.SettingsIcon
import showdown.web.wrapper.material.ShareIcon
import styled.css
import styled.styledDiv
import kotlin.browser.window
import kotlin.math.floor


interface MyProps : RProps


class HomeView : RComponent<MyProps, HomeContract.HomeViewState>(), HomeContract.View {

    // private val messageUseCase = MessageUseCase()
    val admin = true
    private val presenter: HomeContract.Presenter by lazy {
        HomePresenter(this)
    }

    override fun HomeContract.HomeViewState.init() {
        showSnackbar = false
        members = emptyList()
        options = listOf()
        results = emptyList()
        gameModeId = 0
        playerName = "Jens"
        customOptions = ""
        showEntryPopup = true
        showShareDialog = false
        selectedOptionId = -1
        roomPassword = ""
        timerStart = DateTime.now()
        diffSecs = 0.0
        showSettings = false
        startTimer=false
        requestRoomPassword=false
    }


    override fun componentDidMount() {
        window.setInterval({
            setState {
                val nowDate = DateTime.now()
                diffSecs = (nowDate - state.timerStart).seconds
            }
        }, 1000)
        presenter.onCreate()
    }

    override fun RBuilder.render() {

        entryDialog()

            insertPasswordDialog()

        shareDialog()
        toolbar()

        optionsList()
        styledDiv {

            list {
                attrs {
                    style = kotlinext.js.js {
                        this.textAlign = "right"
                    }

                }
            }
        }
        members()
        results()




        if (state.showSettings) {

            adminMenu(state.gameModeId) { gameModeId, gameOptions ->
                setState {
                    this.gameModeId = gameModeId
                    this.customOptions = gameOptions
                }
                log("GameMod" + gameModeId + " " + gameOptions)
                presenter.changeConfig(gameModeId, gameOptions)
            }


            //adminMenu()
        }
    }

    private fun RBuilder.insertPasswordDialog() {
        dialog {
            attrs {
                this.open = state.requestRoomPassword
            }

            div {
                textField {
                    attrs {
                        variant = FormControlVariant.filled
                        value(state.roomPassword)
                        label {
                            +"A room password is required"
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

            button {
                attrs {
                    text("Join Game")
                    variant = ButtonVariant.contained
                    color = ButtonColor.primary
                    onClickFunction = {
                        setState {
                            this.requestRoomPassword=false
                        }
                        presenter.joinGame()
                    }
                }
            }

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
                    value = window.location.toString()
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

    private fun RBuilder.members() {
        h2 {
            +"Members (${state.members.size})"
        }

        div {
            if (state.members.isEmpty()) {
                h3 {
                    +"Nobody here, share the link!"
                }
            } else {
                state.members.forEach {
                    h3 {
                        +("Player: " + it.playerName + " Status:" + it.voteStatus + "\n")
                    }
                }
            }

        }
    }

    private fun RBuilder.results() {

        if (state.results.isNotEmpty()) {
            h2 {
                +"Result:"
            }
        }

        state.results.forEachIndexed { index, result ->
            h3 {
                +"${index + 1}) \"${result.name}\" ${result.voters}"
            }

        }

        /**
         *  ApexChart {
        attrs {
        width = "50%"
        type = "bar"
        series = arrayOf(Serie("series-1", arrayOf(3, 4)))
        options = ChartOptions(
        chart = Chart("basic-bar"),
        plotOptions = PlotOptions(Bar(horizontal = true)),
        xaxis = Xaxis(arrayOf("5", "8"))
        )
        }
        }
         */
    }

    private fun RBuilder.optionsList() {
        h2 {
            +"Select an Option:"
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


    private fun RBuilder.toolbar() {


        styledDiv {
            css {
                backgroundColor = Color.tomato
            }

            ShareIcon {
                attrs {
                    onClick = {
                        setState {
                            this.showShareDialog = true
                        }
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

            SettingsIcon {
                attrs {
                    onClick = {
                        setState {
                            this.showSettings = !this.showSettings
                        }
                    }
                }
            }

            +"Estimation time: ${floor(state.diffSecs)} seconds. "

        }
    }


    private fun snackbarVisibility(): Boolean = state.showSnackbar

    override fun newState(buildState: HomeContract.HomeViewState.(HomeContract.HomeViewState) -> Unit) {
        setState {
            buildState(this)
        }
    }

    override fun getState(): HomeContract.HomeViewState = state
}


fun RBuilder.home() = child(HomeView::class) {
    this.attrs {

    }
}




