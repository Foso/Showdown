package showdown.web.ui.home


import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import materialui.components.button.button
import materialui.components.button.enums.ButtonColor
import materialui.components.button.enums.ButtonVariant
import materialui.components.dialog.dialog
import materialui.components.formcontrol.enums.FormControlVariant
import materialui.components.textfield.textField
import org.w3c.dom.HTMLInputElement
import react.RBuilder
import react.RComponent
import react.RProps
import react.dom.div
import react.setState
import kotlin.browser.window
import kotlin.js.Date


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
        startEstimationTimer = false
        requestRoomPassword = false

        //TOOLBAR
        gameStartTime = Date()
        diffSecs = 0.0

        //MESSAGE
        showConnectionError = false
        showChangePassword = false

    }


    override fun componentDidMount() {
        window.setInterval({
            setState {
                val startDate = state.gameStartTime
                val endDate = Date()

                diffSecs = (endDate.getTime() - startDate.getTime()) / 1000
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

        myToolbar(
        startTimer = state.startEstimationTimer,
        onNewGameClicked = {
            presenter.reset()
        },
        onShowVotesClicked = {
            presenter.showVotes()
        },diffSecs = state.diffSecs
    )

        optionsList(state.options, state.selectedOptionId, onOptionClicked = { index: Int ->
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
        playerNameDialog(state.showEntryPopup, onJoinClicked = { playerName ->
            setState {
                console.log("STATE" + playerName)
                this.playerName = playerName
                this.showEntryPopup = false
            }
            presenter.joinGame(playerName)
        })
        insertPasswordDialog(state)
        setRoomPasswordDialog()

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

    private fun RBuilder.insertPasswordDialog(dialogState: HomeContract.HomeViewState) {
        dialog {
            attrs {
                this.open = dialogState.requestRoomPassword
            }

            div {
                textField {
                    attrs {
                        variant = FormControlVariant.filled
                        value(dialogState.roomPassword)
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

            joinGameButton {
                setState {
                    this.requestRoomPassword = false
                }
                presenter.joinGame(state.playerName)
            }

        }
    }




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



