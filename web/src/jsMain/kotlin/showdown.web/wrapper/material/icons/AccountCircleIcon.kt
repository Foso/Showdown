package showdown.web.wrapper.material.icons

import org.w3c.dom.events.Event
import react.ComponentClass
import react.Props
import showdown.web.wrapper.material.ShareIconProps

@JsModule("@material-ui/icons/AccountCircle")
external val AccountCircleIconImport: dynamic

external interface AccountCircleIconProps : Props {
    var onClick: (Event) -> Unit
}

var AccountCircleIcon: ComponentClass<ShareIconProps> = AccountCircleIconImport.default
