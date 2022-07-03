package showdown.web.ui.game

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
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
        fun setSpectatorStatus(b: Boolean)
        var isSpectator: MutableState<Boolean>

        fun reset()
        fun showVotes()
        var options: MutableState<List<String>>
        var selectedOption: MutableState<Int>
        fun changeConfig(gameModeId: Int, gameOptions: String)
        fun setAutoReveal(any: Boolean)
        fun setAnonymVote(any: Boolean)

        var autoReveal: MutableState<Boolean>

        var anonymResults: MutableState<Boolean>
    }

}