package showdown.web.wrapper.material.icons

import org.w3c.dom.events.Event
import react.RClass
import react.RProps
import showdown.web.wrapper.material.ShareIconProps

@JsModule("@material-ui/icons/LockOpen")
external val LockOpenIconImport: dynamic

external interface LockOpenIconProps :RProps {
    var onClick: (Event) -> Unit
}

var LockOpenIcon: RClass<ShareIconProps> = LockOpenIconImport.default
