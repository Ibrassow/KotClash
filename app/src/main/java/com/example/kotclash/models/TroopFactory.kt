package com.example.kotclash.models

class TroopFactory(val gameManager: GameManager) {

    lateinit var troopSelect : GameObject


    fun getTroop(enemy : Boolean,
                 type : String,
                 target : Entity?,
                 coordinates: Pair<Float,Float>,
                 currentOrientation: Float): GameObject {

        //TODO : gameObjectFactory
        when (type){
            "base" -> troopSelect = Base(enemy, coordinates, currentOrientation, gameManager)
            "submarine" -> troopSelect = Submarine(enemy, coordinates, currentOrientation, gameManager)
            //"projectile" -> troopSelect = Projectile(enemy, target!!, coordinates, currentOrientation, gameManager)
            //"boat" -> troopSelect = Boat(true, coordinates, currentOrientation, gameManager)
            "simpleTower" -> troopSelect = SimpleTower(enemy, coordinates, currentOrientation, gameManager)


            //TODO : redefine missile for each entity - mdr
        }

        return troopSelect

    }

}