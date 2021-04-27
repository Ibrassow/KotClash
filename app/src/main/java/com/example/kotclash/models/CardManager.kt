package com.example.kotclash.models


class CardManager(val troopFactory: TroopFactory, val gameManager: GameManager) {

    val costBoat = 0
    val costSubmarine = 0


    //TODO: could also use CardManager for autonomousEnemyGeneration()
    fun playCard(cardNumber: Int, resources: Double, coordinates: Pair<Float,Float>, grid: Map){
        when(cardNumber){
            1 -> if(resources > costBoat){gameManager.gameObjectList.
            add(troopFactory.getTroop(false,"boat",null, coordinates, 0f))}
            2 -> if(resources > costSubmarine){gameManager.gameObjectList.
            add(troopFactory.getTroop(false,"submarine",null, coordinates, 0f))}
        }
    }


}