package showdown.web.ui.home

import com.soywiz.klock.DateTime
import de.jensklingenberg.showdown.model.Member
import de.jensklingenberg.showdown.model.Result
import org.w3c.dom.events.EventTarget
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
        var customOptions : String
        var showEntryPopup:Boolean
        var showShareDialog: Boolean
        var selectedOptionId: Int
        var roomPassword: String
        var timerStart : DateTime
        var startTimer : Boolean
        var anchor: EventTarget?
        var requestRoomPassword:Boolean

        var showConnectionError: Boolean

        //TOOLBAR
        var diffSecs : Double
        var showSettings : Boolean
        var openMenu: Boolean
        var showChangePassword:Boolean

    }
}