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
import react.ReactElement

@JsModule("@material-ui/core/Stepper/Stepper")
external val StepperImport: dynamic

external interface StepperProps : PaperProps {
    var activeStep: Number? get() = definedExternally; set(value) = definedExternally
    var alternativeLabel: Boolean? get() = definedExternally; set(value) = definedExternally
    var children: ReactElement
    var connector: dynamic /* React.ReactElement<Any> | React.ReactNode */ get() = definedExternally; set(value) = definedExternally
    var nonLinear: Boolean? get() = definedExternally; set(value) = definedExternally
    var orientation: dynamic /* String /* "horizontal" */ | String /* "vertical" */ */ get() = definedExternally; set(value) = definedExternally
}

var Stepper: RClass<StepperProps> = StepperImport.default
