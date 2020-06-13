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

@JsModule("@material-ui/core/Drawer/Drawer")
external val DrawerImport: dynamic

external interface DrawerProps : ModalProps {
    var anchor: dynamic /* String /* "left" */ | String /* "top" */ | String /* "right" */ | String /* "bottom" */ */ get() = definedExternally; set(value) = definedExternally
    var children: RComponent<RProps, RState>? get() = definedExternally; set(value) = definedExternally
    var elevation: Number? get() = definedExternally; set(value) = definedExternally
    var ModalProps: Any? get() = definedExternally; set(value) = definedExternally
    var PaperProps: Any? get() = definedExternally; set(value) = definedExternally
    var SlideProps: Any? get() = definedExternally; set(value) = definedExternally
    var theme: Any? get() = definedExternally; set(value) = definedExternally
    var transitionDuration: dynamic /* "IndexedAccessType" kind unsupported yet here! (build/node_modules/@material-ui/core/Drawer/Drawer.d.ts:23:23 to 23:50) */ get() = definedExternally; set(value) = definedExternally
    var variant: dynamic /* String /* "permanent" */ | String /* "persistent" */ | String /* "temporary" */ */ get() = definedExternally; set(value) = definedExternally
}

var Drawer: RClass<DrawerProps> = DrawerImport.default
