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

@JsModule("@material-ui/core/TableHead/TableHead")
external val TableHeadImport: dynamic

external interface TableHeadProps : RProps {
    var component: String? get() = definedExternally; set(value) = definedExternally
}

var TableHead: RClass<TableHeadProps> = TableHeadImport.default