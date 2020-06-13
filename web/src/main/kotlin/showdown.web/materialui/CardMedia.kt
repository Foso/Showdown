@file:Suppress(
    "INTERFACE_WITH_SUPERCLASS",
    "OVERRIDING_FINAL_MEMBER",
    "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
    "CONFLICTING_OVERLOADS",
    "EXTERNAL_DELEGATION",
    "NESTED_CLASS_IN_EXTERNAL_INTERFACE"
)

package components.material


import react.RClass
import react.RComponent
import react.RProps
import react.RState

@JsModule("@material-ui/core/CardMedia/CardMedia")
external val CardMediaImport: dynamic

external interface CardMediaProps : RProps {
    var component: RComponent<RProps, RState>? get() = definedExternally; set(value) = definedExternally
    var image: String? get() = definedExternally; set(value) = definedExternally
    var src: String? get() = definedExternally; set(value) = definedExternally
}

var CardMeedia: RClass<CardMediaProps> = CardMediaImport.default
