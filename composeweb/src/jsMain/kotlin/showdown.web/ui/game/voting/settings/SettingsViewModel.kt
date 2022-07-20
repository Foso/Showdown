package showdown.web.ui.game.voting.settings

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.badoo.reaktive.observable.subscribe
import de.jensklingenberg.showdown.model.api.clientrequest.NewGameConfig
import de.jensklingenberg.showdown.model.fibo
import de.jensklingenberg.showdown.model.modFibo
import de.jensklingenberg.showdown.model.powerOf2
import de.jensklingenberg.showdown.model.tshirtSizesList
import showdown.web.Application
import showdown.web.game.GameDataSource

class SettingsViewModel( private val gameDataSource: GameDataSource = Application.gameDataSource){
    var isAnonymResults: MutableState<Boolean> = mutableStateOf(false)
    var autoRevealEnabled: MutableState<Boolean> = mutableStateOf(false)


    fun onCreate() {
        gameDataSource.observeRoomConfig().subscribe {
            it?.let {
                autoRevealEnabled.value = it.autoReveal
                isAnonymResults.value = it.anonymResults
            }
        }
    }


    fun changeConfig(gameModeId: Int, gameOptions: String) {
        val mode = when (gameModeId) {
            0 -> {
                fibo
            }
            1 -> {
                tshirtSizesList
            }
            2 -> {
                modFibo
            }
            3 -> {
                powerOf2
            }
            4 -> {
                gameOptions.split(";")
            }
            else -> fibo
        }
        val config =
            NewGameConfig(voteOptions = mode)
        gameDataSource.changeConfig(config)
    }

    fun changeRoomPassword(password: String) {
        gameDataSource.changeRoomPassword(password)
    }

    fun setAutoReveal(enable: Boolean) {
        gameDataSource.setAutoReveal(enable)
    }

    fun setAnonymVote(enable: Boolean) {
        gameDataSource.setAnonymVote(enable)
    }

}