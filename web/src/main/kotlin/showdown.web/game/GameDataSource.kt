package showdown.web.game

import com.badoo.reaktive.observable.Observable
import de.jensklingenberg.showdown.model.*

interface GameDataSource {
    fun prepareGame()
    fun joinRoom(name:String,password:String,roomName:String)
    fun observeGameState(): Observable<GameState>
    fun requestReset()
    fun observeErrors(): Observable<ShowdownError?>
    fun showVotes()
    fun onSelectedVote(voteId: Int)
    fun changeConfig(clientGameConfig: ClientGameConfig)
}

