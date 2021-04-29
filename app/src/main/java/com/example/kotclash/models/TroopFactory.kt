package com.example.kotclash.models

class TroopFactory(val game: GameManager) {

    fun getTroop(enemy : Boolean,
                 type : String,
                 coordinates: Pair<Float,Float>,
                 target : Entity? = null): GameObject {

        lateinit var troopSelect : GameObject

        //TODO : gameObjectFactory
        when (type){
            "base" -> troopSelect = Base(enemy, coordinates, game)
            "simpleTower" -> troopSelect = SimpleTower(enemy, coordinates, game)
            "submarine" -> troopSelect = Submarine(enemy, coordinates, game)
            //"projectile" -> troopSelect = Projectile(enemy, target!!, coordinates, gameManager)
            //"boat" -> troopSelect = Boat(true, coordinates,  gameManager)

        }


        return troopSelect
    }

}