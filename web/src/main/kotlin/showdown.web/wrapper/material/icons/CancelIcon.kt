package showdown.web.wrapper.material.icons

import org.w3c.dom.events.Event
import react.RClass
import react.RProps

@JsModule("@material-ui/icons/Cancel")
external val CancelIconImport: dynamic

external interface CancelIconProps : RProps {
    var onClick: (Event) -> Unit
    var color : String
}

var CancelIcon: RClass<CancelIconProps> = CancelIconImport.default
