package showdown.web.game

import com.badoo.reaktive.observable.Observable
import de.jensklingenberg.showdown.model.*

interface GameDataSource {
    fun prepareGame()
    fun joinRoom(name:String)
    fun observeGameState(): Observable<GameState>
    fun requestReset()

    fun createNewRoom(roomName: String)
    fun showVotes()
    fun onSelectedVote(i: Int)
    fun changeConfig(gameConfig: GameConfig)
}

