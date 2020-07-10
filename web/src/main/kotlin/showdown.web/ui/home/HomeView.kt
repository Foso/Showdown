package showdown.web.ui.home


import com.soywiz.klock.DateTime
import kotlinx.css.Color
import kotlinx.css.TextAlign
import kotlinx.css.backgroundColor
import kotlinx.css.textAlign
import kotlinx.html.id
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import materialui.components.appbar.appBar
import materialui.components.appbar.enums.AppBarColor
import materialui.components.appbar.enums.AppBarPosition
import materialui.components.button.button
import materialui.components.button.enums.ButtonColor
import materialui.components.button.enums.ButtonVariant
import materialui.components.dialog.dialog
import materialui.components.divider.divider
import materialui.components.formcontrol.enums.FormControlVariant
import materialui.components.list.list
import materialui.components.listitemtext.listItemText
import materialui.components.menu.menu
import materialui.components.menuitem.menuItem
import materialui.components.textfield.textField
import org.w3c.dom.HTMLInputElement
import react.RBuilder
import react.RComponent
import react.RProps
import react.dom.div
import react.dom.h2
import react.dom.h3
import react.dom.label
import react.setState
import showdown.web.wrapper.material.AddCircleIcon
import showdown.web.wrapper.material.SettingsIcon
import showdown.web.wrapper.material.ShareIcon
import showdown.web.wrapper.material.VisibilityIcon
import styled.css
import styled.styledDiv
import styled.styledH3
import kotlin.browser.window
import kotlin.math.floor


class HomeView : RComponent<RProps, HomeContract.HomeViewState>(), HomeContract.View {


    private val presenter: HomeContract.Presenter by lazy {
        HomePresenter(this)
    }

    override fun HomeContract.HomeViewState.init() {
        showSnackbar = false
        members = emptyList()
        options = listOf()
        results = emptyList()
        gameModeId = 0
        playerName = "Jens"
        customOptions = ""
        showEntryPopup = false

        selectedOptionId = -1
        roomPassword = ""

        showSettings = false
        startTimer = false
        requestRoomPassword = false

        //TOOLBAR
        anchor = null
        openMenu = false
        showShareDialog = false
        timerStart = DateTime.now()
        diffSecs = 0.0

        //MESSAGE
        showConnectionError =false
        showChangePassword=false

    }


    override fun componentDidMount() {
        window.setInterval({
            setState {
                val nowDate = DateTime.now()
                diffSecs = (nowDate - state.timerStart).seconds
            }
        }, 1000)
        presenter.onCreate()
    }

    override fun RBuilder.render() {

        connectionErrorSnackbar(this,state.showConnectionError){
            presenter.onCreate()

            setState {
                this.showConnectionError=false
            }
        }
        entryDialog()
        insertPasswordDialog()
        setRoomPasswordDialog()
        shareDialog(state.showShareDialog){
            setState {
                this.showShareDialog = false
            }
        }
        toolbar2()
        optionsList()
        members()
        results()

        if (state.showSettings) {

            adminMenu(state.gameModeId) { gameModeId, gameOptions ->
                setState {
                    this.gameModeId = gameModeId
                    this.customOptions = gameOptions
                }
                log("GameMod" + gameModeId + " " + gameOptions)
                presenter.changeConfig(gameModeId, gameOptions)
            }

        }
    }



    private fun RBuilder.setRoomPasswordDialog() {
        dialog {
            attrs {
                this.open = state.showChangePassword
            }

            div {
                textField {
                    attrs {
                        variant = FormControlVariant.filled
                        value(state.roomPassword)
                        label {
                            +"Set a new room password:"
                        }
                        onChangeFunction = {
                            val target = it.target as HTMLInputElement

                            setState {
                                this.roomPassword = target.value
                            }
                        }
                    }

                }
            }

            button {
                attrs {
                    text("Save Password")
                    variant = ButtonVariant.contained
                    color = ButtonColor.primary
                    onClickFunction = {
                        setState {
                            this.showChangePassword = false
                        }
                        presenter.changeRoomPassword(state.roomPassword)
                    }
                }
            }

            button {
                attrs {
                    text("Close")
                    variant = ButtonVariant.contained
                    color = ButtonColor.primary
                    onClickFunction = {
                        setState {
                            this.showChangePassword = false
                        }
                    }
                }
            }

        }
    }

    private fun RBuilder.insertPasswordDialog() {
        dialog {
            attrs {
                this.open = state.requestRoomPassword
            }

            div {
                textField {
                    attrs {
                        variant = FormControlVariant.filled
                        value(state.roomPassword)
                        label {
                            +"A room password is required"
                        }
                        onChangeFunction = {
                            val target = it.target as HTMLInputElement

                            setState {
                                this.roomPassword = target.value
                            }
                        }
                    }

                }
            }

            button {
                attrs {
                    text("Join Game")
                    variant = ButtonVariant.contained
                    color = ButtonColor.primary
                    onClickFunction = {
                        setState {
                            this.requestRoomPassword = false
                        }
                        presenter.joinGame()
                    }
                }
            }

        }
    }

    private fun RBuilder.entryDialog() {
        dialog {
            attrs {
                this.open = state.showEntryPopup
            }

            div {
                textField {
                    attrs {
                        variant = FormControlVariant.filled
                        label {
                            +"Insert a Name"
                        }
                        value(state.playerName)
                        onChangeFunction = {
                            val target = it.target as HTMLInputElement

                            setState {
                                this.playerName = target.value
                            }
                        }
                    }

                }

            }

            button {
                attrs {
                    text("Join Game")
                    variant = ButtonVariant.contained
                    color = ButtonColor.primary
                    onClickFunction = {
                        setState {
                            this.showEntryPopup = false
                        }
                        presenter.joinGame()
                    }
                }
            }

        }
    }



    fun styleProps(textAlign: String = "", display: String = "", width: String = ""): String {
        return kotlinext.js.js {
            if (textAlign.isNotEmpty()) {
                this.textAlign = textAlign
            }

            if (width.isNotEmpty()) {
                this.width = width
            }

            if (display.isNotEmpty()) {
                this.display = display
            }
        }

    }

    private fun RBuilder.members() {
        h2 {
            +"Players (${state.members.size})"
        }

        div {
            if (state.members.isEmpty()) {
                h3 {
                    +"Nobody here, share the link!"
                }
            } else {
                state.members.forEach {

                }
            }

        }

        list {

            state.members.forEach {
                listItemText {

                    styledH3 {
                        css {
                            this.textAlign = TextAlign.center
                        }
                        +("Player: " + it.playerName + " Status:" + it.voteStatus + "\n")
                    }

                }
                divider {}
            }
        }
    }

    private fun RBuilder.results() {

        if (state.results.isNotEmpty()) {
            h2 {
                +"Result:"
            }

            h2 {
                +"Top Voted Answer: 26"
            }

        }

        state.results.forEachIndexed { index, result ->
            h3 {
                +"\"${result.optionName}\" ${result.voterName}"
            }

        }

        /**
         *  ApexChart {
        attrs {
        width = "50%"
        type = "bar"
        series = arrayOf(Serie("series-1", arrayOf(3, 4)))
        options = ChartOptions(
        chart = Chart("basic-bar"),
        plotOptions = PlotOptions(Bar(horizontal = true)),
        xaxis = Xaxis(arrayOf("5", "8"))
        )
        }
        }
         */
    }

    private fun RBuilder.optionsList() {
        h2 {
            +"Select an option:"
        }
        state.options.forEachIndexed { index, option ->

            button {
                attrs {
                    this.color = if (index != state.selectedOptionId) {
                        ButtonColor.primary
                    } else {
                        ButtonColor.secondary
                    }
                    variant = ButtonVariant.outlined
                    onClickFunction = {
                        setState {
                            this.selectedOptionId = index
                        }
                        presenter.onSelectedVote(option.id)
                    }

                }
                +option.text

            }

        }
    }


    private fun RBuilder.toolbar2() {

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
                            presenter.reset()
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
                            presenter.showVotes()
                        }
                        startIcon {
                            VisibilityIcon {}
                        }
                    }
                }



                button {
                    +"Menu"
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

                menu() {
                    attrs {
                        id = "simple-menu"
                        open = state.openMenu
                        onClose = { event, s ->
                            setState {
                                // anchor = currentTarget
                                openMenu = false

                            }
                        }
                        //asDynamic()["anchorEl"] = state.anchor
                        anchorEl(state.anchor)
                    }
                    menuItem {
                        attrs {
                            this.value = "HEEEE"

                            this.onClickFunction = {
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
                            this.value = "HEEEE"
                            this.onClickFunction = {
                                setState {
                                    showChangePassword=true
                                    openMenu = false
                                }
                            }
                        }
                       label {
                           +"Room password is: ${state.roomPassword}"
                       }
                    }

                }


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

                +"Estimation time: ${getTimerText()} seconds. "
            }


        }


        styledDiv {
            css {
                backgroundColor = Color.tomato
            }


        }
    }

    fun getTimerText(): String {
      return  if(state.startTimer){
            floor(state.diffSecs).toString()
        }else{
            "0"
        }
    }

    private fun snackbarVisibility(): Boolean = state.showSnackbar

    override fun newState(buildState: HomeContract.HomeViewState.(HomeContract.HomeViewState) -> Unit) {
        setState {
            buildState(this)
        }
    }

    override fun getState(): HomeContract.HomeViewState = state
}


fun RBuilder.home() = child(HomeView::class) {
    this.attrs {

    }
}




