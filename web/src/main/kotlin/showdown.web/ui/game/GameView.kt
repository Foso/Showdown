package showdown.web.ui.game


import kotlinx.html.js.onChangeFunction
import materialui.components.dialog.dialog
import materialui.components.formcontrol.enums.FormControlVariant
import materialui.components.textfield.textField
import org.w3c.dom.HTMLInputElement
import react.RBuilder
import react.RComponent
import react.RProps
import react.dom.div
import react.setState
import showdown.web.ui.common.mySnackbar
import showdown.web.ui.game.footer.myfooter
import kotlin.browser.window
import kotlin.js.Date


class HomeView : RComponent<RProps, GameContract.HomeViewState>(), GameContract.View {

    private val presenter: GameContract.Presenter by lazy {
        GamePresenter(this)
    }

    override fun GameContract.HomeViewState.init() {
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
        autoReveal = false
        snackbarMessage = ""

    }

    override fun componentWillUnmount() {
        //presenter.onDestroy()
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

        if (state.snackbarMessage.isNotEmpty()) {
            mySnackbar(state.snackbarMessage) {
                setState {
                    this.snackbarMessage = ""
                }
            }

        }
        connectionErrorSnackbar(state.showConnectionError, onActionClick = {
            presenter.onCreate()

            setState {
                this.showConnectionError = false
            }
        })
        setupDialogs()

        myToolbar(
            startTimer = state.startEstimationTimer,
            onNewGameClicked = {
                presenter.reset()
            },
            onShowVotesClicked = {
                presenter.showVotes()
            }, diffSecs = state.diffSecs,
            onGameModeClicked = {
                setState {
                    this.showSettings = !this.showSettings
                }
            },
            gameConfig = state.autoReveal
        )
        if (state.showSettings) {

            gameModeSettings(state.gameModeId) { gameModeId, gameOptions ->
                setState {
                    this.gameModeId = gameModeId
                    this.customOptions = gameOptions
                }
                presenter.changeConfig(gameModeId, gameOptions)
            }

        }
        optionsList(state.options, state.selectedOptionId, onOptionClicked = { index: Int ->
            setState {
                this.selectedOptionId = index
            }
            presenter.onSelectedVote(index)
        })
        resultsList(state.results)

        playersList(state.players)


        myfooter()
    }

    private fun RBuilder.setupDialogs() {
        playerNameDialog(state.showEntryPopup, onJoinClicked = { playerName ->
            setState {
                this.playerName = playerName
                this.showEntryPopup = false
            }
            presenter.joinGame(playerName)
        })
        insertPasswordDialog(state)


    }


    private fun RBuilder.insertPasswordDialog(dialogState: GameContract.HomeViewState) {
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

    override fun showInfoPopup(it: String) {
        setState {
            this.snackbarMessage = it
        }
    }


    override fun newState(buildState: GameContract.HomeViewState.(GameContract.HomeViewState) -> Unit) {
        setState {
            buildState(this)
        }
    }

    override fun getState(): GameContract.HomeViewState = state
}


fun RBuilder.home() = child(HomeView::class) {
    this.attrs {

    }
}



