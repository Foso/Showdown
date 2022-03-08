package showdown.web.ui.game


import com.badoo.reaktive.observable.subscribe
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


class GameView : RComponent<Props, GameViewState>(), GameContract.View {

    private val viewmodel: GameContract.Viewmodel by lazy {
        GameViewmodel(this)
    }


    override fun GameViewState.init() {
        showSnackbar = false

        playerName = ""
        showEntryPopup = true
        roomPassword = ""

        startEstimationTimer = false
        requestRoomPassword = false

        //MESSAGE
        showConnectionError = false
        snackbarMessage = ""
    }


    override fun componentDidMount() {
        viewmodel.onCreate()
        viewmodel.starEstimationTimerSubject.subscribe {
            setState {
                this.startEstimationTimer = it
            }
        }
    }

    override fun RBuilder.render() {
        setupDialogs()

        //TOOLBAR
        myToolbar(
            startTimer = state.startEstimationTimer,
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
                playerNameDialog(onJoinClicked = { joinGame ->
                    setState {
                        this.playerName = joinGame.playerName
                        this.showEntryPopup = false
                    }
                    viewmodel.joinGame(joinGame.playerName, "", joinGame.isSpectator)
                })
            }

        }

        if (state.requestRoomPassword) {
            insertPasswordDialog(state.roomPassword, onJoinClicked = {

                setState {
                    this.requestRoomPassword = false
                }
                viewmodel.joinGame(state.playerName, state.roomPassword)
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

