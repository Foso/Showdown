@file:Suppress(
    "INTERFACE_WITH_SUPERCLASS",
    "OVERRIDING_FINAL_MEMBER",
    "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
    "CONFLICTING_OVERLOADS",
    "EXTERNAL_DELEGATION",
    "NESTED_CLASS_IN_EXTERNAL_INTERFACE"
)

package de.jensklingenberg.showdown.web.components.materialui.icons

import components.materialui.ButtonProps
import react.RClass

@JsModule("@material-ui/icons/InfoOutlined")
external val InfoOutlinedIconImport: dynamic

external interface InfoOutlinedIconProps : ButtonProps {
}

var InfoOutlinedIcon: RClass<InfoOutlinedIconProps> = InfoOutlinedIconImport.default
