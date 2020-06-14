package showdown.web.game

import com.badoo.reaktive.observable.Observable
import de.jensklingenberg.showdown.model.*

interface GameDataSource {
    fun getPlayer():Player?
    fun prepareGame()
    fun join()
    fun observeGameState(): Observable<GameState>
    fun requestReset()
    fun observePlayer(): Observable<Int>
    fun observeMap(): Observable<List<Warrior>>

    fun startGame()
    fun revealCards()
    fun onSelectedVote(i: Int)
}

