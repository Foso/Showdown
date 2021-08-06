package showdown.web.ui.game.toolbar

import showdown.web.Application
import de.jensklingenberg.showdown.model.api.clientrequest.NewGameConfig
import de.jensklingenberg.showdown.model.fibo
import de.jensklingenberg.showdown.model.modFibo
import de.jensklingenberg.showdown.model.powerOf2
import de.jensklingenberg.showdown.model.tshirtSizesList
import showdown.web.game.GameDataSource

class ToolbarPresenter(view: ToolContract.View) : ToolContract.Presenter {
    private val gameDataSource: GameDataSource = Application.gameDataSource

    override fun reset() {
        gameDataSource.requestReset()
    }

    override fun showVotes() {
        gameDataSource.showVotes()
    }

    override fun changeConfig(gameModeId: Int, gameOptions: String) {
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

}