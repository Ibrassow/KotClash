package com.example.kotclash.models

class Submarine(enemy: Boolean,
                coordinates : Pair<Float,Float>,
                gameManager: GameManager
) : Troop(enemy, coordinates, gameManager) {    //open var view:GameObjectView,
    override var type = "submarine"
    override val freqShoot = 0f
    override val damage = 0
    override var health = 0
    override val speed = 0.03f
}