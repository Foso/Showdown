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
import react.ReactElement


@JsModule("@material-ui/core/FormLabel/FormLabel")
external val FormLabelImport: dynamic

external interface FormLabelProps : RProps {
    var component: ReactElement? get() = definedExternally; set(value) = definedExternally
    var disabled: Boolean? get() = definedExternally; set(value) = definedExternally
    var error: Boolean? get() = definedExternally; set(value) = definedExternally
    var focused: Boolean? get() = definedExternally; set(value) = definedExternally
    var required: Boolean? get() = definedExternally; set(value) = definedExternally
}

var FormLabel: RClass<FormLabelProps> = FormLabelImport.default

