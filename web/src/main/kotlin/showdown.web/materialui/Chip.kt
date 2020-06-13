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

@JsModule("@material-ui/core/Chip/Chip")
external val ChipImport: dynamic

external interface ChipProps : RProps {
    var avatar: ReactElement? get() = definedExternally; set(value) = definedExternally
    var component: dynamic
    var deleteIcon: ReactElement? get() = definedExternally; set(value) = definedExternally
    var label: dynamic
    var onDelete: (Event) -> Unit
    var onKeyDown: (Event) -> Unit
}

var Chip: RClass<ChipProps> = ChipImport.default

