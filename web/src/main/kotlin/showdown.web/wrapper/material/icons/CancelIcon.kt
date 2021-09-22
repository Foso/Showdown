package showdown.web.wrapper.material.icons

import org.w3c.dom.events.Event
import react.ComponentClass

import react.Props

@JsModule("@material-ui/icons/Cancel")
external val CancelIconImport: dynamic

external interface CancelIconProps : Props {
    var onClick: (Event) -> Unit
    var color: String
}

var CancelIcon: ComponentClass<CancelIconProps> = CancelIconImport.default
