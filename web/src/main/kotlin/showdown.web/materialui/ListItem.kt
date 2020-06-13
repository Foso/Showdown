@file:Suppress(
    "INTERFACE_WITH_SUPERCLASS",
    "OVERRIDING_FINAL_MEMBER",
    "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
    "CONFLICTING_OVERLOADS",
    "EXTERNAL_DELEGATION",
    "NESTED_CLASS_IN_EXTERNAL_INTERFACE"
)

package de.jensklingenberg.showdown.web.components.materialui

import components.materialui.ButtonProps
import de.jensklingenberg.showdown.web.ui.common.LayoutProps
import react.RClass

@JsModule("@material-ui/core/ListItem/ListItem")
external val ListItemImport: dynamic


external interface ListItemProps : ButtonProps, LayoutProps {
    var button: Boolean? get() = definedExternally; set(value) = definedExternally
    var ContainerComponent: Any? get() = definedExternally; set(value) = definedExternally
    var ContainerProps: Any? get() = definedExternally; set(value) = definedExternally
    var dense: Boolean? get() = definedExternally; set(value) = definedExternally
    var disableGutters: Boolean? get() = definedExternally; set(value) = definedExternally
    var divider: Boolean? get() = definedExternally; set(value) = definedExternally

}

var ListItem: RClass<ListItemProps> = ListItemImport.default


