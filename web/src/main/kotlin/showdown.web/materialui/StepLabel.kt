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

@JsModule("@material-ui/core/StepLabel/StepLabel")
external val StepLabelImport: dynamic

external interface StepLabelProps : RProps {
    var active: Boolean? get() = definedExternally; set(value) = definedExternally
    var alternativeLabel: Boolean? get() = definedExternally; set(value) = definedExternally
    var children: ReactElement
    var completed: Boolean? get() = definedExternally; set(value) = definedExternally
    var disabled: Boolean? get() = definedExternally; set(value) = definedExternally
    var error: Boolean? get() = definedExternally; set(value) = definedExternally
    var icon: dynamic get() = definedExternally; set(value) = definedExternally
    var last: Boolean? get() = definedExternally; set(value) = definedExternally
    var optional: dynamic get() = definedExternally; set(value) = definedExternally
    var orientation: dynamic get() = definedExternally; set(value) = definedExternally
}

var StepLabel: RClass<StepLabelProps> = StepLabelImport.default

