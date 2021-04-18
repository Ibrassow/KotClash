package com.example.kotclash


class Submarine(enemy: Boolean,
                coordinates : Pair<Float,Float>,
                gameManager: GameManager, currentOrientation: Float,
) : Troop(enemy, coordinates, gameManager, currentOrientation) {    //open var view:GameObjectView,

    override val freqShoot = 0f
    override val damage = 0
    override var health = 0
    override val speed = 0f
}