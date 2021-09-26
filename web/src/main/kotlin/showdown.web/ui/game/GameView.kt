package showdown.web.ui.game


import kotlinx.browser.window
import org.w3c.dom.url.URLSearchParams
import react.Props
import react.RBuilder
import react.RComponent
import react.setState
import showdown.web.Application.Companion.PARAM_UNAME
import showdown.web.ui.common.mySnackbar
import showdown.web.ui.game.field.gameFieldComponent
import showdown.web.ui.game.footer.myfooter
import showdown.web.ui.game.toolbar.myToolbar
import kotlin.js.Date


class GameView : RComponent<Props, GameViewState>(), GameContract.View {

    private val viewmodel: GameContract.Viewmodel by lazy {
        GameViewmodel(this)
    }


    override fun GameViewState.init() {
        showSnackbar = false
        gameModeId = 0
        playerName = ""
        showEntryPopup = false
        roomPassword = ""

        startEstimationTimer = false
        requestRoomPassword = false

        //TOOLBAR
        gameStartTime = Date()

        //MESSAGE
        showConnectionError = false
        autoReveal = false
        snackbarMessage = ""

        anonymResults = false
    }


    override fun componentDidMount() {
        viewmodel.onCreate()
    }

    override fun RBuilder.render() {
        setupDialogs()

        //TOOLBAR
        myToolbar(
            startTimer = state.startEstimationTimer,
            gameModeId = state.gameModeId,
            shareDialogDataHolder = ShareDialogDataHolder(state.autoReveal, state.anonymResults),
            gameStartTime = state.gameStartTime
        )

        gameFieldComponent()


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
                viewmodel.onCreate()

                setState {
                    this.showConnectionError = false
                }
            })
        }

        myfooter()
    }


    private fun RBuilder.setupDialogs() {
        if (state.showEntryPopup) {
            val urlSearchParams = URLSearchParams(window.location.hash.substringAfter("?"))

            if (urlSearchParams.has(PARAM_UNAME)) {
                val uname = urlSearchParams.get(PARAM_UNAME) ?: ""
                viewmodel.joinGame(uname)
                setState {
                    this.playerName = uname
                    this.showEntryPopup = false
                }
            } else {
                playerNameDialog(onJoinClicked = { playerName ->
                    setState {
                        this.playerName = playerName
                        this.showEntryPopup = false
                    }
                    viewmodel.joinGame(playerName)
                })
            }

        }

        if (state.requestRoomPassword) {
            insertPasswordDialog(state.roomPassword, onJoinClicked = {

                setState {
                    this.requestRoomPassword = false
                }
                viewmodel.joinGame(state.playerName,state.roomPassword)
            }, onTextChanged = {
                setState {
                    this.roomPassword = it
                }
            })
        }


    }


    override fun showInfoPopup(it: String) {
        setState {
            this.snackbarMessage = it
        }
    }

    override fun newState(buildState: GameViewState.(GameViewState) -> Unit) {
        setState {
            buildState(this)
        }
    }

}

