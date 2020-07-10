package showdown.web.ui.home

import kotlinx.html.js.onClickFunction
import materialui.components.button.button
import materialui.components.button.enums.ButtonColor
import materialui.components.button.enums.ButtonSize
import materialui.components.snackbar.snackbar
import materialui.components.snackbarcontent.snackbarContent
import org.w3c.dom.events.Event
import react.RBuilder

fun connectionErrorSnackbar(rBuilder: RBuilder,showErrorSnackbar:Boolean, onActionClick : (Event)->Unit) {
   with(rBuilder){
       snackbar {
           attrs {
               this.open = showErrorSnackbar
           }
           snackbarContent {
               attrs {
                   action {
                       button {
                           attrs {
                               color = ButtonColor.secondary
                               size = ButtonSize.small
                               onClickFunction = {
                                   onActionClick(it)
                               }
                           }
                           +"Retry"
                       }
                   }
                   message {
                       +"You are not connected to the server"
                   }
               }
           }

       }
   }

}