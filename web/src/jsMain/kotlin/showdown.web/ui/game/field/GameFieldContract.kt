package showdown.web.ui.game.field

import com.badoo.reaktive.subject.behavior.BehaviorSubject
import de.jensklingenberg.showdown.model.GameState


interface GameFieldContract {

    interface Viewmodel {
        fun onCreate()
        fun onDestroy()
        fun onSelectedVote(voteId: Int)

        fun setSpectatorStatus(b: Boolean)

        val gameStateSubject: BehaviorSubject<GameState>
        val spectatorSubject: BehaviorSubject<Boolean>
    }

}