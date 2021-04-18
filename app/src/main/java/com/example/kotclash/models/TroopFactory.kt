package com.example.kotclash.models

import com.example.kotclash.GameManager

class TroopFactory(val gameManager: GameManager) {     //var view: GameObjectView

    lateinit var troopSelect : GameObject


    fun getTroop(enemy : Boolean,
                 type : String,
                 target : Entity?,
                 coordinates: Pair<Float,Float>,
                 currentOrientation: Float): GameObject {

        //TODO : gameObjectFactory
        when (type){
            //"submarine" -> troopSelect = Submarine(enemy, coordinates, gameManager, currentOrientation)
            "projectile" -> troopSelect = Projectile(enemy, target!!, coordinates, gameManager, currentOrientation)
            "boat" -> troopSelect = Boat(true, coordinates, gameManager, currentOrientation)
            //"simpleTower" -> troopSelect = SimpleTower(enemy, coordinates, gameManager, currentOrientation)
            "base" -> troopSelect = Base(enemy, coordinates, gameManager, currentOrientation)

            //TODO : redefine missile for each entity

        }

        return troopSelect

    }

}