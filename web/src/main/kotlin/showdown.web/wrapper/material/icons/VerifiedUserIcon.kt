package showdown.web.wrapper.material.icons

import org.w3c.dom.events.Event
import react.ComponentClass

import react.Props

@JsModule("@material-ui/icons/VerifiedUser")
external val VerifiedUserIconImport: dynamic

external interface VerifiedUserIconProps : Props {
    var onClick: (Event) -> Unit
    var color: String
}

var VerifiedUserIcon: ComponentClass<VerifiedUserIconProps> = VerifiedUserIconImport.default
