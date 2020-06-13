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

@JsModule("@material-ui/core/BottomNavigationAction/BottomNavigationAction")
external val BottomNavigationActionImport: dynamic

external interface BottomNavigationActionProps : RProps {
    var value: String? get() = definedExternally; set(value) = definedExternally
    var onChange: (Event, String) -> Unit
    var className: String? get() = definedExternally; set(value) = definedExternally
    var onClick: (Event) -> Unit
    var selected: Boolean? get() = definedExternally; set(value) = definedExternally

    var label: String? get() = definedExternally; set(value) = definedExternally
    var icon: dynamic get() = definedExternally; set(value) = definedExternally


}

var BottomNavigationAction: RClass<BottomNavigationActionProps> = BottomNavigationActionImport.default
