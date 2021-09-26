package showdown.web.ui.game.field

import com.badoo.reaktive.observable.subscribe
import de.jensklingenberg.showdown.model.GameState
import de.jensklingenberg.showdown.model.Member
import de.jensklingenberg.showdown.model.Result
import kotlinx.html.js.onClickFunction
import materialui.components.checkbox.checkbox
import react.Props
import react.RBuilder
import react.RComponent
import react.State
import react.dom.attrs
import react.dom.div
import react.setState
import showdown.web.ui.game.Strings

fun RBuilder.gameFieldComponent() {
    child(GameFieldComponent::class) {}
}

external interface GameFieldState : State {
    var players: List<Member>
    var options: List<String>
    var results: List<Result>
    var selectedOptionId: Int
    var isSpectator: Boolean
}


class GameFieldComponent : RComponent<Props, GameFieldState>() {

    private val viewmodel: GameFieldContract.Viewmodel by lazy {
        GameFieldViewmodel()
    }

    override fun GameFieldState.init() {
        players = emptyList()
        options = listOf()
        results = emptyList()
        selectedOptionId = -1
        isSpectator = false
    }

    override fun componentDidMount() {
        viewmodel.onCreate()
        viewmodel.spectatorSubject.subscribe {
            setState {
                this.isSpectator = it
            }
        }
        viewmodel.gameStateSubject.subscribe { newState ->
            when (newState) {
                GameState.NotStarted -> {
                }
                is GameState.Started -> {
                    val config = newState.clientGameConfig
                    setState {
                        this.options = config.voteOptions
                        this.results = emptyList()
                        this.selectedOptionId = -1
                    }
                }
                is GameState.PlayerListUpdate -> {
                    setState {
                        this.players = newState.members
                    }
                }
                is GameState.ShowVotes -> {
                    setState {
                        this.results = newState.results
                    }
                }
            }
        }
    }

    override fun RBuilder.render() {

        //OPTIONS
        if (!state.isSpectator) {
            optionsList(state.selectedOptionId, state.options, onOptionClicked = { index: Int ->
                setState {
                    this.selectedOptionId = index
                }
                viewmodel.onSelectedVote(index)
            })
        }
        spectatorCheckbox()


        //RESULTS
        if (state.results.isNotEmpty()) {
            resultsList(state.results)
        }

        //PLAYERS
        playersList(state.players)
        //


    }

    private fun RBuilder.spectatorCheckbox() {
        div {
            checkbox {
                attrs {
                    checked = state.isSpectator
                    onClickFunction = {
                        viewmodel.setSpectatorStatus(!state.isSpectator)
                    }
                }
            }
            +Strings.IMSPECTATOR
        }
    }
}

