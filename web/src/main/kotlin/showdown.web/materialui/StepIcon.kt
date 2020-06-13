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

@JsModule("@material-ui/core/StepIcon/StepIcon")
external val StepIconImport: dynamic

external interface StepIconProps : RProps {
    var active: Boolean? get() = definedExternally; set(value) = definedExternally
    var completed: Boolean? get() = definedExternally; set(value) = definedExternally
    var error: Boolean? get() = definedExternally; set(value) = definedExternally
    var icon: ReactElement
}

var StepIcon: RClass<StepIconProps> = StepIconImport.default

