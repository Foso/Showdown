package showdown.web.ui.game

import androidx.compose.runtime.Composable
import de.jensklingenberg.showdown.SHOWDOWN_ISSUES_URL
import de.jensklingenberg.showdown.SHOWDOWN_REPO_URL
import de.jensklingenberg.showdown.SHOWDOWN_VERSION
import dev.petuska.kmdc.button.MDCButton
import dev.petuska.kmdc.button.MDCButtonType
import dev.petuska.kmdc.checkbox.MDCCheckbox
import dev.petuska.kmdc.dialog.Content
import dev.petuska.kmdc.dialog.MDCDialog
import dev.petuska.kmdc.dialog.onClosed
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import showdown.web.common.JKTextField
import showdown.web.ui.Strings
import showdown.web.ui.Strings.Companion.AUTO_REVEAL
import showdown.web.ui.Strings.Companion.CHANGE_MODE
import showdown.web.ui.Strings.Companion.SETTINGS_GAMEMODE

@Composable
fun SettingsDialog(gameViewmodel: GameViewmodel, onClose: () -> Unit) {
    val CUSTOM_MODE = 4
    val gameModeId = rememberMutableStateOf(0)

    val customOptio0ns = rememberMutableStateOf("")

    MDCDialog(open = true, attrs = {
        this.onClosed {
            onClose()
        }
    }) {

        this.Content {

            H1 {
                Text(Strings.GAME_SETTINGS)
            }

            Div {

                GameModeMenu(gameModeOptions.map { it.first }) {
                    gameModeId.value = it
                }

                if (gameModeId.value == CUSTOM_MODE) {
                    JKTextField(
                        customOptio0ns.value,
                        label = SETTINGS_GAMEMODE,
                        onTextChange = {
                            customOptio0ns.value = it
                        },
                        onEnterPressed = {})
                }


            }

            MDCButton(text = CHANGE_MODE, type = MDCButtonType.Raised) {
                onClick {
                    gameViewmodel.changeConfig(gameModeId.value, customOptio0ns.value)
                    onClose()
                }

            }

            Div(attrs = {
                style {
                    textAlign("center")
                    justifyContent(JustifyContent.Center)
                    alignItems(AlignItems.Center)

                }
            }) {

                MDCCheckbox(gameViewmodel.autoReveal.value, attrs = {
                    onClick { gameViewmodel.setAutoReveal(!gameViewmodel.autoReveal.value) }
                })
                Text(AUTO_REVEAL)
            }

            Div(attrs = {
                style {
                    textAlign("center")
                    justifyContent(JustifyContent.Center)
                    alignItems(AlignItems.Center)

                }
            }) {

                MDCCheckbox(gameViewmodel.anonymResults.value, attrs = {
                    onClick { gameViewmodel.setAnonymVote(!gameViewmodel.anonymResults.value) }
                })
                Text(Strings.anonym)
            }
            H1 {
                Text("About")
            }


            Hr {

            }
            Div {
                A(href = SHOWDOWN_ISSUES_URL) {
                    Text("Issues/Feature Requests")
                }
            }

            Div {
                A(href = SHOWDOWN_REPO_URL) {
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