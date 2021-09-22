package showdown.web.wrapper.material.icons

import org.w3c.dom.events.Event
import react.ComponentClass
import react.Props

@JsModule("@material-ui/icons/CheckCircle")
external val CheckCircleIconImport: dynamic

external interface CheckCircleIconProps : Props {
    var onClick: (Event) -> Unit
}

var CheckCircleIcon: ComponentClass<CheckCircleIconProps> = CheckCircleIconImport.default
