package showdown.web.network

import de.jensklingenberg.showdown.debugPort
import kotlinx.browser.window


class NetworkPreferences {
    /**
    Change the testingAddress to the IP of your server
     */
    private val localIP = "192.168.178.20"
    var hostUri: String = "${window.location}"

    private val hostname = when {
        hostUri.contains("localhost") -> {
            "ws://localhost:$debugPort/"
        }
        hostUri.startsWith("http://192.") -> {
            "ws://${localIP}:$debugPort/"
        }
        else -> {
            "wss://${window.location.hostname}/"
        }
    }

    fun websocketUrl(): String {

        val roomName =
            window.location.toString().substringAfter("/room/").substringBefore("/").substringBefore("?")

        return hostname + "showdown?room=" + roomName


    }


}