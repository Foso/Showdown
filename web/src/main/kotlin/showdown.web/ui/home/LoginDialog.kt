package showdown.web.ui.home

import com.soywiz.klock.DateTime
import de.jensklingenberg.showdown.model.VoteOptions
import kotlinext.js.getOwnPropertyNames
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import materialui.components.button.button
import materialui.components.button.enums.ButtonColor
import materialui.components.button.enums.ButtonVariant
import materialui.components.formcontrol.enums.FormControlVariant
import materialui.components.menuitem.menuItem
import materialui.components.textfield.textField
import org.w3c.dom.HTMLInputElement
import react.*
import react.dom.div
import react.dom.key
import react.dom.p
import kotlin.browser.*
import kotlin.js.Console
import kotlin.reflect.KClass

/*

style = kotlinext.js.js {
                        this.textAlign = "right"
                    }
 */

interface LoginProps : RProps {
    var startFrom: (Int,String)->Unit
}

interface LoginState : RState {
    var timerStarted: (Int,String)->Unit
    var nowDate : DateTime
    var diffSecs : Double
    var gameModeId: Int
    var customOptions : String
}

class LoginDialog(prps: LoginProps) : RComponent<LoginProps, LoginState>(prps) {

    init {
        console.log("HERHEIKRHEIR INIT")

    }
    override fun LoginState.init(props: LoginProps) {
        timerStarted = props.startFrom
        nowDate = DateTime.now()
        diffSecs = 0.0
        gameModeId =0
        customOptions=""
    }

    var timerID: Int? = null
    var timerRunnung : Boolean = false
    override fun componentDidUpdate(prevProps: LoginProps, prevState: LoginState, snapshot: Any) {
        console.log("componentDidUpdate")
        timerRunnung=true


    }

    override fun componentDidMount() {


    }

    override fun componentWillUnmount() {


    }


    override fun RBuilder.render() {

    }
}

fun RBuilder.loginDialog(gameModeId: Int,onSave:  (Int,String)->Unit ) = child(LoginDialog::class) {
   attrs.startFrom=onSave

}
