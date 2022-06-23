package showdown.web.wrapper.material.icons

import org.w3c.dom.events.Event
import react.ComponentClass

import react.Props

@JsModule("@material-ui/icons/Group")
external val GroupIconImport: dynamic

external interface GroupIconProps : Props {
    var onClick: (Event) -> Unit
    var color: String
}

var GroupIcon: ComponentClass<GroupIconProps> = GroupIconImport.default
