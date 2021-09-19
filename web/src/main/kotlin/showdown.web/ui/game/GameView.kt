package showdown.web.ui.game


import kotlinx.browser.window
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import materialui.components.checkbox.checkbox
import materialui.components.dialog.dialog
import materialui.components.formcontrol.enums.FormControlVariant
import materialui.components.textfield.textField
import org.w3c.dom.HTMLInputElement
import react.Props
import react.RBuilder
import react.RComponent
import react.RProps
import react.dom.attrs
import react.dom.div
import react.fc
import react.setState
import showdown.web.ui.common.mySnackbar
import showdown.web.ui.game.footer.myfooter
import showdown.web.ui.game.toolbar.myToolbar
import kotlin.js.Date


external interface MyProps : Props

fun routegame() = fc<RProps> { props ->
    GameView()

}

class GameView : RComponent<MyProps, HomeViewState>(), GameContract.View {

    private val presenter: GameContract.Presenter by lazy {
        GamePresenter(this)
    }

    override fun HomeViewState.init() {
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
        isSpectator = false
        anonymResults=false
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

        myToolbar(
            startTimer = state.startEstimationTimer,
            diffSecs = state.diffSecs,
            gameModeId = state.gameModeId,
            shareDialogDataHolder = ShareDialogDataHolder(state.autoReveal,state.anonymResults)
        )

        setupDialogs()
        optionsList(state.options, state.selectedOptionId, onOptionClicked = { index: Int ->
            setState {
                this.selectedOptionId = index
            }
            presenter.onSelectedVote(index)
        })
        div {
            checkbox {
                attrs {
                    checked = state.isSpectator
                    onClickFunction = {
                        presenter.setSpectatorStatus(!state.isSpectator)
                    }
                }
            }
            +"I'm a spectator"
        }
        resultsList(state.results)
        playersList(state.players)
        //myfooter()
        if (state.snackbarMessage.isNotEmpty()) {
            mySnackbar(state.snackbarMessage) {
                setState {
                    this.snackbarMessage = ""
                }
            }

        }

        if (state.showConnectionError) {
            connectionErrorSnackbar(onActionClick = {
                presenter.onCreate()

                setState {
                    this.showConnectionError = false
                }
            })
        }
    }

    private fun RBuilder.setupDialogs() {
        if (state.showEntryPopup) {
            playerNameDialog(onJoinClicked = { playerName ->
                setState {
                    this.playerName = playerName
                    this.showEntryPopup = false
                }
                presenter.joinGame(playerName)
            })
        }

        insertPasswordDialog(state)


    }


    private fun RBuilder.insertPasswordDialog(homeViewState: HomeViewState) {
        dialog {
            attrs {
                this.open = homeViewState.requestRoomPassword
            }

            div {
                textField {
                    attrs {
                        variant = FormControlVariant.filled
                        value(homeViewState.roomPassword)
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

    override fun setSpectatorStatus(it: Boolean) {
        setState {
            this.isSpectator = it
        }
    }


    override fun newState(buildState: HomeViewState.(HomeViewState) -> Unit) {
        setState {
            buildState(this)
        }
    }

    override fun getState(): HomeViewState = state
}


fun RBuilder.home() = child(GameView::class) {
    this.attrs {

    }
}



