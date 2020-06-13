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

@JsModule("@material-ui/core/Divider/Divider")
external val DividerImport: dynamic

external interface DividerProps : RProps {
    var absolute: Boolean? get() = definedExternally; set(value) = definedExternally
    var component: ReactElement? get() = definedExternally; set(value) = definedExternally
    var inset: Boolean? get() = definedExternally; set(value) = definedExternally
    var light: Boolean? get() = definedExternally; set(value) = definedExternally
}

var Divider: RClass<DividerProps> = DividerImport.default
