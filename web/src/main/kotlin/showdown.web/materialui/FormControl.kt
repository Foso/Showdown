@file:Suppress(
    "INTERFACE_WITH_SUPERCLASS",
    "OVERRIDING_FINAL_MEMBER",
    "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
    "CONFLICTING_OVERLOADS",
    "EXTERNAL_DELEGATION",
    "NESTED_CLASS_IN_EXTERNAL_INTERFACE"
)

package components.materialui


import org.w3c.dom.events.Event
import react.RClass
import react.RProps
import react.ReactElement

@JsModule("@material-ui/core/FormControl/FormControl")
external val FormControlImport: dynamic

external interface FormControlProps : RProps {
    var component: ReactElement? get() = definedExternally; set(value) = definedExternally
    var disabled: Boolean? get() = definedExternally; set(value) = definedExternally
    var error: Boolean? get() = definedExternally; set(value) = definedExternally
    var fullWidth: Boolean? get() = definedExternally; set(value) = definedExternally
    var margin: String? get() = definedExternally; set(value) = definedExternally
    var onBlur: (Event) -> Unit
    var onFocus: (Event) -> Unit
    var required: Boolean? get() = definedExternally; set(value) = definedExternally
}

var FormControl: RClass<FormControlProps> = FormControlImport.default

