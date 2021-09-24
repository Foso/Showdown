package showdown.web.ui.game.toolbar

import kotlinx.browser.window
import materialui.components.appbar.appBar
import materialui.components.appbar.enums.AppBarColor
import materialui.components.appbar.enums.AppBarPosition
import react.Props
import react.RBuilder
import react.RComponent
import react.State
import react.dom.attrs
import react.dom.div
import react.setState
import showdown.web.ui.game.ShareDialogDataHolder
import showdown.web.ui.game.settingsDialog
import showdown.web.wrapper.material.SettingsIcon
import showdown.web.wrapper.material.VisibilityIcon
import showdown.web.wrapper.material.icons.AddCircleIcon
import kotlin.js.Date
import kotlin.math.floor


external interface ToolbarState : State {
    var diffSecs: Double
    var startTimer: Boolean
    var showShareDialog: Boolean
    var gameModeId: Int
    var shareDialogDataHolder: ShareDialogDataHolder
    var gameStartTime: Date
}


external interface ToolbarProps : Props {
    var startTimer: Boolean

    var gameModeId: Int
    var shareDialogDataHolder: ShareDialogDataHolder
    var gameStartTime: Date

}

class Toolbar(props: ToolbarProps) : RComponent<ToolbarProps, ToolbarState>(props) {

    private val viewModel: ToolContract.ViewModel by lazy {
        ToolbarViewModel()
    }


    override fun ToolbarState.init(props: ToolbarProps) {
        this.startTimer = props.startTimer
        this.showShareDialog = false

        this.gameModeId = props.gameModeId
        this.shareDialogDataHolder = props.shareDialogDataHolder
        this.gameStartTime = props.gameStartTime
    }

    override fun componentWillReceiveProps(nextProps: ToolbarProps) {
        println("NextProps ${props.shareDialogDataHolder} == ${nextProps.shareDialogDataHolder}")
        setState {

            this.startTimer = nextProps.startTimer
            this.gameModeId = nextProps.gameModeId
            this.shareDialogDataHolder = nextProps.shareDialogDataHolder
            this.gameStartTime = nextProps.gameStartTime
        }
    }

    override fun componentDidMount() {
        window.setInterval({
            setState {
                val startDate = state.gameStartTime
                val endDate = Date()

                diffSecs = (endDate.getTime() - startDate.getTime()) / 1000
            }
        }, 1000)


    }

    override fun RBuilder.render() {
        if (state.showShareDialog) {
            settingsDialog(onCloseFunction = {
                setState {
                    this.showShareDialog = false
                }
            }, state.gameModeId, onSave = { gameModeId, gameOptions ->
                viewModel.changeConfig(gameModeId, gameOptions)
            }, state.shareDialogDataHolder)
        }

        myAppBar()
    }

    private fun RBuilder.myAppBar() {
        appBar {
            attrs {
                position = AppBarPosition.static
                color = AppBarColor.primary
            }
            div {
                toolbarButton("New Voting", AddCircleIcon, onClick = {
                    viewModel.reset()
                })

                toolbarButton("Show Votes", VisibilityIcon, onClick = {
                    viewModel.showVotes()
                })
                toolbarButton("Settings", SettingsIcon, onClick = {
                    setState {
                        showShareDialog = true
                    }
                })
                +"Estimation time: ${getTimerText()} seconds."
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


}


fun RBuilder.myToolbar(
    startTimer: Boolean,

    gameModeId: Int,
    shareDialogDataHolder: ShareDialogDataHolder,
    gameStartTime: Date,
) {
    child(Toolbar::class) {
        attrs {
            this.startTimer = startTimer
            this.gameModeId = gameModeId
            this.shareDialogDataHolder = shareDialogDataHolder
            this.gameStartTime = gameStartTime
        }
    }
}