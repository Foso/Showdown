package showdown.web.ui.game.toolbar

import de.jensklingenberg.showdown.SHOWDOWN_ISSUES_URL
import de.jensklingenberg.showdown.SHOWDOWN_REPO_URL
import de.jensklingenberg.showdown.SHOWDOWN_VERSION
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
import showdown.web.game.GameDataSource
import showdown.web.ui.game.Strings.Companion.GAME_SETTINGS
import showdown.web.wrapper.material.QrCode


data class SettingsDialogDataHolder(val autoReveal: Boolean, val anonymResults: Boolean)

/**
 * Shows a dialog with a QR Code of the link to the room
 */
fun RBuilder.settingsDialog(
    onCloseFunction: (Event) -> Unit,
    gameModeId: Int,
    onSave: (Int, String) -> Unit,
    settingsDialogDataHolder: SettingsDialogDataHolder,
    gameDataSource: GameDataSource
) {

    dialog {
        attrs {
            this.open = true
            this.fullWidth = true

        }
        dialogContent {

            h1 {
                +GAME_SETTINGS
            }

            div {
                gameModeSettings(gameModeId, onSave)
            }

            div {
                checkbox {
                    attrs {
                        checked = settingsDialogDataHolder.autoReveal
                        onClickFunction = {
                            gameDataSource.setAutoReveal(!settingsDialogDataHolder.autoReveal)
                        }
                    }
                }
                +"Auto reveal votes when all voted"
            }

            div {
                checkbox {
                    attrs {
                        checked = settingsDialogDataHolder.anonymResults
                        onClickFunction = {
                            gameDataSource.setAnonymVote(!settingsDialogDataHolder.anonymResults)
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
                        href = SHOWDOWN_ISSUES_URL
                    }
                    +"Issues/Feature Requests"

                }
            }

            div {
                a {
                    attrs {
                        href = SHOWDOWN_REPO_URL
                    }
                    +"Showdown v$SHOWDOWN_VERSION on Github"

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