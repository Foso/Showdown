package showdown.web.wrapper.material

import org.w3c.dom.events.Event
import react.RClass
import react.RProps

@JsModule("@material-ui/icons/Share")
external val SkipNextIconImport: dynamic

external interface ShareIconProps :RProps {
    var onClick: (Event) -> Unit
}

var ShareIcon: RClass<ShareIconProps> = SkipNextIconImport.default
