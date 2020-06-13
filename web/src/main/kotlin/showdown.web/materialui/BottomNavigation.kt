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

@JsModule("@material-ui/core/BottomNavigation/BottomNavigation")
external val BottomNavigationImport: dynamic

external interface BottomNavigationProps : RProps {
    var value: String? get() = definedExternally; set(value) = definedExternally
    var onChange: (Event, String) -> Unit
    var className: String? get() = definedExternally; set(value) = definedExternally
    var showLabel: Boolean? get() = definedExternally; set(value) = definedExternally


}

var BottomNavigation: RClass<BottomNavigationProps> = BottomNavigationImport.default

