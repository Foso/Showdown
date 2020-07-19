package showdown.web.wrapper.material.icons

import org.w3c.dom.events.Event
import react.RClass
import react.RProps
import showdown.web.wrapper.material.ShareIconProps

@JsModule("@material-ui/icons/Lock")
external val LockIconImport: dynamic

external interface LockIconProps :RProps {
    var onClick: (Event) -> Unit
}

var LockIcon: RClass<ShareIconProps> = LockIconImport.default
