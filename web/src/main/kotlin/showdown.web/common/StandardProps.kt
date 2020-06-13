package de.jensklingenberg.showdown.web.components

import org.w3c.dom.events.Event

external interface StandardProps {
    var id: String? get() = definedExternally; set(value) = definedExternally
    var onClick: (Event) -> Unit

}