@file:Suppress(
    "INTERFACE_WITH_SUPERCLASS",
    "OVERRIDING_FINAL_MEMBER",
    "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
    "CONFLICTING_OVERLOADS",
    "EXTERNAL_DELEGATION",
    "NESTED_CLASS_IN_EXTERNAL_INTERFACE"
)

package components.materialui

import de.jensklingenberg.showdown.web.components.StandardProps
import de.jensklingenberg.showdown.web.components.materialui.ListItemProps
import org.w3c.dom.events.Event
import org.w3c.dom.events.EventTarget
import react.RClass

@JsModule("@material-ui/core/Menu/Menu")
external val MenuImport: dynamic

external interface MenuProps : ListItemProps, StandardProps {
    var open: Boolean? get() = definedExternally; set(value) = definedExternally
    var anchorEl: EventTarget? get() = definedExternally; set(value) = definedExternally
    var onClose: (Event) -> Unit

}

var Menu: RClass<MenuProps> = MenuImport.default
