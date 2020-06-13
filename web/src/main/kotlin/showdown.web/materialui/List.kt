@file:Suppress(
    "INTERFACE_WITH_SUPERCLASS",
    "OVERRIDING_FINAL_MEMBER",
    "RETURN_TYPE_MISMATCH_ON_OVERRIDE",
    "CONFLICTING_OVERLOADS",
    "EXTERNAL_DELEGATION",
    "NESTED_CLASS_IN_EXTERNAL_INTERFACE"
)

package de.jensklingenberg.showdown.web.components.materialui

import react.RClass
import react.RElementBuilder
import react.RProps


@JsModule("@material-ui/core/List/List")
external val ListImport: dynamic

external interface ListProps : RProps {
    var component: dynamic get() = definedExternally; set(value) = definedExternally
    var dense: Boolean? get() = definedExternally; set(value) = definedExternally
    var disablePadding: Boolean? get() = definedExternally; set(value) = definedExternally
    var subheader: RElementBuilder<RProps>? get() = definedExternally; set(value) = definedExternally
}

var List: RClass<ListProps> = ListImport.default

