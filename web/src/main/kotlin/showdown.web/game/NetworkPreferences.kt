package showdown.web.game

import kotlin.browser.window


class NetworkPreferences {
    /**
    Change the testingAddress to the IP of your server
     */

    val herokuUrl = "wss://hidden-plateau-72953.herokuapp.com/"

    val apiVersion = "v1"


    val port = window.location.port


    var hostUri: String = "${window}+${window.location}+${window.location.hostname}"

    val hostname = if (hostUri.contains("localhost")) {
        console.log("HOST " + window.location.hostname)
        "ws://localhost:23567/"
    } else {
        "wss://hidden-plateau-72953.herokuapp.com/"
    }


}