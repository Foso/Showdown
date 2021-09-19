package showdown.web.wrapper.material

import org.w3c.dom.events.Event
import react.ComponentClass

import react.RProps

@JsModule("@material-ui/icons/Visibility")
external val VisibilityIconImport: dynamic

external interface VisibilityIconProps : RProps {
    var onClick: (Event) -> Unit
}

var VisibilityIcon: ComponentClass<VisibilityIconProps> = VisibilityIconImport.default
