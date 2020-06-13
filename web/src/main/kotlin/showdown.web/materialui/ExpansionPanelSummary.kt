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

@JsModule("@material-ui/core/ExpansionPanelSummary/ExpansionPanelSummary")
external val ExpansionPanelSummaryImport: dynamic

external interface ExpansionPanelSummaryProps : ButtonProps {
    var expanded: Boolean? get() = definedExternally; set(value) = definedExternally
    var expandIcon: Any? get() = definedExternally; set(value) = definedExternally
    var onChange: EventHandlerFunction? get() = definedExternally; set(value) = definedExternally
}

var ExpansionPanelSummary: RClass<ExpansionPanelSummaryProps> = ExpansionPanelSummaryImport.default
