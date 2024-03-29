package showdown.web.ui.game.voting.settings

import androidx.compose.runtime.*
import dev.petuska.kmdc.dialog.Content
import dev.petuska.kmdc.dialog.MDCDialog
import dev.petuska.kmdc.dialog.onClosed
import dev.petuska.kmdc.switch.MDCSwitch
import dev.petuska.kmdcx.icons.MDCIcon
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import showdown.web.common.H2
import showdown.web.common.IconButton
import showdown.web.common.JKRaisedButton
import showdown.web.common.JKTextField
import showdown.web.ui.Strings
import showdown.web.ui.Strings.Companion.SETTINGS_ABOUT
import showdown.web.ui.Strings.Companion.CHANGE_MODE
import showdown.web.ui.Strings.Companion.ROOM_PASSWORD_SETTINGS
import showdown.web.ui.Strings.Companion.SETTINGS_GAMEMODE
import showdown.web.ui.Strings.Companion.SHOWDOWN_ISSUES_URL
import showdown.web.ui.Strings.Companion.SHOWDOWN_REPO_URL
import showdown.web.ui.Strings.Companion.SHOWDOWN_VERSION

val gameModeOptions: List<Pair<String, Int>> = listOf(
        "Fibonacci" to 0,
        "T-Shirt" to 1,
        "Modified Fibonacci" to 2,
        "Power of 2" to 3,
        "Custom" to 4
    )


@Composable
fun SettingsDialog(settingsViewModel: SettingsViewModel, onClose: () -> Unit) {
    val CUSTOM_MODE = 4
    val gameModeId = remember { mutableStateOf(0) }
    val customOptions = remember { mutableStateOf("") }
    var roomPassword by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        settingsViewModel.onCreate()
    }

    MDCDialog(open = true, attrs = {
        this.onClosed {
            onClose()
        }
    }) {
        IconButton(MDCIcon.Close, onClick = { onClose() }) {}
        Content {

            H1 {
                Text(Strings.GAME_SETTINGS)
            }

            Div {

                DropdownMenu(Strings.GAME_MODE, gameModeOptions.map { it.first }) {
                    gameModeId.value = it
                }

                if (gameModeId.value == CUSTOM_MODE) {
                    JKTextField(
                        customOptions.value,
                        label = SETTINGS_GAMEMODE,
                        onTextChange = {
                            customOptions.value = it
                        },
                        onEnterPressed = {})
                }


            }

            JKRaisedButton(CHANGE_MODE) {
                settingsViewModel.changeConfig(gameModeId.value, customOptions.value)
                onClose()
            }


            Div(attrs = {
                style {
                    textAlign("left")
                    justifyContent(JustifyContent.Start)
                    alignItems(AlignItems.Start)

                }
            }) {

                MDCSwitch(settingsViewModel.autoRevealEnabled.value, label = Strings.AUTO_REVEAL, attrs = {
                    onClick { settingsViewModel.setAutoReveal(!settingsViewModel.autoRevealEnabled.value) }
                }) {}

            }

            Div(attrs = {
                style {
                    marginTop(10.px)
                    textAlign("left")
                    justifyContent(JustifyContent.Start)
                    alignItems(AlignItems.Start)

                }
            }) {
                MDCSwitch(settingsViewModel.isAnonymResults.value, label = Strings.anonym, attrs = {
                    onClick { settingsViewModel.setAnonymVote(!settingsViewModel.isAnonymResults.value) }
                }) {}
            }

            Hr {}

            H2(ROOM_PASSWORD_SETTINGS)

            Div {
                JKTextField(value = roomPassword, label = Strings.SET_ROOM_PW, onTextChange = {
                    roomPassword = it
                }, onEnterPressed = {
                    settingsViewModel.changeRoomPassword(roomPassword)
                })
            }
            Div {
                JKRaisedButton(Strings.Save_PASSWORD) {
                    settingsViewModel.changeRoomPassword(roomPassword)
                }
            }
            Div {
                JKRaisedButton(Strings.REMOVE_PASSWORD) {
                    settingsViewModel.changeRoomPassword("")
                }
            }



            Hr {}

            H2(SETTINGS_ABOUT)

            Div {
                A(href = SHOWDOWN_ISSUES_URL) {
                    Text("Issues/Feature Requests")
                }
            }

            Div {
                A(href = SHOWDOWN_REPO_URL) {
                    Text("Showdown v$SHOWDOWN_VERSION on GitHub")
                }
            }

        }


    }
}

