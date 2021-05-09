package com.example.kotclash.models


class Tankred(enemy: Boolean,
              coordinates : Pair<Float,Float>
) : Troop(enemy, coordinates) {
    override var type = "tankred"
    override val freqShoot = 1000f
    override val damage = 150
    override var health = 1000
    override val speed = 0.5f
}