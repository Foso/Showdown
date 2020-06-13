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

@JsModule("@material-ui/core/StepContent/StepContent")
external val StepContentImport: dynamic

external interface StepContentProps : RProps {
    var active: Boolean? get() = definedExternally; set(value) = definedExternally
    var alternativeLabel: Boolean? get() = definedExternally; set(value) = definedExternally
    var children: ReactElement
    var completed: Boolean? get() = definedExternally; set(value) = definedExternally
    var last: Boolean? get() = definedExternally; set(value) = definedExternally
    var optional: Boolean? get() = definedExternally; set(value) = definedExternally
    var orientation: dynamic get() = definedExternally; set(value) = definedExternally
    var TransitionComponent: ReactElement? get() = definedExternally; set(value) = definedExternally
    var transitionDuration: dynamic /* dynamic /* "IndexedAccessType" kind unsupported yet here! (build/node_modules/@material-ui/core/StepContent/StepContent.d.ts:16:23 to 16:50) */ | String /* "auto" */ */ get() = definedExternally; set(value) = definedExternally
    var TransitionProps: ReactElement? get() = definedExternally; set(value) = definedExternally
}

var StepContent: RClass<StepContentProps> = StepContentImport.default

