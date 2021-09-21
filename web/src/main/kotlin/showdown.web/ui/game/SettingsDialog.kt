package showdown.web.ui.game

import showdown.web.Application
import kotlinx.browser.window
import kotlinx.html.js.onClickFunction
import materialui.components.button.button
import materialui.components.button.enums.ButtonColor
import materialui.components.button.enums.ButtonVariant
import materialui.components.checkbox.checkbox
import materialui.components.dialog.dialog
import materialui.components.dialogcontent.dialogContent
import materialui.components.textfield.textField
import org.w3c.dom.events.Event
import react.RBuilder
import react.dom.a
import react.dom.attrs
import react.dom.div
import react.dom.h1
import showdown.web.wrapper.material.QrCode


data class ShareDialogDataHolder(val autoReveal: Boolean, val anonymResults:Boolean)

/**
 * Shows a dialog with a QR Code of the link to the room
 */
fun RBuilder.settingsDialog(
    onCloseFunction: (Event) -> Unit,
    gameModeId: Int,
    onSave: (Int, String) -> Unit,
    shareDialogDataHolder: ShareDialogDataHolder
) {

    dialog {
        attrs {
            this.open = true
            this.fullWidth = true

        }
        dialogContent {

            h1 {
                +"Game Settings"
            }

            div {
                gameModeSettings(gameModeId, onSave)
            }

            div {
                checkbox {
                    attrs {
                        checked = shareDialogDataHolder.autoReveal
                        onClickFunction = {
                            Application.gameDataSource.setAutoReveal(!shareDialogDataHolder.autoReveal)
                        }
                    }
                }
                +"Auto Reveal votes when all voted"
            }

            div {
                checkbox {
                    attrs {
                        checked = shareDialogDataHolder.anonymResults
                        onClickFunction = {
                            Application.gameDataSource.setAnonymVote(!shareDialogDataHolder.anonymResults)
                        }
                    }
                }
                +"anonymize vote results"
            }

            h1 {
                +"About"
            }
            div {
                QrCode {
                    attrs {
                        value = window.location.toString()
                    }
                }
            }

            div {
                textField {
                    attrs {
                        value = window.location.toString()
                    }
                }
            }

            div {
                a {
                    attrs {
                        href = "https://github.com/Foso/Showdown/issues"
                    }
                    +"Issues/Feature Requests"

                }
            }

            div {
                a {
                    attrs {
                        href = "https://github.com/Foso/Showdown"
                    }
                    +"Showdown v1.2 on Github"

                }
            }

            button {
                attrs {
                    text("Close")
                    variant = ButtonVariant.contained
                    color = ButtonColor.primary
                    onClickFunction = onCloseFunction
                }
            }
        }


    }
}