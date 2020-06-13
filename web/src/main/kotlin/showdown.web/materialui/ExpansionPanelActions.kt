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

@JsModule("@material-ui/core/ExpansionPanelActions/ExpansionPanelActions")
external val ExpansionPanelActionsImport: dynamic

external interface ExpansionPanelActionsProps : RProps

var ExpansionPanelActions: RClass<ExpansionPanelActionsProps> = ExpansionPanelActionsImport.default
