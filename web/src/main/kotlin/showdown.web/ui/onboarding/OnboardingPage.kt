package showdown.web.ui.onboarding

import kotlinx.html.DIV
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import kotlinx.html.js.onKeyDownFunction
import materialui.components.formcontrol.enums.FormControlVariant
import materialui.components.textfield.textField
import org.w3c.dom.HTMLInputElement
import react.RBuilder
import react.RComponent
import react.RState
import react.dom.*
import react.setState
import showdown.web.ui.game.SettingsProps
import kotlin.browser.window

interface OnboardingPageState : RState {
    var roomName: String
}

/**
 * On this view, the user can see an explanation
 * on how to use Showdown and can select a room name
 */
class OnboardingPage : RComponent<SettingsProps, OnboardingPageState>() {

    override fun OnboardingPageState.init() {
        this.roomName = ""
    }

    override fun RBuilder.render() {

        h1 {
            +"Showdown - Scrum Poker Web App"
        }
        h2 { +"How to:" }
        div {
            step1()
        }
        div {
            step2()
        }
        div {
            step3()
        }
        div {
            step4()
        }
        div {
            step5()
        }
        br { }
        hr { }
        br { }
        fingerDown()//Finger down
        br { }
        goToRoom()
        br {}
        br { }
        br { }
        footer()
    }

    private fun RBuilder.footer() {
        div {
            a(href = "https://github.com/Foso/Showdown") {
                +"Showdown"
            }

            +" by "
            a(href = "http://www.jensklingenberg.de") {
                +"Jens Klingenberg"
            }
            +". The source code is licensed under "
            a(href = "http://www.apache.org/licenses/") {
                +"Apache 2.0"
            }

        }
    }

    private fun RBuilder.goToRoom() {
        textField {
            attrs {
                variant = FormControlVariant.filled
                value(state.roomName)
                label {
                    +"Choose new room name:"
                }
                onChangeFunction = {
                    val target = it.target as HTMLInputElement

                    setState {
                        this.roomName = target.value
                    }
                }
                onKeyDownFunction = {
                    if (it.type == "keydown" && it.asDynamic()["key"] == "Enter") {

                        window.location.href = "/#/room/${state.roomName}";

                    }

                }
            }
        }

        div {
            button {
                +"Go to room"
                attrs {
                    onClickFunction = {
                        window.location.href = "/#/room/${state.roomName}";
                    }
                }
            }
        }
    }

    private fun RBuilder.fingerDown() {
        div { +"\uD83D\uDC47" }
    }

    private fun RDOMBuilder<DIV>.step5() {
        h3 {
            +"5) Click Show Votes to see the votes"
        }
        img {
            attrs {
                src = "/web/img/showvotes.png"
            }
        }
    }

    private fun RDOMBuilder<DIV>.step4() {
        h3 {
            +"4) Select an Option"
        }
        img {
            attrs {
                src = "/web/img/option.png"
            }
        }
    }

    private fun RDOMBuilder<DIV>.step3() {
        h3 {
            +"3) Share the room link"
        }
    }

    private fun RDOMBuilder<DIV>.step2() {
        h3 {
            +"2) Choose a player name\n"
        }
        img {
            attrs {
                src = "/web/img/joingame.png"
            }
        }
    }

    private fun RDOMBuilder<DIV>.step1() {
        h3 {
            +"1) Choose a room name and go to the room"
        }
        textField {
            attrs {
                variant = FormControlVariant.filled
                value(state.roomName)
                label {
                    +"Choose new room name:"
                }
                onKeyDownFunction = {
                    if (it.type == "keydown" && it.asDynamic()["key"] == "Enter") {

                        window.location.href = "/#/room/${state.roomName}";

                    }

                }

                onChangeFunction = {
                    val target = it.target as HTMLInputElement

                    setState {
                        this.roomName = target.value
                    }
                }
            }
        }

        div {
            button {
                +"Go to room"
                attrs {
                    onClickFunction = {
                        window.location.href = "/#/room/${state.roomName}";
                    }
                }
            }
        }
    }


}

fun RBuilder.startPage() = child(OnboardingPage::class) {

}
