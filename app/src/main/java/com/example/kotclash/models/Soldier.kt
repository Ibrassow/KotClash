package com.example.kotclash.models

import android.util.Log

class Soldier(enemy: Boolean,
coordinates : Pair<Float,Float>
) : Troop(enemy, coordinates) {
    override var type = "soldier"
    override val freqShoot = 1000f
    override val damage = 50
    override var health = 500
    override val speed = 1f

    init {
        //Log.e("soldier", "CREATED")
    }
}