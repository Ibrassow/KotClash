package com.example.kotclash.models


class SimpleTower(enemy: Boolean,
                  coordinates : Pair<Float,Float>
) : Tower(enemy, coordinates) {

    override var type = "simpleTower"
    override val freqShoot = 0f
    override val damage = 0
    override var health = 0
}