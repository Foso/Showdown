package showdown.web.ui.game

interface StateView<T> {
    fun newState(buildState: T.(T) -> Unit)
}

interface GameContract {
    interface View : StateView<GameViewState> {
        fun showInfoPopup(it: String)
    }

    interface Viewmodel {
        fun onCreate()
        fun onDestroy()
        fun joinGame(playerName: String, password: String = "")
        fun changeRoomPassword(password: String)
    }

}