package showdown.web.network

import de.jensklingenberg.showdown.model.ServerResponse
import de.jensklingenberg.showdown.model.GameState
import de.jensklingenberg.showdown.model.PlayerResponseEvent

interface NetworkApiObserver {

    fun onGameStateChanged(gameState: GameState)
    fun onPlayerEventChanged(gameResponse: PlayerResponseEvent)
    fun onError(errorEvent: ServerResponse.ErrorEvent)
}