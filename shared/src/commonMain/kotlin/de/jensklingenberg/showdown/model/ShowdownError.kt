package de.jensklingenberg.showdown.model

import kotlinx.serialization.Serializable

@Serializable
enum class ShowdownError(var message: String) {
    NotAuthorizedError("NotAuthorizedError"),
    NoConnectionError("NotAuthorizedError")
}