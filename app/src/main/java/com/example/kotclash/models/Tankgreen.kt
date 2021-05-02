package com.example.kotclash.models

import android.util.Log

class Tankgreen(enemy: Boolean,
               coordinates : Pair<Float,Float>
    ) : Troop(enemy, coordinates) {
        override var type = "tank"
        override val freqShoot = 100f
        override val damage = 200
        override var health = 500
        override val speed = 1f
        //override val price = 10f
        init {
            Log.e("Tank", "CREATED")
        }
    }