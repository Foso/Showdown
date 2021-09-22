package showdown.web.wrapper.material.icons

import org.w3c.dom.events.Event
import react.ComponentClass

import react.Props
import showdown.web.wrapper.material.ShareIconProps

@JsModule("@material-ui/icons/LockOpen")
external val LockOpenIconImport: dynamic

external interface LockOpenIconProps : Props {
    var onClick: (Event) -> Unit
}

var LockOpenIcon: ComponentClass<ShareIconProps> = LockOpenIconImport.default
