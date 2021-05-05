package com.example.kotclash.models


class SimpleTower(enemy: Boolean,
                  coordinates : Pair<Float,Float>
) : Tower(enemy, coordinates) {

    override var type = "simpleTower"
    override val freqShoot = 1000f
    override val damage = 200
    override var health = 2000
}