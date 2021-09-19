package showdown.web.wrapper.material.icons

import org.w3c.dom.events.Event
import react.ComponentClass

import react.RProps

@JsModule("@material-ui/icons/TouchApp")
external val TouchAppIconImport: dynamic

external interface TouchAppIconProps : RProps {
    var onClick: (Event) -> Unit
    var color: String
}

var TouchAppIcon: ComponentClass<TouchAppIconProps> = TouchAppIconImport.default
