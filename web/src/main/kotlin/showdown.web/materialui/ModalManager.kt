@file:Suppress(
    "INTERFACE_WITH_SUPERCLASS",
    "OVERRIDING_FINAL_MEMBER",
    "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
    "CONFLICTING_OVERLOADS",
    "EXTERNAL_DELEGATION",
    "NESTED_CLASS_IN_EXTERNAL_INTERFACE"
)

package components.materialui


@JsModule("@material-ui/core/Modal/ModalManager")
external val ModalManagerImport: dynamic

external interface `T$0` {
    var hideSiblingNodes: Boolean? get() = definedExternally; set(value) = definedExternally
    var handleContainerOverflow: Boolean? get() = definedExternally; set(value) = definedExternally
}

external open class ModalManager(opts: `T$0`? = definedExternally /* null */) {
    open fun add(modal: Any, container: Any): Number = definedExternally
    open fun remove(modal: Any): Unit = definedExternally
    open fun isTopModal(modal: Any): Boolean = definedExternally
}