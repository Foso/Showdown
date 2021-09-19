package showdown.web.wrapper.material.icons

import org.w3c.dom.events.Event
import react.ComponentClass

import react.RProps
import showdown.web.wrapper.material.ShareIconProps

@JsModule("@material-ui/icons/AddCircle")
external val AddCircleIconImport: dynamic

external interface AddCircleIconProps : RProps {
    var onClick: (Event) -> Unit
}

var AddCircleIcon: ComponentClass<ShareIconProps> = AddCircleIconImport.default
