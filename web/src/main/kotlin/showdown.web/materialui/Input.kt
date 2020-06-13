@file:Suppress(
    "INTERFACE_WITH_SUPERCLASS",
    "OVERRIDING_FINAL_MEMBER",
    "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
    "CONFLICTING_OVERLOADS",
    "EXTERNAL_DELEGATION",
    "NESTED_CLASS_IN_EXTERNAL_INTERFACE"
)

package de.jensklingenberg.showdown.web.components.materialui

import de.jensklingenberg.showdown.web.ui.common.LayoutProps
import org.w3c.dom.events.Event
import react.RClass
import react.RComponent
import react.RProps
import react.RState
import kotlin.js.Json

@JsModule("@material-ui/core/Input/Input")
external val InputImport: dynamic

external interface InputProps : RProps, LayoutProps {
    var autoComplete: String? get() = definedExternally; set(value) = definedExternally
    var autoFocus: Boolean? get() = definedExternally; set(value) = definedExternally
    var defaultValue: dynamic /* String | Number */ get() = definedExternally; set(value) = definedExternally
    var disabled: Boolean? get() = definedExternally; set(value) = definedExternally
    var disableUnderline: Boolean? get() = definedExternally; set(value) = definedExternally
    var endAdornment: RComponent<RProps, RState>? get() = definedExternally; set(value) = definedExternally
    var error: Boolean? get() = definedExternally; set(value) = definedExternally
    var fullWidth: Boolean? get() = definedExternally; set(value) = definedExternally
    var id: String? get() = definedExternally; set(value) = definedExternally
    var inputComponent: RComponent<InputComponentProps, RState>? get() = definedExternally; set(value) = definedExternally
    var inputProps: Json? get() = definedExternally; set(value) = definedExternally
    var inputRef: dynamic get() = definedExternally; set(value) = definedExternally
    var margin: String? /* "dense" */ get() = definedExternally; set(value) = definedExternally
    var multiline: Boolean? get() = definedExternally; set(value) = definedExternally
    var name: String? get() = definedExternally; set(value) = definedExternally
    var placeholder: String? get() = definedExternally; set(value) = definedExternally
    var rows: dynamic /* String | Number */ get() = definedExternally; set(value) = definedExternally
    var rowsMax: dynamic /* String | Number */ get() = definedExternally; set(value) = definedExternally
    var startAdornment: dynamic get() = definedExternally; set(value) = definedExternally
    var type: String? get() = definedExternally; set(value) = definedExternally
    var value: dynamic /* String | Number | Array<dynamic /* String | Number */> */ get() = definedExternally; set(value) = definedExternally
    var onChange: (Event) -> Unit
    var onKeyUp: dynamic
    var onKeyDown: dynamic
}

external interface InputComponentProps : InputProps {
    @nativeGetter
    operator fun get(arbitrary: String): Any?

    @nativeSetter
    operator fun set(arbitrary: String, value: Any)
}

var Input: RClass<InputProps> = InputImport.default
