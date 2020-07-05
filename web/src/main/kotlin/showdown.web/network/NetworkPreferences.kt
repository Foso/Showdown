package showdown.web.network

import kotlin.browser.window


class NetworkPreferences {
    /**
    Change the testingAddress to the IP of your server
     */

    val herokuUrl = "wss://hidden-plateau-72953.herokuapp.com/"


    val port = window.location.port


    var hostUri: String = "${window.location}+${window.location.hostname}"

    val hostname = if (hostUri.contains("localhost")) {

        console.log("HOST " + window.location.toString().substringAfter("game?",missingDelimiterValue = ""))
        "ws://192.168.178.56:23567/"
    } else if(hostUri.startsWith("http://192.")) {
        "ws://192.168.178.56:23567/"
    }

    else {
        console.log("HOST " + hostUri)
        "wss://shwdwn.herokuapp.com/"
    }

    fun websocketUrl(): String {

            //http://localhost:3001/#/room=MyRoom&pw=Hallo
            val socketPath =window.location.toString().substringAfter("#/",missingDelimiterValue = "").replace("/","=")
            console.log( hostname+"showdown?"+socketPath)
          return   hostname+"showdown?"+socketPath


        }


}