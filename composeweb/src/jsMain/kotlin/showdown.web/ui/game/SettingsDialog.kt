package showdown.web.ui.game

import androidx.compose.runtime.*
import de.jensklingenberg.mealapp.mainpage.JKTextField
import de.jensklingenberg.showdown.SHOWDOWN_ISSUES_URL
import de.jensklingenberg.showdown.SHOWDOWN_REPO_URL
import de.jensklingenberg.showdown.SHOWDOWN_VERSION
import de.jensklingenberg.showdown.model.api.clientrequest.JoinGame
import dev.petuska.kmdc.button.MDCButton
import dev.petuska.kmdc.button.MDCButtonType
import dev.petuska.kmdc.checkbox.MDCCheckbox
import dev.petuska.kmdc.dialog.Content
import dev.petuska.kmdc.dialog.MDCDialog
import dev.petuska.kmdc.dialog.onClosed
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import showdown.web.ui.Strings

@Composable
fun SettingsDialog(onClose: () -> Unit) {
    var isSpec by remember { mutableStateOf(false) }
    var playerName by remember { mutableStateOf("User" + (0..1000).random().toString()) }

    MDCDialog(open = true, attrs = {
        style {

        }
        this.onClosed {
            onClose()
        }
    }) {

        this.Content {

            H1 {
                Text(Strings.GAME_SETTINGS)
            }

            Div {
                MDCButton(text = "Change Mode", type = MDCButtonType.Raised) {
                    onClick { onClose() }

                }
            }

            Div {
                MDCCheckbox(isSpec, label = "I'm a spectator", attrs = {
                    onClick { isSpec = !isSpec }
                })
            }
            Div {
                MDCButton(text = "Close", type = MDCButtonType.Raised) {
                    onClick { onClose() }

                }
            }

            Div(attrs = {
                style {
                    textAlign("center")
                    justifyContent(JustifyContent.Center)
                    alignItems(AlignItems.Center)

                }
            }) {
                val autoReveal = true
                MDCCheckbox(autoReveal, attrs = {
                    onClick {  }
                })
                Text("Auto Reveal votes when all voted")
            }

            Div(attrs = {
                style {
                    textAlign("center")
                    justifyContent(JustifyContent.Center)
                    alignItems(AlignItems.Center)

                }
            }) {
                val autoReveal = true
                MDCCheckbox(autoReveal, attrs = {
                    onClick {  }
                })
                Text("anonymize vote results")
            }
            H1 {
                Text("About")
            }


            Hr {

            }
            Div {
                A(href = SHOWDOWN_ISSUES_URL){
                    Text("Issues/Feature Requests")
                }
            }

            Div {
                A(href = SHOWDOWN_REPO_URL){
                    Text("Showdown v$SHOWDOWN_VERSION on Github")
                }
            }
            Div {
                MDCButton(text = "Close", type = MDCButtonType.Raised) {
                    onClick { onClose() }

                }
            }
        }


    }
}