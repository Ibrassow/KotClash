package com.example.kotclash


class Base(enemy: Boolean,
           coordinates : Pair<Float,Float>,
           gameManager: GameManager,
           currentOrientation: Float
) : Tower(enemy, coordinates, gameManager, currentOrientation) {    //open var view:GameObjectView,

    override val freqShoot = 0f
    override val damage = 0
    override var health = 0

    override fun getDamaged(dmg: Int) {
        super.getDamaged(dmg)
        if (dead && enemy){
            gameManager.setGameOver(true)
        }
    }

}