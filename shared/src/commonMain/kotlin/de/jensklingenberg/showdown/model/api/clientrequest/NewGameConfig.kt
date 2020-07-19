package de.jensklingenberg.showdown.model.api.clientrequest

import de.jensklingenberg.showdown.model.fibo

data class NewGameConfig(var voteOptions: List<String> = fibo, val autoReveal: Boolean = false)