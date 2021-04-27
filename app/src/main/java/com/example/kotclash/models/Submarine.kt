package com.example.kotclash.models

class Submarine(enemy: Boolean,
                coordinates : Pair<Float,Float>
) : Troop(enemy, coordinates) {    //open var view:GameObjectView,
    override var type = "submarine"
    override val freqShoot = 0f
    override val damage = 0
    override var health = 0
    override val speed = 1f
}