package de.jensklingenberg.showdown.model

import kotlinx.serialization.Serializable


class Request(val path: String, val body: String = "")

class Response(val path: String, val body: String = "")

fun Request.toJson():String{
  return "{\"path\":\"$path\",\"body\":\"{\\\"_playerName_0\\\":\\\"User541\\\",\\\"_roomPassword\\\":\\\"\\\"}\"}"
}