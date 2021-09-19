package showdown.web.ui.game

import de.jensklingenberg.showdown.model.Member
import de.jensklingenberg.showdown.model.Result
import react.State
import kotlin.js.Date

external interface HomeViewState : State {
    var playerName: String
    var errorMessage: String
    var showSnackbar: Boolean
    var players: List<Member>
    var options: List<String>
    var results: List<Result>
    var gameModeId: Int
    var snackbarMessage: String
    var customOptions: String
    var showEntryPopup: Boolean
    var selectedOptionId: Int
    var roomPassword: String
    var gameStartTime: Date
    var startEstimationTimer: Boolean
    var requestRoomPassword: Boolean
    var autoReveal: Boolean
    var showConnectionError: Boolean
    var anonymResults:Boolean
    //TOOLBAR
    var diffSecs: Double
    var showSettings: Boolean

    var isSpectator: Boolean

}