package de.jensklingenberg.showdown.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration




val list3: List<String> = listOf("Hund", "Katze", "Maus", "Bär", "Tiger")
@Serializable
open class VoteOptions(open val options: List<String>)
class Fibo : VoteOptions(listOf("0", "1", "2", "3", "5", "8", "13", "21", "34", "55", "89", "?"))

class TShirt : VoteOptions(listOf("xxs", "xs", "s", "m", "l", "xl", "xxl", "?"))

class Custom(override val options: List<String> = list3) : VoteOptions(options)


data class JoinGame(val playerName: String, val roomPassword: String)



class ChangeConfig(val clientGameConfig: ClientGameConfig)

