package showdown.web.ui.new

import Application
import showdown.web.game.GameDataSource

class NewPresenter(private val view: NewContract.View) : NewContract.Presenter {

    private val gameDataSource: GameDataSource = Application.gameDataSource

    override fun onCreate() {
        gameDataSource.prepareGame()

    }

    override fun joinGame() {
        gameDataSource.joinRoom("NOt set","geheim")
    }

    override fun createNewRoom(roomName: String,gameModeId:Int) {
        gameDataSource.createNewRoom(roomName)
    }


}