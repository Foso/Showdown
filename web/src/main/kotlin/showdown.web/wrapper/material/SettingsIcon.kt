package showdown.web.wrapper.material

import org.w3c.dom.events.Event
import react.RClass
import react.RProps

@JsModule("@material-ui/icons/Settings")
external val SettingsIconImport: dynamic

external interface SettingsIconProps :RProps {
    var onClick: (Event) -> Unit
}

var SettingsIcon: RClass<ShareIconProps> = SettingsIconImport.default
