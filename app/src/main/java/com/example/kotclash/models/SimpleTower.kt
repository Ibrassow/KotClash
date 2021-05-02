package com.example.kotclash.models


class SimpleTower(enemy: Boolean,
                  coordinates : Pair<Float,Float>
) : Tower(enemy, coordinates) {

    override var type = "simpleTower"
    override val freqShoot = 100f
    override val damage = 10
    override var health = 50

    override fun getDamaged(dmg: Int) {
        super.getDamaged(dmg)
        if(dead){
            game.map.updateGates(this)
        }
    }
}