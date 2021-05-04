package com.example.kotclash.models

import android.util.Log

class Bomber(enemy: Boolean,
           coordinates : Pair<Float,Float>
) : Troop(enemy, coordinates) {
    override var type = "bomber"
    override val freqShoot = 1000f
    override val damage = 1000
    override var health = 1000
    override val speed = 0.1f

    init {
        Log.e("bomber", "CREATED")
    }
}