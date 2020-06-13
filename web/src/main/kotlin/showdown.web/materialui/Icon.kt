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

@JsModule("@material-ui/core/Icon/Icon")
external val IconImport: dynamic

external interface IconProps : RProps {
    var color: dynamic /* PropTypes.Color | String /* "action" */ | String /* "disabled" */ | String /* "error" */ */ get() = definedExternally; set(value) = definedExternally
}

var Icon: RClass<IconProps> = IconImport.default

