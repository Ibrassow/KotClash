package com.example.kotclash.models

import android.util.Log

class Soldier(enemy: Boolean,
coordinates : Pair<Float,Float>
) : Troop(enemy, coordinates) {
    override var type = "soldier"
    override val freqShoot = 100f
    override val damage = 100
    override var health = 500
    override val speed = 0.1f
    init {
        Log.e("soldier", "CREATED")
    }
}