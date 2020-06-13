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
import react.RProps

@JsModule("@material-ui/core/Toolbar/Toolbar")
external val ToolbarImport: dynamic

external interface ToolbarProps : RProps {
    var disableGutters: Boolean? get() = definedExternally; set(value) = definedExternally
    var color: String? get() = definedExternally; set(value) = definedExternally

}

var Toolbar: RClass<ToolbarProps> = ToolbarImport.default

