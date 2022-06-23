package showdown.web.ui.onboarding


import de.jensklingenberg.showdown.SHOWDOWN_REPO_URL
import kotlinx.browser.window
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import kotlinx.html.js.onKeyDownFunction
import materialui.components.formcontrol.enums.FormControlVariant
import materialui.components.textfield.textField
import org.w3c.dom.HTMLInputElement
import react.Props
import react.dom.a
import react.dom.attrs
import react.dom.br
import react.dom.button
import react.dom.div
import react.dom.h1
import react.dom.h2
import react.dom.h3
import react.dom.hr
import react.dom.img
import react.fc
import react.useState


/**
 * On this view, the user can see an explanation
 * on how to use Showdown and can select a room name
 */
fun onboardingScreen() = fc<Props> {

    val (roomName, setRoomName) = useState("")

    h1 {
        +"Showdown - Scrum Poker Web App"
    }
    h2 { +"How to:" }
    div {
        h3 {
            +"1) Choose a room name and go to the room"
        }
        textField {
            attrs {
                variant = FormControlVariant.filled
                value(roomName)
                label {
                    +"Choose new room name:"
                }
                onKeyDownFunction = {
                    if (it.type == "keydown" && it.asDynamic()["key"] == "Enter") {
                        window.location.href = "/#/room/${roomName}";
                    }
                }

                onChangeFunction = {
                    val target = it.target as HTMLInputElement
                    setRoomName(target.value)
                }
            }
        }

        div {
            button {
                +"Go to room"
                attrs {
                    onClickFunction = {
                        window.location.href = "/#/room/${roomName}";
                    }
                }
            }
        }
    }
    div {
        h3 {
            +"2) Choose a voter name\n"
        }
        img {
            attrs {
                src = "/web/img/joingame.png"
            }
        }
    }
    div {
        h3 {
            +"3) Share the room link"
        }
    }
    div {
        h3 {
            +"4) Select an Option"
        }
        img {
            attrs {
                src = "/web/img/option.png"
            }
        }
    }
    div {
        h3 {
            +"5) Click Show Votes to see the votes"
        }
        img {
            attrs {
                src = "/web/img/showvotes.png"
            }
        }
    }
    br { }
    hr { }
    br { }
    //Finger down
    div { +"\uD83D\uDC47" }
    br { }
    textField {
        attrs {
            variant = FormControlVariant.filled
            value(roomName)
            label {
                +"Choose new room name:"
            }
            onChangeFunction = {
                val target = it.target as HTMLInputElement
                setRoomName(target.value)
            }

            onKeyDownFunction = {
                if (it.type == "keydown" && it.asDynamic()["key"] == "Enter") {
                    window.location.href = "/#/room/${roomName}";
                }
            }
        }
    }

    div {
        button {
            +"Go to room"
            attrs {
                onClickFunction = {
                    window.location.href = "/#/room/${roomName}";
                }
            }
        }
    }
    br {}
    br { }
    br { }
    div {
        a(href = SHOWDOWN_REPO_URL) {
            +"Showdown"
        }

        +" by "
        a(href = "http://jensklingenberg.de") {
            +"Jens Klingenberg"
        }
        +". The source code is licensed under "
        a(href = "http://www.apache.org/licenses/") {
            +"Apache 2.0"
        }

    }
}



