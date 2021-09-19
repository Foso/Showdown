package showdown.web.ui.game

interface StateView<T> {
    fun newState(buildState: T.(T) -> Unit)
    fun getState(): T
}

interface GameContract {
    interface View : StateView<HomeViewState> {
        fun showInfoPopup(it: String)
        fun setSpectatorStatus(it: Boolean)
    }

    interface Presenter {
        fun onCreate()
        fun onDestroy()
        fun joinGame(playerName: String)
        fun onSelectedVote(voteId: Int)
        fun changeRoomPassword(password: String)
        fun setSpectatorStatus(b: Boolean)

    }

}