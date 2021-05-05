package com.example.kotclash.models

class TroopFactory(val game : GameManager) {

    fun getTroop(enemy : Boolean,
                 type : String,
                 coordinates: Pair<Float,Float>,
                 target : Entity? = null,
                 thrower : Entity? = null
    ): GameObject {

        lateinit var troopSelect : GameObject


        when (type){
            "base" -> troopSelect = Base(enemy, coordinates)
            "simpleTower" -> troopSelect = SimpleTower(enemy, coordinates)
            "tankred" -> troopSelect = Tankred(enemy, coordinates)
            "tankblue" -> troopSelect = Tankblue(enemy, coordinates)
            "tankgreen" -> troopSelect = Tankgreen(enemy, coordinates)
            "bomber" -> troopSelect = Bomber(enemy, coordinates)
            "soldier" -> troopSelect = Soldier(enemy, coordinates)
            "projectile" -> troopSelect = Projectile(enemy, target!!, coordinates, thrower!!.damage)

        }


        return troopSelect
    }

}