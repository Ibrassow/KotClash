package com.example.kotclash.models

class TroopFactory(val game : GameManager) {

    fun getTroop(enemy : Boolean,
                 type : String,
                 coordinates: Pair<Float,Float>,
                 target : Entity? = null): GameObject {

        lateinit var troopSelect : GameObject

        //TODO if enemy -> coordinates


        when (type){
            "base" -> troopSelect = Base(enemy, coordinates)
            "simpleTower" -> troopSelect = SimpleTower(enemy, coordinates)
            "tank" -> troopSelect = Tank(enemy, coordinates)
            //"projectile" -> troopSelect = Projectile(enemy, target!!, coordinates)
            "boat" -> troopSelect = Boat(enemy, coordinates)

        }


        return troopSelect
    }

}