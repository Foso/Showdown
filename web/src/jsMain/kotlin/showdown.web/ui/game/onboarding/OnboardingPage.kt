package showdown.web.ui.game.onboarding


import androidx.compose.runtime.*
import kotlinx.browser.window
import org.jetbrains.compose.web.dom.*
import showdown.web.common.H2
import showdown.web.common.H3
import showdown.web.common.JKTextField
import showdown.web.ui.Strings.Companion.SHOWDOWN
import showdown.web.ui.Strings.Companion.SHOWDOWN_REPO_URL


/**
 * On this view, the user can see an explanation
 * on how to use Showdown and can select a room name
 */
@Composable
fun OnboardingScreen() {

    var roomName by remember { mutableStateOf("") }

    H1 {
        Text("Showdown - Scrum Poker Web App")
    }
    H2("How to:")

    H3("1) Choose a room name and go to the room")
    Div {

        JKTextField(roomName, label = "Choose new room name", onTextChange = {
            roomName = (it)
        }, onEnterPressed = {
            window.location.href = "/#/room/${roomName}";
        })

    }
    Div {
        Button(attrs = {
            onClick {
                window.location.href = "/#/room/${roomName}";
            }
        }) {
            Text("Go to room")

        }
    }
    H3("2) Choose a voter name\n")

    Img("/web/img/joingame.png")
    H3("3) Share the room link")

    H3("4) Select an Option")

    Img("/web/img/option.png")
    H3("5) Click Show Votes to see the votes")

    Img("/web/img/showvotes.png")
    Br {}
    Hr { }
    Br { }
    Text("\uD83D\uDC47")
    Br { }
    Div {

        JKTextField(roomName, label = "Choose new room name", onTextChange = {
            roomName = (it)
        }, onEnterPressed = {
            window.location.href = "/#/room/${roomName}";
        })

    }
    Div {
        Button(attrs = {
            onClick {
                window.location.href = "/#/room/${roomName}";
            }
        }) {
            Text("Go to room")

        }
    }
    Br { }
    Br { }
    Br { }
    Div {
        A(href = SHOWDOWN_REPO_URL) {
            Text(SHOWDOWN)
        }

        Text(" by ")
        A(href = "http://jensklingenberg.de") {
            Text("Jens Klingenberg")
        }
        Text(". The source code is licensed under ")
        A(href = "http://www.apache.org/licenses/") {
            Text("Apache 2.0")
        }

    }
}



