package com.example.kotclash.models

import android.util.Log

class Tankred(enemy: Boolean,
              coordinates : Pair<Float,Float>
) : Troop(enemy, coordinates) {
    override var type = "tankred"
    override val freqShoot = 1000f
    override val damage = 200
    override var health = 500
    override val speed = 0.1f
    init {
        Log.e("Tankred", "CREATED")
    }
}