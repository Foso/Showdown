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

@JsModule("@material-ui/core/Paper/Paper")
external val PaperImport: dynamic

external interface PaperProps : RProps {
    var component: RComponent<RProps, RState>? get() = definedExternally; set(value) = definedExternally
    var elevation: Number? get() = definedExternally; set(value) = definedExternally
    var square: Boolean? get() = definedExternally; set(value) = definedExternally
}

var Paper: RClass<PaperProps> = PaperImport.default

