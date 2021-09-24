package de.jensklingenberg.showdown.model

import kotlinx.serialization.Serializable

@Serializable
data class Request(val path: String, val body: String = "")
typealias Response = Request
