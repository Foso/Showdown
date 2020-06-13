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

@JsModule("@material-ui/core/Modal/Modal")
external val ModalImport: dynamic

external interface ModalProps : RProps {
    var BackdropComponent: RComponent<BackdropProps, RState>? get() = definedExternally; set(value) = definedExternally
    var BackdropProps: Any? get() = definedExternally; set(value) = definedExternally
    var disableAutoFocus: Boolean? get() = definedExternally; set(value) = definedExternally
    var disableBackdropClick: Boolean? get() = definedExternally; set(value) = definedExternally
    var disableEnforceFocus: Boolean? get() = definedExternally; set(value) = definedExternally
    var disableEscapeKeyDown: Boolean? get() = definedExternally; set(value) = definedExternally
    var disableRestoreFocus: Boolean? get() = definedExternally; set(value) = definedExternally
    var hideBackdrop: Boolean? get() = definedExternally; set(value) = definedExternally
    var keepMounted: Boolean? get() = definedExternally; set(value) = definedExternally
    var manager: ModalManager? get() = definedExternally; set(value) = definedExternally
    var onBackdropClick: EventHandlerFunction? get() = definedExternally; set(value) = definedExternally
    var onClose: EventHandlerFunction? get() = definedExternally; set(value) = definedExternally
    var onEscapeKeyDown: EventHandlerFunction? get() = definedExternally; set(value) = definedExternally
    var open: Boolean
}

var Modal: RClass<ModalProps> = ModalImport.default
