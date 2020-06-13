@file:Suppress(
    "INTERFACE_WITH_SUPERCLASS",
    "OVERRIDING_FINAL_MEMBER",
    "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
    "CONFLICTING_OVERLOADS",
    "EXTERNAL_DELEGATION",
    "NESTED_CLASS_IN_EXTERNAL_INTERFACE"
)

package components.materialui.icons

import components.materialui.IconProps
import react.RClass

@JsModule("@material-ui/icons/Android")
external val AndroidIconImport: dynamic

external interface AndroidIconProps : IconProps {
}

var AndroidIcon: RClass<AndroidIconProps> = AndroidIconImport.default
