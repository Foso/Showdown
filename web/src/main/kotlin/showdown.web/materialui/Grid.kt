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

@JsModule("@material-ui/core/Grid/Grid")
external val GridImport: dynamic

external interface GridProps : RProps {
    var alignContent: dynamic /* String /* "stretch" */ | String /* "center" */ | String /* "flex-start" */ | String /* "flex-end" */ | String /* "space-between" */ | String /* "space-around" */ */ get() = definedExternally; set(value) = definedExternally
    var alignItems: dynamic /* String /* "stretch" */ | String /* "center" */ | String /* "flex-start" */ | String /* "flex-end" */ | String /* "baseline" */ */ get() = definedExternally; set(value) = definedExternally
    var component: dynamic /* String | React.ComponentType<Omit<GridProps, dynamic /* String /* "container" */ | String /* "item" */ | String /* "hidden" */ | String /* "classes" */ | String /* "className" */ | String /* "component" */ | String /* "alignContent" */ | String /* "alignItems" */ | String /* "direction" */ | String /* "spacing" */ | String /* "justify" */ | String /* "wrap" */ | String /* "xs" */ | String /* "sm" */ | String /* "md" */ | String /* "lg" */ | String /* "xl" */ */>> */ get() = definedExternally; set(value) = definedExternally
    var container: Boolean? get() = definedExternally; set(value) = definedExternally
    var direction: dynamic /* String /* "row" */ | String /* "row-reverse" */ | String /* "column" */ | String /* "column-reverse" */ */ get() = definedExternally; set(value) = definedExternally
    var item: Boolean? get() = definedExternally; set(value) = definedExternally
    var justify: dynamic /* String /* "center" */ | String /* "flex-start" */ | String /* "flex-end" */ | String /* "space-between" */ | String /* "space-around" */ */ get() = definedExternally; set(value) = definedExternally
    var spacing: dynamic /* Number /* 0 */ | Number /* 8 */ | Number /* 16 */ | Number /* 24 */ | Number /* 32 */ | Number /* 40 */ */ get() = definedExternally; set(value) = definedExternally
    var wrap: dynamic /* String /* "wrap" */ | String /* "nowrap" */ | String /* "wrap-reverse" */ */ get() = definedExternally; set(value) = definedExternally
    var zeroMinWidth: Boolean? get() = definedExternally; set(value) = definedExternally
    var xs: Int
    var sm: Int
    var md: Int
    var lg: Int
    var xl: Int
}

var Grid: RClass<GridProps> = GridImport.default

