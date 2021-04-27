package com.example.kotclash.models

class TroopFactory(val gameManager: GameManager) {

    lateinit var troopSelect : GameObject


    fun getTroop(enemy : Boolean,
                 type : String,
                 coordinates: Pair<Float,Float>,
                 target : Entity? = null): GameObject {

        //TODO : gameObjectFactory
        when (type){
            "base" -> troopSelect = Base(enemy, coordinates, gameManager)
            "simpleTower" -> troopSelect = SimpleTower(enemy, coordinates, gameManager)
            "submarine" -> troopSelect = Submarine(enemy, coordinates, gameManager)
            //"projectile" -> troopSelect = Projectile(enemy, target!!, coordinates, gameManager)
            //"boat" -> troopSelect = Boat(true, coordinates,  gameManager)



            //TODO : redefine missile for each entity - mdr
        }

        return troopSelect

    }

}