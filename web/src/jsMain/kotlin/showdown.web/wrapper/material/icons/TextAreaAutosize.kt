package showdown.web.wrapper.material.icons

import org.w3c.dom.events.Event
import react.ComponentClass

import react.Props

@JsModule("@material-ui/core/TextareaAutosize")
external val TextareaAutosizeImport: dynamic

external interface TextareaAutosizeProps : Props {
    var onChangeFunction: (Event) -> Unit
}

var TextareaAutosize: ComponentClass<TextareaAutosizeProps> = TextareaAutosizeImport.default
