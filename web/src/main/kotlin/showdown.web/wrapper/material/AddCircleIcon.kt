package showdown.web.wrapper.material

import org.w3c.dom.events.Event
import react.RClass
import react.RProps

@JsModule("@material-ui/icons/AddCircle")
external val AddCircleIconImport: dynamic

external interface AddCircleIconProps :RProps {
    var onClick: (Event) -> Unit
}

var AddCircleIcon: RClass<ShareIconProps> = AddCircleIconImport.default
