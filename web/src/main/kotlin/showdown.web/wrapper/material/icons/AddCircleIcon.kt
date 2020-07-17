package showdown.web.wrapper.material.icons

import org.w3c.dom.events.Event
import react.RClass
import react.RProps
import showdown.web.wrapper.material.ShareIconProps

@JsModule("@material-ui/icons/AddCircle")
external val AddCircleIconImport: dynamic

external interface AddCircleIconProps :RProps {
    var onClick: (Event) -> Unit
}

var AddCircleIcon: RClass<ShareIconProps> = AddCircleIconImport.default
