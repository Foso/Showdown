package showdown.web.game

import com.badoo.reaktive.completable.Completable
import com.badoo.reaktive.observable.Observable
import de.jensklingenberg.showdown.model.ClientGameConfig
import de.jensklingenberg.showdown.model.GameState
import de.jensklingenberg.showdown.model.ShowdownError
import de.jensklingenberg.showdown.model.api.clientrequest.NewGameConfig

interface GameDataSource {
    fun connectToServer(): Completable
    fun joinRoom(name: String, password: String)
    fun observeGameState(): Observable<GameState>
    fun observeMessage(): Observable<String>
    fun observeRoomConfig(): Observable<ClientGameConfig?>
    fun observeSpectatorStatus(): Observable<Boolean>
    fun requestReset()
    fun observeErrors(): Observable<ShowdownError?>
    fun showVotes()
    fun onSelectedVote(voteId: Int)
    fun changeConfig(newGameConfig: NewGameConfig)
    fun changeRoomPassword(password: String)
    fun getPlayerName(): String
    fun getRoomPassword(): String
    fun setAutoReveal(enabled: Boolean)
    fun setSpectatorStatus(enabled: Boolean)
    fun setAnonymVote(enabled: Boolean)
    //fun getRoomConfig(): NewGameConfig?
}

