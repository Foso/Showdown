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

@JsModule("@material-ui/core/InputLabel/InputLabel")
external val InputLabelImport: dynamic

external interface InputLabelProps : FormLabelProps {
    var disableAnimation: Boolean? get() = definedExternally; set(value) = definedExternally
    var FormLabelClasses: Any? get() = definedExternally; set(value) = definedExternally
    var shrink: Boolean? get() = definedExternally; set(value) = definedExternally
    var htmlFor: String
    var id: String? get() = definedExternally; set(value) = definedExternally

}

var InputLabel: RClass<InputLabelProps> = InputLabelImport.default
