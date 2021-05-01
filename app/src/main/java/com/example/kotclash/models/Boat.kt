package com.example.kotclash.models


class Boat(enemy: Boolean,
           coordinates : Pair<Float,Float>
) : Troop(enemy, coordinates) {
    override var type = "boat"
    override val freqShoot = 0f
    override val damage = 0
    override var health = 0
    override val speed = 0f
}