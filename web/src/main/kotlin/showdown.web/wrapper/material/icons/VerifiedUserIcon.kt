package showdown.web.wrapper.material.icons

import org.w3c.dom.events.Event
import react.RClass
import react.RProps

@JsModule("@material-ui/icons/VerifiedUser")
external val VerifiedUserIconImport: dynamic

external interface VerifiedUserIconProps : RProps {
    var onClick: (Event) -> Unit
    var color : String
}

var VerifiedUserIcon: RClass<VerifiedUserIconProps> = VerifiedUserIconImport.default
