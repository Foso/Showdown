@file:Suppress(
    "INTERFACE_WITH_SUPERCLASS",
    "OVERRIDING_FINAL_MEMBER",
    "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
    "CONFLICTING_OVERLOADS",
    "EXTERNAL_DELEGATION",
    "NESTED_CLASS_IN_EXTERNAL_INTERFACE"
)

package components.materialui

import de.jensklingenberg.showdown.web.components.materialui.ListProps
import react.RClass

@JsModule("@material-ui/core/MenuList/MenuList")
external val MenuListImport: dynamic

external interface MenuListProps : ListProps {
    var onKeyDown: EventHandlerFunction? get() = definedExternally; set(value) = definedExternally
}

var MenuList: RClass<MenuListProps> = MenuListImport.default