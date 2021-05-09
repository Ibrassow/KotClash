package com.example.kotclash.models

import android.util.Log
import kotlin.math.cos
import kotlin.math.sin

class Bomber(enemy: Boolean,
           coordinates : Pair<Float,Float>
) : Troop(enemy, coordinates) {
    override var type = "bomber"
    override val freqShoot = 3000f
    override val damage = 1000
    override var health = 500
    override val speed = 1f

    init {
        Log.e("bomber", "CREATED")
    }
}