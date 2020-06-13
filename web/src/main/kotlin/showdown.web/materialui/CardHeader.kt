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
import react.RComponent
import react.RProps
import react.RState


@JsModule("@material-ui/core/CardHeader/CardHeader")
external val CardHeaderImport: dynamic

external interface CardHeaderProps : RProps {
    var action: dynamic
    var avatar: dynamic
    var component: RComponent<RProps, RState>
    var subheader: String
    var title: String
}

var CardHeader: RClass<CardHeaderProps> = CardHeaderImport.default
