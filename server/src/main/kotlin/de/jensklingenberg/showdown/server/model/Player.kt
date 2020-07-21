package de.jensklingenberg.showdown.server.model

data class Player(val sessionId: String, val name: String = "Unnamed", val vote: Vote? = null)