package showdown.web.wrapper.material

import org.w3c.dom.events.Event
import react.RClass
import react.RProps

@JsModule("@material-ui/icons/Visibility")
external val VisibilityIconImport: dynamic

external interface VisibilityIconProps :RProps {
    var onClick: (Event) -> Unit
}

var VisibilityIcon: RClass<VisibilityIconProps> = VisibilityIconImport.default
