package showdown.web.ui.home

import kotlinx.html.id
import kotlinx.html.js.onClickFunction
import materialui.components.appbar.appBar
import materialui.components.appbar.enums.AppBarColor
import materialui.components.appbar.enums.AppBarPosition
import materialui.components.button.button
import materialui.components.button.enums.ButtonColor
import materialui.components.button.enums.ButtonVariant
import materialui.components.menu.menu
import materialui.components.menuitem.menuItem
import org.w3c.dom.events.EventTarget
import react.*
import react.dom.div
import react.dom.label
import showdown.web.wrapper.material.AddCircleIcon
import showdown.web.wrapper.material.SettingsIcon
import showdown.web.wrapper.material.ShareIcon
import showdown.web.wrapper.material.VisibilityIcon
import kotlin.browser.window
import kotlin.math.floor


interface ToolbarState : RState {
    var onNewGameClicked: () -> Unit
    var onShowVotesClicked: () -> Unit
    var diffSecs: Double
    var showSettings: Boolean
    var openMenu: Boolean
    var showChangePassword: Boolean
    var anchor: EventTarget?
    var startTimer: Boolean
    var showShareDialog: Boolean

}


interface ToolbarProps : RProps {
    var onNewGameClicked: () -> Unit
    var onShowVotesClicked: () -> Unit
    var startTimer: Boolean
    var diffSecs: Double

}

class Toolbar(props: ToolbarProps) : RComponent<ToolbarProps, ToolbarState>(props) {

    override fun ToolbarState.init(props: ToolbarProps) {
        this.onNewGameClicked = props.onNewGameClicked
        this.onShowVotesClicked = props.onShowVotesClicked
        this.startTimer = props.startTimer
        this.showShareDialog=false
        this.diffSecs=props.diffSecs
    }

    override fun componentWillReceiveProps(nextProps: ToolbarProps) {
        setState {

            this.diffSecs=props.diffSecs
            this.startTimer=props.startTimer

        }
    }

    override fun RBuilder.render() {
        shareDialog(state.showShareDialog) {
            setState {
                this.showShareDialog = false
            }
        }

        appBar {
            attrs {
                position = AppBarPosition.static
                color = AppBarColor.primary
            }
            div {
                button {
                    attrs {
                        variant = ButtonVariant.contained
                        color = ButtonColor.primary
                        text("New Game")
                        onClickFunction = {
                            state.onNewGameClicked()
                        }
                        startIcon {
                            AddCircleIcon {}
                        }
                    }
                }

                button {
                    attrs {
                        variant = ButtonVariant.contained
                        color = ButtonColor.primary
                        text("Show Votes")
                        onClickFunction = {
                            state.onShowVotesClicked()
                        }
                        startIcon {
                            VisibilityIcon {}
                        }
                    }
                }
                settingsPopupMenu(state)

                button {
                    attrs {
                        variant = ButtonVariant.contained
                        color = ButtonColor.primary
                        text("Share")
                        onClickFunction = {
                            setState {
                                this.showShareDialog = true
                            }
                        }
                        startIcon {
                            ShareIcon {}
                        }
                    }
                }

                +"Estimation time: ${getTimerText()} seconds."
            }
        }


    }

    override fun componentDidMount() {


    }

    private fun getTimerText(): String {
        return if (state.startTimer) {
            floor(state.diffSecs).toString()
        } else {
            "0"
        }
    }

    private fun RBuilder.settingsPopupMenu(
        state: ToolbarState
    ) {
        button {
            +"Settings"
            attrs {
                variant = ButtonVariant.contained
                color = ButtonColor.primary
                asDynamic()["aria-controls"] = "simple-menu"
                asDynamic()["aria-haspopup"] = true
                startIcon {
                    SettingsIcon {}
                }
                onClickFunction = { event ->
                    val currentTarget = event.currentTarget

                    setState {
                        anchor = currentTarget
                        openMenu = !state.openMenu
                    }

                }
            }
        }

        menu {
            attrs {
                id = "simple-menu"
                open = state.openMenu
                onClose = { event, s ->
                    setState {
                        // anchor = currentTarget
                        openMenu = false

                    }
                }
                anchorEl(state.anchor)
            }
            menuItem {
                attrs {
                    onClickFunction = {
                        setState {
                            this.showSettings = !this.showSettings
                            openMenu = false
                        }
                    }
                }
                label {
                    +" Change GameConfig"
                }
            }

            menuItem {
                attrs {
                    onClickFunction = {
                        setState {
                            showChangePassword = true
                            openMenu = false
                        }
                    }
                }
                label {
                    +"Room password is: "
                }
            }
            menuItem {
                attrs {
                    onClickFunction = {
                        window.location.href = "https://github.com/Foso/Showdown";
                    }
                }
                label {
                    +"Showdown v1.0 Github"
                }
            }

        }


    }


}

fun RBuilder.myToolbar(
    startTimer: Boolean,
    onNewGameClicked: () -> Unit,
    onShowVotesClicked: () -> Unit,
    diffSecs: Double
): ReactElement {
    return child(Toolbar::class) {
        attrs {
            this.onNewGameClicked = onNewGameClicked
            this.onShowVotesClicked = onShowVotesClicked
            this.startTimer = startTimer
            this.diffSecs=diffSecs
        }
    }
}