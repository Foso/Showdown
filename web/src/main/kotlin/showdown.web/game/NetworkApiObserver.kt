package showdown.web.game

import de.jensklingenberg.showdown.model.ServerResponse
import de.jensklingenberg.showdown.model.GameState
import de.jensklingenberg.showdown.model.PlayerResponseEvent

interface NetworkApiObserver {

    fun onGameStateChanged(gameState: GameState)
    fun onPlayerEventChanged(gameResponse: PlayerResponseEvent)
    fun onError(gameJoined: ServerResponse.ErrorEvent)
    fun onPlayerCardRevealed(gameState: ServerResponse.PlayerVotes)
}