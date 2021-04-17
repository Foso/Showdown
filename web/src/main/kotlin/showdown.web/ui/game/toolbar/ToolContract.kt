package showdown.web.ui.game.toolbar

interface ToolContract {
    interface Presenter {
        fun reset()
        fun showVotes()
        fun changeConfig(gameModeId: Int, gameOptions: String)
    }

    interface View {

    }
}