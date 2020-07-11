package showdown.web.ui.start

import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import materialui.components.formcontrol.enums.FormControlVariant
import materialui.components.textfield.textField
import org.w3c.dom.HTMLInputElement
import react.*
import react.dom.button
import react.dom.div
import showdown.web.ui.home.SettingsProps
import showdown.web.ui.home.TTickerState
import kotlin.browser.window
 interface StartPageState : RState{
     var roomName : String
 }

class StartPage : RComponent<SettingsProps, StartPageState>() {

    override fun StartPageState.init() {
        this.roomName=""
    }

    override fun RBuilder.render() {

        textField {
            attrs {
                variant = FormControlVariant.filled
                value(state.roomName)
                label {
                    +"Set a new room name:"
                }
                onChangeFunction = {
                    val target = it.target as HTMLInputElement

                    setState {
                        this.roomName = target.value
                    }
                }
            }
            +"HHHHHER"
        }

        div {
          button {
              +"GO"
              attrs {
                  onClickFunction={
                      window.location.href = "/#/room/${state.roomName}";
                  }
              }
          }
       }


    }
}

fun RBuilder.startPage() = child(StartPage::class) {

}
