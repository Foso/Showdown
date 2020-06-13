package challenge.usecase

import challenge.wrapper.material.Snackbar
import challenge.wrapper.material.SnackbarOrigin
import react.RBuilder

class MessageUseCase {


    fun showErrorSnackbar(rBuilder: RBuilder, snackbarMessage: String, showSnackbar: Boolean, duration: Int = 6000) {
        rBuilder.run {

            Snackbar {
                attrs {
                    this.anchorOrigin = object : SnackbarOrigin {
                        override var horizontal: String? = "center"
                        override var vertical: String? = "bottom"
                    }
                    open = showSnackbar
                    message = snackbarMessage
                    autoHideDuration = duration
                    onClose = { false }
                    variant = "error"
                }
            }
        }
    }


}