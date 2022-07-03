package showdown.web.ui.game.voting

import androidx.compose.runtime.State
import de.jensklingenberg.showdown.model.Member
import de.jensklingenberg.showdown.model.Result


interface GameContract {


    interface Viewmodel {
        fun onCreate()
        fun onDestroy()
        fun joinGame(playerName: String, password: String = "", isSpectator: Boolean = false)
        fun changeRoomPassword(password: String)
        val showEntryPopup: State<Boolean>
        fun onSelectedVote(voteId: Int)
        fun setSpectatorStatus(b: Boolean)
        val isSpectator: State<Boolean>

        fun reset()
        fun showVotes()
      
        fun changeConfig(gameModeId: Int, gameOptions: String)
        fun setAutoReveal(any: Boolean)
        fun setAnonymVote(any: Boolean)
        fun onEntryPopupClosed()

        val options: State<List<String>>
        val selectedOption: State<Int>
        val autoReveal: State<Boolean>
        val anonymResults: State<Boolean>
        val showConnectionError: State<Boolean>
        val results: State<List<Result>>
        val members: State<List<Member>>
        val timer: State<Int>
    }

}