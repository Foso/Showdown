package showdown.web.ui.home

import com.soywiz.klock.DateTime
import de.jensklingenberg.showdown.model.ClientVote
import de.jensklingenberg.showdown.model.Option
import de.jensklingenberg.showdown.model.Result

import react.RState

interface HomeContract {
    interface View {
        fun newState(buildState: HomeViewState.(HomeViewState) -> Unit)
        fun getState(): HomeViewState
    }

    interface Presenter {
        fun onCreate()
        fun reset()
        fun joinGame()
        fun showVotes()
        fun onSelectedVote(voteId: Int)
        fun changeConfig(gameModeId: Int, gameOptions: String)

    }

    interface HomeViewState : RState {
        var playerName: String
        var errorMessage: String
        var showSnackbar: Boolean
        var players: List<ClientVote>
        var options: List<Option>
        var results : List<Result>
        var gameModeId: Int
        var customOptions : String
        var showEntryPopup:Boolean
        var showShareDialog: Boolean
        var selectedOptionId: Int
        var roomPassword: String
        var timerStart : DateTime
        var diffSecs : Double


    }
}