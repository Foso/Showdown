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
import react.RComponent
import react.RProps
import react.RState

@JsModule("@material-ui/core/FormControlLabel/FormControlLabel")
external val FormControlLabelImport: dynamic

external interface FormControlLabelProps : RProps {
    var checked: dynamic /* String | Boolean */ get() = definedExternally; set(value) = definedExternally
    var control: RComponent<RProps, RState>
    var disabled: Boolean? get() = definedExternally; set(value) = definedExternally
    var inputRef: dynamic
    var label: String
    var name: String? get() = definedExternally; set(value) = definedExternally
    var onChange: (Event) -> Unit
    var value: String? get() = definedExternally; set(value) = definedExternally
}

var FormControlLabel: RClass<FormControlLabelProps> = FormControlLabelImport.default

