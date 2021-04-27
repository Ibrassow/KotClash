package com.example.kotclash.models

class Base(enemy: Boolean, coordinates : Pair<Float,Float>, currentOrientation: Float, gameManager: GameManager
) : Tower(enemy, coordinates, currentOrientation, gameManager) {

    override var type = "base"

    override var size = Pair(5f,3f)

    override val freqShoot = 0f
    override val damage = 0
    override var health = 100

    override fun getDamaged(dmg: Int) {
        super.getDamaged(dmg)
        if (dead && enemy){
            //gameManager.setGameOver(true) //TODO
        }
    }

}