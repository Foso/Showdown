package showdown.web.ui.new

import de.jensklingenberg.showdown.model.ClientVote
import de.jensklingenberg.showdown.model.Option
import de.jensklingenberg.showdown.model.Result

import react.RState

class Player(val name: String,val value :String)


interface HomeContract {
    interface View {
        fun showError(error: String)
        fun setPlayerId(list: List<ClientVote>)

        fun setHidden(hidden:Boolean)
        fun setOptions(list:List<Option>)
        fun setResults(message: List<Result>)
    }

    interface Presenter {
        fun onCreate()
        fun reset()
        fun joinGame()
        fun startGame()
        fun revealCards()
        fun onSelectedVote(voteId: Int)
    }

    interface HomeViewState : RState {
        var errorMessage: String
        var showSnackbar: Boolean
        var playerList: List<ClientVote>
        var hidden: Boolean
        var options: List<Option>
        var results : List<Result>

    }
}