package de.jensklingenberg.showdown.model


val fibo = (listOf("0", "1", "2", "3", "5", "8", "13", "21", "34", "55", "89", "?"))
val modFibo = (listOf("0", "1/2", "1", "2", "3", "5", "8", "13", "20", "40", "100", "?"))
val powerOf2 = (listOf("0", "1", "2", "4", "8", "16", "32", "64", "?"))

val tshirtList = (listOf("xxs", "xs", "s", "m", "l", "xl", "xxl", "?"))

data class JoinGame(val playerName: String, val roomPassword: String)

