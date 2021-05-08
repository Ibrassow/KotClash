package com.example.kotclash.models

class Tankblue(enemy: Boolean,
               coordinates : Pair<Float,Float>
    ) : Troop(enemy, coordinates) {
        override var type = "tankblue"
        override val freqShoot = 1000f
        override val damage = 150
        override var health = 500
        override val speed = 1f
    }