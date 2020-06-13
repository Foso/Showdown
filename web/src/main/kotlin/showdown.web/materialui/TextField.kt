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

@JsModule("@material-ui/core/TextField/TextField")
external val TextFieldImport: dynamic


external interface TextFieldProps : FormControlProps {
    var autoComplete: String? get() = definedExternally; set(value) = definedExternally
    var autoFocus: Boolean? get() = definedExternally; set(value) = definedExternally
    var children: dynamic get() = definedExternally; set(value) = definedExternally
    var defaultValue: dynamic /* String | Number */ get() = definedExternally; set(value) = definedExternally
    var FormHelperTextProps: Any? get() = definedExternally; set(value) = definedExternally
    var helperText: dynamic get() = definedExternally; set(value) = definedExternally
    var id: String? get() = definedExternally; set(value) = definedExternally
    var InputLabelProps: Any? get() = definedExternally; set(value) = definedExternally
    var InputProps: Any? get() = definedExternally; set(value) = definedExternally
    var inputProps: dynamic /* "IndexedAccessType" kind unsupported yet here! (build/node_modules/@material-ui/core/TextField/TextField.d.ts:24:15 to 24:40) */ get() = definedExternally; set(value) = definedExternally
    var inputRef: dynamic get() = definedExternally; set(value) = definedExternally
    var label: dynamic get() = definedExternally; set(value) = definedExternally
    var multiline: dynamic get() = definedExternally; set(value) = definedExternally
    var name: String? get() = definedExternally; set(value) = definedExternally
    var onChange: (Event) -> Unit
    var placeholder: String? get() = definedExternally; set(value) = definedExternally
    var rows: dynamic /* String | Number */ get() = definedExternally; set(value) = definedExternally
    var rowsMax: dynamic /* String | Number */ get() = definedExternally; set(value) = definedExternally
    var select: Boolean? get() = definedExternally; set(value) = definedExternally
    var SelectProps: Any? get() = definedExternally; set(value) = definedExternally
    var type: String? get() = definedExternally; set(value) = definedExternally
    var value: String? /* String | Number | Array<dynamic /* String | Number */> */ get() = definedExternally; set(value) = definedExternally
}

var TextField: RClass<TextFieldProps> = TextFieldImport.default
