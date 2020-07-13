package showdown.web.wrapper.material

import org.w3c.dom.events.Event
import react.RClass
import react.RProps

@JsModule("@material-ui/icons/AccountCircle")
external val AccountCircleIconImport: dynamic

external interface AccountCircleIconProps :RProps {
    var onClick: (Event) -> Unit
}

var AccountCircleIcon: RClass<ShareIconProps> = AccountCircleIconImport.default
