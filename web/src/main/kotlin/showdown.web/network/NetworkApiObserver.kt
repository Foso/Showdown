package showdown.web.network

import de.jensklingenberg.showdown.model.ClientGameConfig
import de.jensklingenberg.showdown.model.GameState
import de.jensklingenberg.showdown.model.ServerResponse

interface NetworkApiObserver {

    fun onGameStateChanged(gameState: GameState)
    fun onError(errorEvent: ServerResponse.ErrorEvent)
    fun onMessageEvent(message: String)
    fun onConfigUpdated(clientGameConfig: ClientGameConfig)
    fun onSpectatorStatusChanged(isSpectator:Boolean)
}