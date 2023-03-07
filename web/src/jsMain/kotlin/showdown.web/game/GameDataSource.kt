package showdown.web.game

import de.jensklingenberg.showdown.model.ClientGameConfig
import de.jensklingenberg.showdown.model.GameState
import de.jensklingenberg.showdown.model.ShowdownError
import de.jensklingenberg.showdown.model.api.clientrequest.NewGameConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import showdown.web.network.Either

interface GameDataSource {
    fun connectToServer(): Flow<Either>
    fun joinRoom(name: String, password: String, isSpectator: Boolean)
    fun observeGameState(): StateFlow<GameState>
    fun observeMessage(): StateFlow<String>
    fun observeRoomConfig(): StateFlow<ClientGameConfig?>
    fun observeSpectatorStatus(): StateFlow<Boolean>
    fun requestReset()
    fun observeErrors(): StateFlow<ShowdownError?>
    fun showVotes()
    fun onSelectedVote(voteId: Int)
    fun changeConfig(newGameConfig: NewGameConfig)
    fun changeRoomPassword(password: String)
    fun getPlayerName(): String
    fun getRoomPassword(): String
    fun setAutoReveal(enabled: Boolean)
    fun setSpectatorStatus(enabled: Boolean)
    fun setAnonymVote(enabled: Boolean)
}

