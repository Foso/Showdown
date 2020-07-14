package showdown.web.ui.start

import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import kotlinx.html.js.onKeyDownFunction
import kotlinx.html.onKeyDown
import materialui.components.formcontrol.enums.FormControlVariant
import materialui.components.textfield.textField
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.KeyboardEvent
import react.RBuilder
import react.RComponent
import react.RState
import react.dom.*
import react.setState
import showdown.web.ui.home.SettingsProps
import kotlin.browser.window

interface StartPageState : RState {
    var roomName: String
}

class StartPage : RComponent<SettingsProps, StartPageState>() {

    override fun StartPageState.init() {
        this.roomName = ""
    }

    override fun RBuilder.render() {

        h1 {
            +"Showdown - Scrum Poker Web App"
        }
        h2 { +"How to:" }
        div {
            p {
                +"1) Choose a room name and go to the room"
            }
            textField {
                attrs {
                    variant = FormControlVariant.filled
                    value(state.roomName)
                    label {
                        +"Choose new room name:"
                    }
                    onKeyDownFunction={
                        if(it.type=="keydown" && it.asDynamic()["key"] == "Enter"){

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
        div {
            p {
                +"2) Choose a player name\n"
            }
            img {
                attrs {
                    src = "/web/img/joingame.png"
                }
            }
        }
        div {
            p {
                +"3) Share the room link"
            }

        }
        div {
            p {
                +"4) Select an Option"
            }
            img {
                attrs {
                    src = "/web/img/option.png"
                }
            }
        }
        div {

            p {
                +"5) When you are ready click Show Votes"
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
        div { +"\uD83D\uDC47" }//Finger down
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
                onKeyDownFunction={
                    if(it.type=="keydown" && it.asDynamic()["key"] == "Enter"){

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
}

fun RBuilder.startPage() = child(StartPage::class) {

}
