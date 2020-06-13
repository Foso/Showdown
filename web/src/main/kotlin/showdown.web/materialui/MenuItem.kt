@file:Suppress(
    "INTERFACE_WITH_SUPERCLASS",
    "OVERRIDING_FINAL_MEMBER",
    "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
    "CONFLICTING_OVERLOADS",
    "EXTERNAL_DELEGATION",
    "NESTED_CLASS_IN_EXTERNAL_INTERFACE"
)

package components.materialui

import de.jensklingenberg.showdown.web.components.materialui.ListItemProps
import react.RClass

@JsModule("@material-ui/core/MenuItem/MenuItem")
external val MenuItemImport: dynamic

external interface MenuItemProps : ListItemProps {
    var role: String? get() = definedExternally; set(value) = definedExternally
    var selected: Boolean? get() = definedExternally; set(value) = definedExternally
    var value: dynamic
}

var MenuItem: RClass<MenuItemProps> = MenuItemImport.default
