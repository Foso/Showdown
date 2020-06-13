package de.jensklingenberg.showdown.model

import kotlinx.serialization.Serializable

interface Element

class Background : Element

@Serializable
data class Warrior(val owner: Player,  val weaponRevealed: Boolean = false) :Element