package showdown.web.ui.game

import react.State
import kotlin.js.Date

external interface GameViewState : State {
    var playerName: String
    var errorMessage: String
    var showSnackbar: Boolean
    var gameModeId: Int
    var snackbarMessage: String
    var showEntryPopup: Boolean
    var roomPassword: String
    var gameStartTime: Date
    var startEstimationTimer: Boolean
    var requestRoomPassword: Boolean
    var autoReveal: Boolean
    var showConnectionError: Boolean
    var anonymResults: Boolean
}