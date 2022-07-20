package showdown.web.ui.game.voting

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import de.jensklingenberg.showdown.model.Member
import dev.petuska.kmdc.checkbox.MDCCheckbox
import kotlinx.browser.window
import org.jetbrains.compose.web.css.*
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

fun getSpectators(members: List<Member>): List<Member> {
    return members.filter { it.isSpectator }
}

fun getPlayers(members: List<Member>): List<Member> {
    return members.filter { !it.isSpectator }
}

@Composable
fun GameView(gameViewmodel: GameViewmodelITF) {


    LaunchedEffect(Unit) {
        gameViewmodel.onCreate()
    }

    if (gameViewmodel.isRegistration.value) {
        val urlSearchParams = URLSearchParams(window.location.hash.substringAfter("?"))

        if (urlSearchParams.has(PARAM_UNAME)) {
            val uname = urlSearchParams.get(PARAM_UNAME) ?: ""
            gameViewmodel.joinGame(uname)
            gameViewmodel.onEntryPopupClosed()
        } else {
            JoinGameDialog {
                gameViewmodel.joinGame(it.playerName, it.roomPassword, it.isSpectator)
                gameViewmodel.onEntryPopupClosed()
            }
        }
    }
    if (gameViewmodel.isRoomPasswordNeeded.value) {
        InsertPasswordDialog {
            gameViewmodel.joinGame("", it)
        }
    }

    if (gameViewmodel.isConnectionError.value) {
        ConnectionErrorSnackbar(CONNECTION_ERROR) {
            gameViewmodel.onCreate()
        }
    }

    Div {

        Toolbar()

        Br { }
        Br { }

        OptionsList(gameViewmodel.options.value, gameViewmodel.selectedOption.value) { selectedIndex ->
            gameViewmodel.onSelectedVote(selectedIndex)
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
            MDCCheckbox(gameViewmodel.isSpectator.value, attrs = {
                onClick { gameViewmodel.setSpectatorStatus(!gameViewmodel.isSpectator.value) }
            })
            Text(Strings.IMSPECTATOR)
        }

        Div {
            if (gameViewmodel.results.value.isNotEmpty()) {
                ResultsList(gameViewmodel.results.value)
            }
        }

        VotersList(getPlayers(gameViewmodel.members.value))

        SpectatorsList(getSpectators(gameViewmodel.members.value))

    }
}



