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
import react.RComponent
import react.RProps
import react.RState


@JsModule("@material-ui/core/TableBody/TableBody")
external val TableBodyImport: dynamic

external interface TableBodyProps : RProps {
    var component: RComponent<RProps, RState>? get() = definedExternally; set(value) = definedExternally
}

var TableBody: RClass<TableBodyProps> = TableBodyImport.default