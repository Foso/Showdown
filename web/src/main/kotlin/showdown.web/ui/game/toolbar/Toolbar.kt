package showdown.web.ui.game.toolbar

import Application
import kotlinx.html.DIV
import kotlinx.html.id
import kotlinx.html.js.onClickFunction
import materialui.components.appbar.appBar
import materialui.components.appbar.enums.AppBarColor
import materialui.components.appbar.enums.AppBarPosition
import materialui.components.button.button
import materialui.components.button.enums.ButtonColor
import materialui.components.button.enums.ButtonVariant
import materialui.components.checkbox.checkbox
import materialui.components.menu.menu
import materialui.components.menuitem.menuItem
import org.w3c.dom.events.EventTarget
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.ReactElement
import react.dom.RDOMBuilder
import react.dom.div
import react.dom.label
import react.setState
import showdown.web.game.GameDataSource
import showdown.web.ui.game.shareDialog
import showdown.web.wrapper.material.ShareIcon
import showdown.web.wrapper.material.VisibilityIcon
import showdown.web.wrapper.material.icons.AccountCircleIcon
import showdown.web.wrapper.material.icons.AddCircleIcon
import kotlin.math.floor


interface ToolbarState : RState {

    var onGameModeClicked: () -> Unit

    var diffSecs: Double
    var openMenu: Boolean
    var anchor: EventTarget?
    var startTimer: Boolean
    var showShareDialog: Boolean
    var autoReveal: Boolean
    var gameModeId: Int
}


interface ToolbarProps : RProps {

    var startTimer: Boolean
    var diffSecs: Double
    var onGameModeClicked: () -> Unit
    var autoReveal: Boolean
    var gameModeId: Int

}

class Toolbar(props: ToolbarProps) : RComponent<ToolbarProps, ToolbarState>(props), ToolContract.View {

    private val presenter: ToolContract.Presenter by kotlin.lazy {
        ToolbarPresenter(this)
    }

    private val gameDataSource: GameDataSource = Application.gameDataSource


    override fun ToolbarState.init(props: ToolbarProps) {
        this.startTimer = props.startTimer
        this.showShareDialog = false
        this.diffSecs = props.diffSecs
        this.onGameModeClicked = props.onGameModeClicked
        this.gameModeId = props.gameModeId
        this.autoReveal = props.autoReveal
    }

    override fun componentWillReceiveProps(nextProps: ToolbarProps) {
        setState {
            this.diffSecs = props.diffSecs
            this.startTimer = props.startTimer
            this.autoReveal = props.autoReveal
        }
    }

    override fun RBuilder.render() {
        if (state.showShareDialog) {
            shareDialog(onCloseFunction = {
                setState {
                    this.showShareDialog = false
                }
            }, state.gameModeId, onSave = { gameModeId, gameOptions ->
                presenter.changeConfig(gameModeId, gameOptions)
            }, state.autoReveal)
        }


        appBar {
            attrs {
                position = AppBarPosition.static
                color = AppBarColor.primary
            }
            div {
                newGameButton()
                showVotesButton()
                shareButton(state.autoReveal)
                +"Estimation time: ${getTimerText()} seconds."
            }
        }


    }

    private fun RDOMBuilder<DIV>.newGameButton() {
        button {
            attrs {
                variant = ButtonVariant.contained
                color = ButtonColor.primary
                text("New Game")
                onClickFunction = {
                    presenter.reset()
                }
                startIcon {
                    AddCircleIcon {}
                }
            }
        }
    }

    private fun RDOMBuilder<DIV>.showVotesButton() {
        button {
            attrs {
                variant = ButtonVariant.contained
                color = ButtonColor.primary
                text("Show Votes")
                onClickFunction = {
                    presenter.showVotes()
                }
                startIcon {
                    VisibilityIcon {}
                }
            }
        }
    }

    private fun RDOMBuilder<DIV>.shareButton(autoReveal: Boolean) {
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
                    AccountCircleIcon {}
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
                        openMenu = false
                    }
                }
                anchorEl(state.anchor)
            }
            menuItem {
                attrs {
                    onClickFunction = {
                        setState {
                            openMenu = false
                        }

                    }
                }
                label {
                    +"Playername: ${gameDataSource.getPlayerName()}"
                }
            }
            menuItem {
                attrs {
                    onClickFunction = {
                        setState {
                            openMenu = false
                        }

                    }
                }
                checkbox {
                    attrs {
                        checked = state.autoReveal
                        onClickFunction = {
                            gameDataSource.setAutoReveal(!state.autoReveal)
                        }
                    }
                }
                label {
                    +"AutoReveal Votes"
                }
            }


        }
    }
}

fun RBuilder.myToolbar(
    startTimer: Boolean,
    diffSecs: Double,
    onGameModeClicked: () -> Unit,
    autoReveal: Boolean,
    gameModeId: Int,

    ): ReactElement {
    return child(Toolbar::class) {
        attrs {
            this.startTimer = startTimer
            this.diffSecs = diffSecs
            this.onGameModeClicked = onGameModeClicked
            this.autoReveal = autoReveal
            this.gameModeId = gameModeId

        }
    }
}