package showdown.web.ui.game.toolbar

interface ToolContract {
    interface ViewModel {
        fun reset()
        fun showVotes()
        fun changeConfig(gameModeId: Int, gameOptions: String)
    }


}