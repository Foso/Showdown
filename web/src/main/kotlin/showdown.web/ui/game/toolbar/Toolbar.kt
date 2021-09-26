package showdown.web.ui.game.toolbar

import com.badoo.reaktive.observable.subscribe
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
import kotlin.math.floor


external interface ToolbarState : State {
    var diffSecs: Double
    var startTimer: Boolean
    var showSettingsDialog: Boolean
    var gameModeId: Int
    var shareDialogDataHolder: ShareDialogDataHolder

}


external interface ToolbarProps : Props {
    var startTimer: Boolean
}

class Toolbar(props: ToolbarProps) : RComponent<ToolbarProps, ToolbarState>(props) {

    private val viewModel: ToolContract.ViewModel by lazy {
        ToolbarViewModel()
    }

    override fun componentWillUnmount() {
        viewModel.onDestroy()
    }

    override fun ToolbarState.init(props: ToolbarProps) {
        this.startTimer = props.startTimer
        this.showSettingsDialog = false
        this.gameModeId = 0
        this.shareDialogDataHolder = ShareDialogDataHolder(false, false)

    }

    override fun componentWillReceiveProps(nextProps: ToolbarProps) {
        if (nextProps.startTimer) {
            viewModel.resetTimer()
        }
        setState {
            this.startTimer = nextProps.startTimer
        }
    }

    override fun componentDidMount() {
        viewModel.onCreate()

        viewModel.roomConfigSubject.subscribe {
            it?.let {
                setState {
                    this.shareDialogDataHolder = ShareDialogDataHolder(it.autoReveal, it.anonymResults)

                }
            }
        }
        viewModel.timerSubject.subscribe {
            setState {
                diffSecs = it
            }
        }

    }

    override fun RBuilder.render() {
        if (state.showSettingsDialog) {
            settingsDialog(onCloseFunction = {
                setState {
                    this.showSettingsDialog = false
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
                        showSettingsDialog = true
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
) {
    child(Toolbar::class) {
        attrs {
            this.startTimer = startTimer

        }
    }
}