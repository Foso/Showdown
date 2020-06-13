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

@JsModule("@material-ui/core/Step/Step")
external val StepImport: dynamic

external interface StepProps : RProps {
    var active: Boolean? get() = definedExternally; set(value) = definedExternally
    var alternativeLabel: Boolean? get() = definedExternally; set(value) = definedExternally
    var children: ReactElement? get() = definedExternally; set(value) = definedExternally
    var completed: Boolean? get() = definedExternally; set(value) = definedExternally
    var connector: ReactElement? get() = definedExternally; set(value) = definedExternally
    var disabled: Boolean? get() = definedExternally; set(value) = definedExternally
    var index: Number? get() = definedExternally; set(value) = definedExternally
    var last: Boolean? get() = definedExternally; set(value) = definedExternally
    var orientation: String? get() = definedExternally; set(value) = definedExternally
}

var Step: RClass<StepProps> = StepImport.default

