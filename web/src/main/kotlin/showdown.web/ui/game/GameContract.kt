package showdown.web.ui.game

import de.jensklingenberg.showdown.model.ClientGameConfig
import de.jensklingenberg.showdown.model.Member
import de.jensklingenberg.showdown.model.Result
import react.RState
import kotlin.js.Date

interface StateView<T>{
    fun newState(buildState: T.(T) -> Unit)
    fun getState(): T
}

interface GameContract {
    interface View : StateView<HomeViewState> {
        fun showInfoPopup(it: String)
    }

    interface Presenter {
        fun onCreate()
        fun onDestroy()
        fun reset()
        fun joinGame(playerName:String)
        fun showVotes()
        fun onSelectedVote(voteId: Int)
        fun changeConfig(gameModeId: Int, gameOptions: String)
        fun changeRoomPassword(password:String)

    }

    interface HomeViewState : RState {
        var playerName: String
        var errorMessage: String
        var showSnackbar: Boolean
        var players: List<Member>
        var options: List<String>
        var results : List<Result>
        var gameModeId: Int
        var snackbarMessage :String
        var customOptions : String
        var showEntryPopup:Boolean
        var selectedOptionId: Int
        var roomPassword: String
        var gameStartTime : Date
        var startEstimationTimer : Boolean
        var requestRoomPassword:Boolean
        var autoReveal: Boolean
        var showConnectionError: Boolean

        //TOOLBAR
        var diffSecs : Double
        var showSettings : Boolean

    }
}