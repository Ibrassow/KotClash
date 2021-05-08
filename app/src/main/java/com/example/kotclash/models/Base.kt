package com.example.kotclash.models

import android.util.Log

class Base(enemy: Boolean,
           coordinates : Pair<Float,Float>
) : Tower(enemy, coordinates) {

    private val gameManager = GameManager.gameInstance

    override var type = "base"

    override var size = Pair(5f,3f)

    override val freqShoot = 1000f
    override val damage = 10
    override var health = 300

    override fun getDamaged(dmg: Int) {
        super.getDamaged(dmg)
        Log.e("BASE", "They damaged the base : $health PV left")
        if (dead && enemy){
            Log.e("BASE", "IM DEAD")
            gameManager.setGameOver(true)
        }
        else if (dead && !enemy){
            gameManager.setGameOver(false)
        }

    }

}