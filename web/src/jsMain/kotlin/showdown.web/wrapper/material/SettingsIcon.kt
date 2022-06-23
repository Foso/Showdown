package showdown.web.wrapper.material

import org.w3c.dom.events.Event
import react.ComponentClass

import react.Props

@JsModule("@material-ui/icons/Settings")
external val SettingsIconImport: dynamic

external interface SettingsIconProps : Props {
    var onClick: (Event) -> Unit
}

var SettingsIcon: ComponentClass<SettingsIconProps> = SettingsIconImport.default
