package com.example.kotclash.models

class Soldier(enemy: Boolean,
coordinates : Pair<Float,Float>
) : Troop(enemy, coordinates) {
    override var type = "soldier"
    override val freqShoot = 1000f
    override val damage = 30
    override var health = 150
    override val speed = 1f

}