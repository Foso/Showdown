package showdown.web.ui.game.voting

import androidx.compose.runtime.State
import de.jensklingenberg.showdown.model.Member
import de.jensklingenberg.showdown.model.Result


interface GameContract {
    interface Viewmodel {
        fun onCreate()
        fun onDestroy()
        fun joinGame(playerName: String, password: String = "", isSpectator: Boolean = false)
        fun onSelectedVote(voteId: Int)
        fun setSpectatorStatus(b: Boolean)
        fun reset()
        fun showVotes()
        fun onEntryPopupClosed()

        val isRoomPasswordNeeded: State<Boolean>
        val isRegistration: State<Boolean>
        val isSpectator: State<Boolean>
        val options: State<List<String>>
        val selectedOption: State<Int>
        val isConnectionError: State<Boolean>
        val results: State<List<Result>>
        val members: State<List<Member>>
        val estimationTimer: State<Int>

    }

}