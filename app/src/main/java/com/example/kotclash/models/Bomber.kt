package com.example.kotclash.models

import android.util.Log

class Bomber(enemy: Boolean,
           coordinates : Pair<Float,Float>
) : Troop(enemy, coordinates) {
    override var type = "bomber"
    override val freqShoot = 1000f
    override val damage = 200
    override var health = 1000
    override val speed = 1f
    //override val price = 100f
    init {
        Log.e("bomber", "CREATED")
    }
}