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
import react.RClass
import react.ReactElement


@JsModule("@material-ui/core/Switch/Switch")
external val SwitchImport: dynamic

external interface SwitchProps : InputProps {
    var checkedIcon: dynamic get() = definedExternally; set(value) = definedExternally
    var color: dynamic /* String /* "primary" */ | String /* "secondary" */ | String /* "default" */ */ get() = definedExternally; set(value) = definedExternally
    var icon: ReactElement? get() = definedExternally; set(value) = definedExternally
}

var Switch: RClass<SwitchProps> = SwitchImport.default

