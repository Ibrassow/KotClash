package com.example.kotclash.models


class CardManager(private val troopFactory: TroopFactory, var game : GameManager) {
    var cost: Int=0

    //TODO: could also use CardManager for autonomousEnemyGeneration()
    fun playCard(cardName: String, resources: Double, coordinates: Pair<Float,Float>){
        when(cardName){
            "test1" -> {cost = 10 ;if(resources > cost){game.gameObjectList.add(troopFactory.getTroop(false,"tankred", coordinates))
                game.useResource(cost)} }
            "test2" -> {cost = 10 ;if(resources > cost){game.gameObjectList.add(troopFactory.getTroop(false,"tankblue", coordinates))
                game.useResource(cost)} }
            "test3" -> {cost = 10 ;if(resources > cost){game.gameObjectList.add(troopFactory.getTroop(false,"tankgreen", coordinates))
                game.useResource(cost)} }
            "test4" -> {cost = 10 ;if(resources > cost){game.gameObjectList.add(troopFactory.getTroop(false,"bomber", coordinates))
                game.useResource(cost)} }
            "test5" -> {cost = 10 ;if(resources > cost){game.gameObjectList.add(troopFactory.getTroop(false,"soldier", coordinates))
                game.useResource(cost)} }

        }
    }


}