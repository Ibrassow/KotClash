package com.example.kotclash.models

class Base(enemy: Boolean, coordinates : Pair<Float,Float>
) : Tower(enemy, coordinates) {

    val gameManager = GameManager.gameInstance


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