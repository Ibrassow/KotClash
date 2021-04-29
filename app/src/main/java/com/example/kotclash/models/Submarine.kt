package com.example.kotclash.models

import android.util.Log

class Submarine(enemy: Boolean,
                coordinates : Pair<Float,Float>,
                game: GameManager
) : Troop(enemy, coordinates, game) {
    override var type = "submarine"
    override val freqShoot = 1000f
    override val damage = 20
    override var health = 50
    override val speed = 1f
    init {
        Log.e("Submarine", "CREATED")
    }
}