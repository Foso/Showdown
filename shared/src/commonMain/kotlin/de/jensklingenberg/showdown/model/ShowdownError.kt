package de.jensklingenberg.showdown.model

import kotlinx.serialization.Serializable

@Serializable
sealed class ShowdownError(var message: String)  {

    @Serializable
    class NotAuthorizedError : ShowdownError("NotAuthorizedError")

    @Serializable
    class NoConnectionError : ShowdownError("NotAuthorizedError")
}