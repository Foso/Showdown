package showdown.web.wrapper.material.icons

import org.w3c.dom.events.Event
import react.RClass
import react.RProps
import showdown.web.wrapper.material.ShareIconProps

@JsModule("@material-ui/icons/CheckCircle")
external val CheckCircleIconImport: dynamic

external interface CheckCircleIconProps :RProps {
    var onClick: (Event) -> Unit
}

var CheckCircleIcon: RClass<ShareIconProps> = CheckCircleIconImport.default
