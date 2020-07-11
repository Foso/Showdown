package showdown.web.ui.home


import com.soywiz.klock.DateTime
import kotlinx.css.Color
import kotlinx.css.backgroundColor
import kotlinx.html.DIV
import kotlinx.html.id
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import materialui.components.appbar.appBar
import materialui.components.appbar.enums.AppBarColor
import materialui.components.appbar.enums.AppBarPosition
import materialui.components.button.button
import materialui.components.button.enums.ButtonColor
import materialui.components.button.enums.ButtonVariant
import materialui.components.dialog.dialog
import materialui.components.formcontrol.enums.FormControlVariant
import materialui.components.menu.menu
import materialui.components.menuitem.menuItem
import materialui.components.textfield.textField
import org.w3c.dom.HTMLInputElement
import react.RBuilder
import react.RComponent
import react.RProps
import react.dom.RDOMBuilder
import react.dom.div
import react.dom.label
import react.setState
import showdown.web.wrapper.material.AddCircleIcon
import showdown.web.wrapper.material.SettingsIcon
import showdown.web.wrapper.material.ShareIcon
import showdown.web.wrapper.material.VisibilityIcon
import styled.css
import styled.styledDiv
import kotlin.browser.window
import kotlin.math.floor


class HomeView : RComponent<RProps, HomeContract.HomeViewState>(), HomeContract.View {


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
        showEntryPopup = false

        selectedOptionId = -1
        roomPassword = ""

        showSettings = false
        startTimer = false
        requestRoomPassword = false

        //TOOLBAR
        anchor = null
        openMenu = false
        showShareDialog = false
        timerStart = DateTime.now()
        diffSecs = 0.0

        //MESSAGE
        showConnectionError = false
        showChangePassword = false

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

        connectionErrorSnackbar(this, state.showConnectionError) {
            presenter.onCreate()

            setState {
                this.showConnectionError = false
            }
        }
        setupDialogs()

        toolbar2(onNewGameClicked = {
            presenter.reset()
        }, onShowVotesClicked = {
            presenter.showVotes()
        }, onShareClicked = {
            setState {
                this.showShareDialog = true
            }
        }, state = state)

        optionsList(state.options,state.selectedOptionId,onOptionClicked = { index: Int ->
            setState {
                this.selectedOptionId = index
            }
            presenter.onSelectedVote(index)
        })
        playersList(state.players)
        resultsList(state.results)

        if (state.showSettings) {

            adminMenu(state.gameModeId) { gameModeId, gameOptions ->
                setState {
                    this.gameModeId = gameModeId
                    this.customOptions = gameOptions
                }
                presenter.changeConfig(gameModeId, gameOptions)
            }

        }
    }

    private fun RBuilder.setupDialogs() {
        insertNameDialog()
        insertPasswordDialog()
        setRoomPasswordDialog()
        shareDialog(state.showShareDialog) {
            setState {
                this.showShareDialog = false
            }
        }
    }


    private fun RBuilder.setRoomPasswordDialog() {
        dialog {
            attrs {
                this.open = state.showChangePassword
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
                            this.requestRoomPassword = false
                        }
                        presenter.joinGame()
                    }
                }
            }

        }
    }


    private fun RBuilder.insertNameDialog() {

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





    fun RBuilder.toolbar2(
        onNewGameClicked: () -> Unit,
        onShowVotesClicked: () -> Unit,
        onShareClicked: () -> Unit,
        state: HomeContract.HomeViewState
    ) {

        appBar {
            attrs {
                position = AppBarPosition.static
                color = AppBarColor.primary
            }
            div {

                button {
                    attrs {
                        variant = ButtonVariant.contained
                        color = ButtonColor.primary
                        text("New Game")
                        onClickFunction = {
                            onNewGameClicked()
                        }
                        startIcon {
                            AddCircleIcon {}
                        }
                    }
                }
                button {
                    attrs {
                        variant = ButtonVariant.contained
                        color = ButtonColor.primary
                        text("Show Votes")
                        onClickFunction = {
                            onShowVotesClicked()
                        }
                        startIcon {
                            VisibilityIcon {}
                        }
                    }
                }

                settingsPopupMenu(state, onShareClicked)

                +"Estimation time: ${getTimerText()} seconds.   PlayerName:${state.playerName}"

            }


        }


        styledDiv {
            css {
                backgroundColor = Color.tomato
            }


        }
    }

    private fun RDOMBuilder<DIV>.settingsPopupMenu(
        state: HomeContract.HomeViewState,
        onShareClicked: () -> Unit
    ) {
        button {
            +"Settings"
            attrs {
                variant = ButtonVariant.contained
                color = ButtonColor.primary
                asDynamic()["aria-controls"] = "simple-menu"
                asDynamic()["aria-haspopup"] = true
                startIcon {
                    SettingsIcon {}
                }
                onClickFunction = { event ->
                    val currentTarget = event.currentTarget

                    setState {
                        anchor = currentTarget
                        openMenu = !state.openMenu
                    }

                }
            }
        }

        menu {
            attrs {
                id = "simple-menu"
                open = state.openMenu
                onClose = { event, s ->
                    setState {
                        // anchor = currentTarget
                        openMenu = false

                    }
                }
                anchorEl(state.anchor)
            }
            menuItem {
                attrs {
                    onClickFunction = {
                        setState {
                            this.showSettings = !this.showSettings
                            openMenu = false
                        }
                    }
                }
                label {
                    +" Change GameConfig"
                }
            }

            menuItem {
                attrs {
                    onClickFunction = {
                        setState {
                            showChangePassword = true
                            openMenu = false
                        }
                    }
                }
                label {
                    +"Room password is: ${state.roomPassword}"
                }
            }
            menuItem {
                attrs {
                    onClickFunction = {
                        window.location.href = "https://github.com/Foso/Showdown";
                    }
                }
                label {
                    +"Showdown v1.0 Github"
                }
            }

        }


        button {
            attrs {
                variant = ButtonVariant.contained
                color = ButtonColor.primary
                text("Share")
                onClickFunction = {
                    onShareClicked()
                }
                startIcon {
                    ShareIcon {}
                }
            }
        }
    }

    fun getTimerText(): String {
        return if (state.startTimer) {
            floor(state.diffSecs).toString()
        } else {
            "0"
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




