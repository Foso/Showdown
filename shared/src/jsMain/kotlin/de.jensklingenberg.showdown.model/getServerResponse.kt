package de.jensklingenberg.showdown.model


fun getServerResponseType(toString: String): ServerResponseTypes {

    return ServerResponseTypes.values().firstOrNull() {
        toString.startsWith("{\"id\":${it.ordinal}")
    } ?: ServerResponseTypes.UNKNOWN
}


fun getServerResponse(json: String): ServerResponse? {
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