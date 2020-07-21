package showdown.web.network

import kotlin.browser.window


class NetworkPreferences {
    /**
    Change the testingAddress to the IP of your server
     */

    var hostUri: String = "${window.location}"

    val hostname = when {
        hostUri.contains("localhost") -> {
            "ws://localhost:23567/"
        }
        hostUri.startsWith("http://192.") -> {
            "ws://192.168.178.56:23567/"
        }
        else -> {
            "wss://${window.location.hostname}/"
        }
    }

    fun websocketUrl(): String {

        val roomName =
            window.location.toString().substringAfter("/room/").substringBefore("/")
        val url = hostname + "showdown?room=" + roomName

        return url


    }


}