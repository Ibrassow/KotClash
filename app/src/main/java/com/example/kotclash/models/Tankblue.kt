package com.example.kotclash.models

import android.util.Log

class Tankblue(enemy: Boolean,
               coordinates : Pair<Float,Float>
    ) : Troop(enemy, coordinates) {
        override var type = "tankblue"
        override val freqShoot = 100f
        override val damage = 150
        override var health = 500
        override val speed = 1f

        init {
            Log.e("Tankblue", "CREATED")
        }
    }