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


@JsModule("@material-ui/core/TableCell/TableCell")
external val TableCellImport: dynamic

external interface TableCellProps : RProps {
    var component: String? get() = definedExternally; set(value) = definedExternally
    var numeric: Boolean? get() = definedExternally; set(value) = definedExternally
    var padding: dynamic /* String /* "default" */ | String /* "checkbox" */ | String /* "dense" */ | String /* "none" */ */ get() = definedExternally; set(value) = definedExternally
    var sortDirection: dynamic /* Boolean | String /* "asc" */ | String /* "desc" */ */ get() = definedExternally; set(value) = definedExternally
    var type: dynamic /* String /* "head" */ | String /* "body" */ | String /* "footer" */ */ get() = definedExternally; set(value) = definedExternally
}

var TableCell: RClass<TableCellProps> = TableCellImport.default

