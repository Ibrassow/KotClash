package com.example.kotclash.models


class SimpleTower(enemy: Boolean,
                  coordinates : Pair<Float,Float>,
                  gameManager: GameManager
) : Tower(enemy, coordinates, gameManager) {    //open var view:GameObjectView,

    override var type = "simpleTower"
    override val freqShoot = 0f
    override val damage = 0
    override var health = 0
}