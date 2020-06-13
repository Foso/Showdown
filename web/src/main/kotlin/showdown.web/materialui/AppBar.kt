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

@JsModule("@material-ui/core/AppBar/AppBar")
external val AppBarImport: dynamic

external interface AppBarProps : RProps {
    var color: String? get() = definedExternally; set(value) = definedExternally
    var position: dynamic /* String /* "fixed" */ | String /* "absolute" */ | String /* "sticky" */ | String /* "static" */ */ get() = definedExternally; set(value) = definedExternally
}

var AppBar: RClass<AppBarProps> = AppBarImport.default

