@file:Suppress(
    "INTERFACE_WITH_SUPERCLASS",
    "OVERRIDING_FINAL_MEMBER",
    "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
    "CONFLICTING_OVERLOADS",
    "EXTERNAL_DELEGATION",
    "NESTED_CLASS_IN_EXTERNAL_INTERFACE"
)

package components.materialui

import react.RClass

@JsModule("@material-ui/core/Checkbox/Checkbox")
external val CheckboxImport: dynamic

interface CheckboxProps : SwitchProps {
    var checked: Boolean
}

var Checkbox: RClass<CheckboxProps> = CheckboxImport.default

