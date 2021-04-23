package com.example.kotclash.models

import com.example.kotclash.GameManager

class Base(enemy: Boolean, coordinates : Pair<Float,Float>, currentOrientation: Float, gameManager: GameManager
) : Tower(enemy, coordinates, currentOrientation, gameManager) {

    override var type = "base"

    override val freqShoot = 0f
    override val damage = 0
    override var health = 100

    override fun getDamaged(dmg: Int) {
        super.getDamaged(dmg)
        if (dead && enemy){
            gameManager.setGameOver(true)
        }
    }

}