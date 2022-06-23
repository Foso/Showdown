package showdown.web.wrapper.material.icons

import org.w3c.dom.events.Event
import react.ComponentClass
import react.Props

@JsModule("@material-ui/icons/Lock")
external val LockIconImport: dynamic

external interface LockIconProps : Props {
    var onClick: (Event) -> Unit
}

var LockIcon: ComponentClass<LockIconProps> = LockIconImport.default
