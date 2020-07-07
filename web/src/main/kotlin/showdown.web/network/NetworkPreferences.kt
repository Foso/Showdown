package showdown.web.network

import kotlin.browser.window


class NetworkPreferences {
    /**
    Change the testingAddress to the IP of your server
     */

    val herokuWsUrl = "wss://shwdwn.herokuapp.com/"


    val port = window.location.port


    var hostUri: String = "${window.location}+${window.location.hostname}"

    val hostname = when {
        hostUri.contains("localhost") -> {
            console.log("HOST " + hostUri)
            "ws://localhost:23567/"
        }
        hostUri.startsWith("http://192.") -> {
            "ws://192.168.178.56:23567/"
        }
        else -> {
            console.log("HOST " + hostUri)
            herokuWsUrl
        }
    }

    fun websocketUrl(): String {

        val roomName =
            window.location.toString().substringAfter("/room/").substringBefore("/")
       val url = hostname + "showdown?room=" + roomName

        console.log("URL $url")
        return url


    }


}