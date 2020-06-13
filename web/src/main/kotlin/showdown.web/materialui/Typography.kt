@file:Suppress(
    "INTERFACE_WITH_SUPERCLASS",
    "OVERRIDING_FINAL_MEMBER",
    "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
    "CONFLICTING_OVERLOADS",
    "EXTERNAL_DELEGATION",
    "NESTED_CLASS_IN_EXTERNAL_INTERFACE"
)

package components.materialui

import de.jensklingenberg.showdown.web.ui.common.LayoutProps
import react.RClass
import react.RProps
import react.ReactElement

@JsModule("@material-ui/core/Typography/Typography")
external val TypographyImport: dynamic

external interface TypographyProps : RProps, LayoutProps {
    var align: dynamic get() = definedExternally; set(value) = definedExternally
    var color: dynamic /* PropTypes.Color | String /* "textSecondary" */ | String /* "error" */ */ get() = definedExternally; set(value) = definedExternally
    var component: ReactElement? get() = definedExternally; set(value) = definedExternally
    var gutterBottom: Boolean? get() = definedExternally; set(value) = definedExternally
    var headlineMapping: Any? get() = definedExternally; set(value) = definedExternally
    var noWrap: Boolean? get() = definedExternally; set(value) = definedExternally
    var paragraph: Boolean? get() = definedExternally; set(value) = definedExternally
    var variant: dynamic /* Style | String /* "caption" */ | String /* "button" */ */ get() = definedExternally; set(value) = definedExternally
}

var Typography: RClass<TypographyProps> = TypographyImport.default

