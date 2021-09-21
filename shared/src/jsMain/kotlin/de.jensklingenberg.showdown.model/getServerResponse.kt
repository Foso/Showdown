package de.jensklingenberg.showdown.model


fun getServerResponseType(toString: String): ServerResponseTypes {
    println("getServerResponseType: $toString")
    return ServerResponseTypes.values().firstOrNull() {
        toString.startsWith("{\"id\":${it.ordinal}")
    } ?: ServerResponseTypes.UNKNOWN
}


fun getServerResponse(json: String): ServerResponse? {
    println("getServerResponseType: $json")
    return when (getServerResponseType(json)) {
        ServerResponseTypes.STATE_CHANGED -> {
            ServerResponseParser.getGameStateChangedCommand(json)
        }
        ServerResponseTypes.ERROR -> {

            ServerResponseParser.getErrorCommand(json)
        }


        else -> {
            null
        }
    }
}