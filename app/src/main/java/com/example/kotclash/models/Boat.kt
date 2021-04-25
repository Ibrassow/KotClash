package com.example.kotclash.models
import com.example.kotclash.controllers.GameManager


class Boat(enemy: Boolean,
           coordinates : Pair<Float,Float>,
           currentOrientation: Float, gameManager: GameManager
) : Troop(enemy, coordinates, currentOrientation, gameManager) {
    override var type = "boat"
    override val freqShoot = 0f
    override val damage = 0
    override var health = 0
    override val speed = 0f
}