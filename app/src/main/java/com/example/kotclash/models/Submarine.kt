package com.example.kotclash.models

import android.util.Log

class Submarine(enemy: Boolean,
                coordinates : Pair<Float,Float>
) : Troop(enemy, coordinates) {
    override var type = "submarine"
    override val freqShoot = 100f
    override val damage = 200
    override var health = 500
    override val speed = 1f
    init {
        Log.e("Submarine", "CREATED")
    }
}