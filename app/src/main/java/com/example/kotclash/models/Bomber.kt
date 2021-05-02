package com.example.kotclash.models

import android.util.Log

class Bombardier(enemy: Boolean,
           coordinates : Pair<Float,Float>
) : Troop(enemy, coordinates) {
    override var type = "bombardier"
    override val freqShoot = 1000f
    override val damage = 200
    override var health = 1000
    override val speed = 1f
    override val price = 100f
    init {
        Log.e("bombardier", "CREATED")
    }
}