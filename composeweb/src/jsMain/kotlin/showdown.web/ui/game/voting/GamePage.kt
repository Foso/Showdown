package showdown.web.ui.game.voting

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import de.jensklingenberg.showdown.model.Member
import dev.petuska.kmdc.checkbox.MDCCheckbox
import kotlinx.browser.window
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Br
import org.jetbrains.compose.web.dom.Div
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
fun GameView(gameViewmodelITF: GameViewmodelITF) {


    LaunchedEffect(Unit) {
        gameViewmodelITF.onCreate()
    }

    if (gameViewmodelITF.isRegistration.value) {
        val urlSearchParams = URLSearchParams(window.location.hash.substringAfter("?"))

        if (urlSearchParams.has(PARAM_UNAME)) {
            val uname = urlSearchParams.get(PARAM_UNAME) ?: ""
            gameViewmodelITF.joinGame(uname)
            gameViewmodelITF.onEntryPopupClosed()
        } else {
            JoinGameDialog {
                gameViewmodelITF.joinGame(it.playerName, it.roomPassword, it.isSpectator)
                gameViewmodelITF.onEntryPopupClosed()
            }
        }
    }
    if (gameViewmodelITF.isRoomPasswordNeeded.value) {
        InsertPasswordDialog {
            gameViewmodelITF.joinGame("", it)
        }
    }

    if (gameViewmodelITF.isConnectionError.value) {
        ConnectionErrorSnackbar(CONNECTION_ERROR) {
            gameViewmodelITF.onCreate()
        }
    }

    Div {

        Toolbar()

        Br { }
        Br { }

        OptionsList(gameViewmodelITF.options.value, gameViewmodelITF.selectedOption.value) { selectedIndex ->
            gameViewmodelITF.onSelectedVote(selectedIndex)
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
            MDCCheckbox(gameViewmodelITF.isSpectator.value, label = Strings.IMSPECTATOR, attrs = {
                onClick { gameViewmodelITF.setSpectatorStatus(!gameViewmodelITF.isSpectator.value) }
            })

        }

        Div {
            if (gameViewmodelITF.results.value.isNotEmpty()) {
                ResultsList(gameViewmodelITF.results.value)
            }
        }

        VotersList(getPlayers(gameViewmodelITF.members.value))

        SpectatorsList(getSpectators(gameViewmodelITF.members.value))

    }
}



