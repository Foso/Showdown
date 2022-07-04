package showdown.web.ui.game.voting

import androidx.compose.runtime.*
import de.jensklingenberg.showdown.model.Member
import dev.petuska.kmdc.checkbox.MDCCheckbox
import kotlinx.browser.document
import kotlinx.browser.window
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import org.w3c.dom.url.URLSearchParams
import showdown.web.Application.Companion.PARAM_UNAME
import showdown.web.common.ConnectionErrorSnackbar
import showdown.web.common.HeightSpacer
import showdown.web.ui.Strings
import showdown.web.ui.Strings.Companion.EROR
import showdown.web.ui.game.voting.settings.SettingsDialog

fun getSpectators(members: List<Member>): List<Member> {
    return members.filter { it.isSpectator }
}

fun getPlayers(members: List<Member>): List<Member> {
    return members.filter { !it.isSpectator }
}

@Composable
fun GameView(gameViewmodel: GameContract.Viewmodel) {


    /* TODO: PASSWORD LINK */

    var openSettings by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        gameViewmodel.onCreate()
    }
    val roomName =
        window.location.toString().substringAfter("/room/").substringBefore("/").substringBefore("?")

     document.title = "Showdown - $roomName"

    if (openSettings) {
        SettingsDialog(gameViewmodel) {
            openSettings = false
        }
    }

    if (gameViewmodel.showEntryPopup.value) {
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

    if (gameViewmodel.showConnectionError.value) {
        ConnectionErrorSnackbar(EROR) {
            gameViewmodel.onCreate()
        }
    }

    Div {

        Toolbar(
            onNewVotingClicked = { gameViewmodel.reset() },
            onShowVotesClicked = { gameViewmodel.showVotes() },
            onOpenSettings = { openSettings = true }, seconds = gameViewmodel.timer.value)

        Br { }
        Br { }

        OptionsList(gameViewmodel)

        HeightSpacer(40.px)
        Div(attrs = {
            onClick {

            }
            style {
                textAlign("center")
                //  display(DisplayStyle.Flex)
                justifyContent(JustifyContent.Center)
                alignItems(AlignItems.Center)
                //property("width", "fit-content")
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

        VotersList(gameViewmodel)

        SpectatorsList(gameViewmodel)

    }
}



