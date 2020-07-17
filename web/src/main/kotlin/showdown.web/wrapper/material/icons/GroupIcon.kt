package showdown.web.wrapper.material.icons

import org.w3c.dom.events.Event
import react.RClass
import react.RProps

@JsModule("@material-ui/icons/Group")
external val GroupIconImport: dynamic

external interface GroupIconProps : RProps {
    var onClick: (Event) -> Unit
    var color : String
}

var GroupIcon: RClass<GroupIconProps> = GroupIconImport.default
