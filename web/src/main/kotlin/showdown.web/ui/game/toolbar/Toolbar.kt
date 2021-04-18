package showdown.web.ui.game.toolbar

import kotlinx.html.DIV
import materialui.components.appbar.appBar
import materialui.components.appbar.enums.AppBarColor
import materialui.components.appbar.enums.AppBarPosition
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.ReactElement
import react.dom.RDOMBuilder
import react.dom.div
import react.setState
import showdown.web.ui.game.ShareDialogDataHolder
import showdown.web.ui.game.shareDialog
import showdown.web.wrapper.material.SettingsIcon
import showdown.web.wrapper.material.VisibilityIcon
import showdown.web.wrapper.material.icons.AddCircleIcon
import kotlin.math.floor


interface ToolbarState : RState {
    var diffSecs: Double
    var startTimer: Boolean
    var showShareDialog: Boolean
    var gameModeId: Int
    var shareDialogDataHolder: ShareDialogDataHolder
}


interface ToolbarProps : RProps {
    var startTimer: Boolean
    var diffSecs: Double
    var gameModeId: Int
    var shareDialogDataHolder: ShareDialogDataHolder

}

private class Toolbar(props: ToolbarProps) : RComponent<ToolbarProps, ToolbarState>(props), ToolContract.View {

    private val presenter: ToolContract.Presenter by lazy {
        ToolbarPresenter(this)
    }

    override fun ToolbarState.init(props: ToolbarProps) {
        this.startTimer = props.startTimer
        this.showShareDialog = false
        this.diffSecs = props.diffSecs
        this.gameModeId = props.gameModeId
        this.shareDialogDataHolder = props.shareDialogDataHolder
    }

    override fun componentWillReceiveProps(nextProps: ToolbarProps) {
        setState {
            this.diffSecs = props.diffSecs
            this.startTimer = props.startTimer
            this.gameModeId = props.gameModeId
            this.shareDialogDataHolder = props.shareDialogDataHolder
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
            }, state.shareDialogDataHolder)
        }

        appBar {
            attrs {
                position = AppBarPosition.static
                color = AppBarColor.primary
            }
            div {
                newGameButton()
                showVotesButton()
                settingsButton()
                +"Estimation time: ${getTimerText()} seconds."
            }
        }
    }

    private fun RDOMBuilder<DIV>.newGameButton() {
        toolbarButton("New Game", AddCircleIcon, onClick = {
            presenter.reset()
        })
    }

    private fun RDOMBuilder<DIV>.showVotesButton() {
        toolbarButton("Show Votes", VisibilityIcon, onClick = {
            presenter.showVotes()
        })
    }

    private fun RDOMBuilder<DIV>.settingsButton() {
        toolbarButton("Settings", SettingsIcon, onClick = {
            setState {
                this.showShareDialog = true
            }
        })
    }

    private fun getTimerText(): String {
        return if (state.startTimer) {
            floor(state.diffSecs).toString()
        } else {
            "0"
        }
    }


}


fun RBuilder.myToolbar(
    startTimer: Boolean,
    diffSecs: Double,
    gameModeId: Int,
    shareDialogDataHolder: ShareDialogDataHolder,
): ReactElement {
    return child(Toolbar::class) {
        attrs {
            this.startTimer = startTimer
            this.diffSecs = diffSecs
            this.gameModeId = gameModeId
            this.shareDialogDataHolder = shareDialogDataHolder
        }
    }
}