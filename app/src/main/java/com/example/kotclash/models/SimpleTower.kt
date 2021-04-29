package com.example.kotclash.models


class SimpleTower(enemy: Boolean,
                  coordinates : Pair<Float,Float>,
                  game:GameManager
) : Tower(enemy, coordinates,game) {

    override var type = "simpleTower"
    override val freqShoot = 2000f
    override val damage = 10
    override var health = 50
}