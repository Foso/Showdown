@file:Suppress(
    "INTERFACE_WITH_SUPERCLASS",
    "OVERRIDING_FINAL_MEMBER",
    "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
    "CONFLICTING_OVERLOADS",
    "EXTERNAL_DELEGATION",
    "NESTED_CLASS_IN_EXTERNAL_INTERFACE"
)

package components.materialui

import de.jensklingenberg.showdown.web.components.materialui.InputProps
import org.w3c.dom.events.Event
import react.RClass

@JsModule("@material-ui/core/Select/Select")
external val SelectImport: dynamic

external interface SelectProps : InputProps {
    var autoWidth: Boolean? get() = definedExternally; set(value) = definedExternally
    var displayEmpty: Boolean? get() = definedExternally; set(value) = definedExternally
    var IconComponent: dynamic get() = definedExternally; set(value) = definedExternally
    var input: dynamic get() = definedExternally; set(value) = definedExternally
    var MenuProps: Any? get() = definedExternally; set(value) = definedExternally
    var multiple: Boolean? get() = definedExternally; set(value) = definedExternally
    var native: Boolean? get() = definedExternally; set(value) = definedExternally
    var onClose: (Event) -> Unit
    var onOpen: (Event) -> Unit
    var open: Boolean? get() = definedExternally; set(value) = definedExternally
    var renderValue: dynamic
    var SelectDisplayProps: dynamic
}

var Select: RClass<SelectProps> = SelectImport.default

