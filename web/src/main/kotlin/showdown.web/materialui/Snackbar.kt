@file:Suppress(
    "INTERFACE_WITH_SUPERCLASS",
    "OVERRIDING_FINAL_MEMBER",
    "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
    "CONFLICTING_OVERLOADS",
    "EXTERNAL_DELEGATION",
    "NESTED_CLASS_IN_EXTERNAL_INTERFACE"
)

package components.materialui

import org.w3c.dom.events.Event
import react.RClass
import react.RProps

@JsModule("@material-ui/core/Snackbar/Snackbar")
external val SnackbarImport: dynamic

interface SnackbarOrigin {
    var horizontal: String?
    var vertical: String?
}


external interface SnackbarProps : RProps {
    var anchorOrigin: SnackbarOrigin? get() = definedExternally; set(value) = definedExternally
    var autoHideDuration: Int? get() = definedExternally; set(value) = definedExternally
    var open: Boolean? get() = definedExternally; set(value) = definedExternally
    var message: String? get() = definedExternally; set(value) = definedExternally
    var onClose: (Event) -> Unit
    var variant: String? get() = definedExternally; set(value) = definedExternally

}

var Snackbar: RClass<SnackbarProps> = SnackbarImport.default

