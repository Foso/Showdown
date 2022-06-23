package showdown.web.wrapper.material

import org.w3c.dom.events.Event
import react.ComponentClass

import react.Props

@JsModule("@material-ui/icons/Share")
external val SkipNextIconImport: dynamic

external interface ShareIconProps : Props {
    var onClick: (Event) -> Unit
}

var ShareIcon: ComponentClass<ShareIconProps> = SkipNextIconImport.default
