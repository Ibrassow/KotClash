package com.example.kotclash.models

class Tankgreen(enemy: Boolean,
               coordinates : Pair<Float,Float>
    ) : Troop(enemy, coordinates) {
        override var type = "tankgreen"
        override val freqShoot = 2000f
        override val damage = 400
        override var health = 500
        override val speed = 1f
    }