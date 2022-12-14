package showdown.web.ui.game.voting

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import de.jensklingenberg.showdown.model.Member
import dev.petuska.kmdc.checkbox.MDCCheckbox
import kotlinx.browser.window
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.A
import org.jetbrains.compose.web.dom.Br
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.url.URLSearchParams
import showdown.web.Application.Companion.PARAM_UNAME
import showdown.web.common.ConnectionErrorSnackbar
import showdown.web.common.HeightSpacer
import showdown.web.ui.Strings
import showdown.web.ui.Strings.Companion.CONNECTION_ERROR
import showdown.web.ui.game.voting.toolbar.Toolbar



@Composable
fun GameView(gameViewmodelItf: GameViewmodelItf) {

    fun getSpectators(members: List<Member>): List<Member> = members.filter { it.isSpectator }

    fun getVoters(members: List<Member>): List<Member> = members.filter { !it.isSpectator }

    LaunchedEffect(Unit) {
        gameViewmodelItf.onCreate()
    }

    if (gameViewmodelItf.isRegistration.value) {
        val urlSearchParams = URLSearchParams(window.location.hash.substringAfter("?"))

        if (urlSearchParams.has(PARAM_UNAME)) {
            val uname = urlSearchParams.get(PARAM_UNAME) ?: ""
            gameViewmodelItf.joinGame(uname)
            gameViewmodelItf.onEntryPopupClosed()
        } else {
            JoinGameDialog {
                gameViewmodelItf.joinGame(it.playerName, it.roomPassword, it.isSpectator)
                gameViewmodelItf.onEntryPopupClosed()
            }
        }
    }
    if (gameViewmodelItf.isRoomPasswordNeeded.value) {
        InsertPasswordDialog {
            gameViewmodelItf.joinGame("", it)
        }
    }

    if (gameViewmodelItf.isConnectionError.value) {
        ConnectionErrorSnackbar(CONNECTION_ERROR) {
            gameViewmodelItf.onCreate()
        }
    }

    Div {

        Toolbar()



        Br { }
        Br { }

        OptionsList(gameViewmodelItf.options.value, gameViewmodelItf.selectedOption.value) { selectedIndex ->
            gameViewmodelItf.onSelectedVote(selectedIndex)
        }

        HeightSpacer(40.px)
        Div(attrs = {
            onClick {

            }
            style {
                textAlign("center")
                justifyContent(JustifyContent.Center)
                alignItems(AlignItems.Center)
            }
        }) {
            MDCCheckbox(gameViewmodelItf.isSpectator.value, label = Strings.IM_SPECTATOR, attrs = {
                onClick { gameViewmodelItf.setSpectatorStatus(!gameViewmodelItf.isSpectator.value) }
            })

        }

        Div {
            if (gameViewmodelItf.results.value.isNotEmpty()) {
                ResultsList(gameViewmodelItf.results.value)
            }
        }

        VotersList(getVoters(gameViewmodelItf.members.value))

        SpectatorsList(getSpectators(gameViewmodelItf.members.value))

    }
}



