package showdown.web.wrapper.material.icons

import org.w3c.dom.events.Event
import react.RClass
import react.RProps

@JsModule("@material-ui/core/TextareaAutosize")
external val TextareaAutosizeImport: dynamic

external interface TextareaAutosizeProps :RProps {
  var  onChangeFunction : (Event) -> Unit
}

var TextareaAutosize: RClass<TextareaAutosizeProps> = TextareaAutosizeImport.default
