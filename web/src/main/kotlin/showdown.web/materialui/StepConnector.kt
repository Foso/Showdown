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

@JsModule("@material-ui/core/StepConnector/StepConnector")
external val StepConnectorImport: dynamic

external interface StepConnectorProps : RProps {
    var alternativeLabel: Boolean? get() = definedExternally; set(value) = definedExternally
    var orientation: dynamic get() = definedExternally; set(value) = definedExternally
}

var StepConnector: RClass<StepperProps> = StepConnectorImport.default
