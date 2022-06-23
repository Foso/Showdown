package showdown.web.ui.game.toolbar

import com.badoo.reaktive.subject.behavior.BehaviorSubject
import de.jensklingenberg.showdown.model.ClientGameConfig

interface ToolContract {
    interface ViewModel {
        fun reset()
        fun showVotes()
        fun changeConfig(gameModeId: Int, gameOptions: String)
        fun onDestroy()
        fun onCreate()
        fun resetTimer()

        val roomConfigSubject: BehaviorSubject<ClientGameConfig?>
        val timerSubject: BehaviorSubject<Double>
    }


}