package showdown.web.ui.new

import de.jensklingenberg.showdown.model.ClientVote
import de.jensklingenberg.showdown.model.Option
import de.jensklingenberg.showdown.model.Result

import react.RState



interface NewContract {
    interface View {
        fun showError(error: String)
    }

    interface Presenter {
        fun onCreate()
        fun joinGame()
        fun createNewRoom(roomName:String)
    }

    interface HomeViewState : RState {
        var errorMessage: String
        var showSnackbar: Boolean

        var options: List<Option>
        var roomName : String

    }
}