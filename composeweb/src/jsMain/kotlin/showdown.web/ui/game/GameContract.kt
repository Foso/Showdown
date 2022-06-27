package showdown.web.ui.game

import androidx.compose.runtime.MutableState
import com.badoo.reaktive.subject.behavior.BehaviorSubject

interface StateView<T> {
    fun newState(buildState: T.(T) -> Unit)
}

interface GameContract {
    interface View : StateView<GameViewState> {
        fun showInfoPopup(it: String)
    }

    interface Viewmodel {
        fun onCreate()
        fun onDestroy()
        fun joinGame(playerName: String, password: String = "", isSpectator: Boolean = false)
        fun changeRoomPassword(password: String)
        val starEstimationTimerSubject: BehaviorSubject<Boolean>
        var showEntryPopup: MutableState<Boolean>
        fun onSelectedVote(voteId: Int)
    }

}