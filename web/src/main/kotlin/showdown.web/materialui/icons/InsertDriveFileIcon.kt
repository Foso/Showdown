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

@JsModule("@material-ui/icons/InsertDriveFile")
external val InsertDriveFileIconImport: dynamic

external interface InsertDriveFileIconProps : ButtonProps {
}

var InsertDriveFileIcon: RClass<InsertDriveFileIconProps> = InsertDriveFileIconImport.default
