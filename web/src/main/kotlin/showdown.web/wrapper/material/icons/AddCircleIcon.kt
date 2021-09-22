package showdown.web.wrapper.material.icons

import org.w3c.dom.events.Event
import react.ComponentClass

import react.Props
import showdown.web.wrapper.material.ShareIconProps

@JsModule("@material-ui/icons/AddCircle")
external val AddCircleIconImport: dynamic

external interface AddCircleIconProps : Props {
    var onClick: (Event) -> Unit
}

var AddCircleIcon: ComponentClass<ShareIconProps> = AddCircleIconImport.default
