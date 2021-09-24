package showdown.web.ui.common

import materialui.components.snackbar.enums.SnackbarOriginHorizontal
import materialui.components.snackbar.enums.SnackbarOriginVertical
import materialui.components.snackbar.horizontal
import materialui.components.snackbar.snackbar
import materialui.components.snackbar.vertical
import materialui.lab.components.alert.alert
import materialui.lab.components.alert.enums.AlertSeverity
import materialui.lab.components.alert.enums.AlertVariant
import react.RBuilder
import react.Props
import react.dom.attrs
import react.fc
import react.useState


fun RBuilder.mySnackbar(message: String, onClose: () -> Unit) {
    child(mySnackbar) {
        attrs.snackbarMessage = message
        attrs.onClose = onClose
    }
}

external interface MySnackbarProps : Props {
    var snackbarMessage: String
    var onClose: () -> Unit
}


val mySnackbar = fc<MySnackbarProps> { props ->
    val text = props.snackbarMessage
    val (visible, setVisibility) = useState(true)

    snackbar {
        attrs {
            anchorOrigin {
                this.horizontal = SnackbarOriginHorizontal.left
                this.vertical = SnackbarOriginVertical.bottom
            }
            this.open = visible
            autoHideDuration = 5000
            onClose = { _, _ ->
                setVisibility(false)
                props.onClose()
            }
        }

        alert {
            attrs {
                severity = AlertSeverity.info
                variant = AlertVariant.filled

            }

            +text
        }


    }
}