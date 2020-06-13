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

@JsModule("@material-ui/core/Backdrop/Backdrop")
external val BackdropImport: dynamic

external interface BackdropProps : RProps {
    var invisible: Boolean? get() = definedExternally; set(value) = definedExternally
    var onClick: EventHandlerFunction? get() = definedExternally; set(value) = definedExternally
    var open: Boolean
    var transitionDuration: dynamic /* "IndexedAccessType" kind unsupported yet here! (build/node_modules/@material-ui/core/Backdrop/Backdrop.d.ts:14:23 to 14:50) */ get() = definedExternally; set(value) = definedExternally
}

var Backdrop: RClass<BackdropProps> = BackdropImport.default

