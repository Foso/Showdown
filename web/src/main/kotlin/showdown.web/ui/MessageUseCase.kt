package challenge.ui

import challenge.wrapper.material.Snackbar
import challenge.wrapper.material.SnackbarOrigin
import react.RBuilder

class MessageUseCase {


    fun showErrorSnackbar(
        rBuilder: RBuilder,
        snackbarMessage: String,
        showSnackbar: Boolean,
        onCloseFunction: () -> Unit = {}
    ) {
        rBuilder.run {

            Snackbar {
                attrs {
                    this.anchorOrigin = object : SnackbarOrigin {
                        override var horizontal: String? = "center"
                        override var vertical: String? = "bottom"
                    }
                    open = showSnackbar
                    message = snackbarMessage
                    autoHideDuration = 5000
                    onClose = { onCloseFunction() }
                    variant = "error"
                }
            }
        }
    }


}