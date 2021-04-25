package com.example.kotclash.models

import com.example.kotclash.Map
import com.example.kotclash.GameManager


class CardManager(val troopFactory: TroopFactory, val gameManager: GameManager) {

    val costBoat = 0f
    val costSubmarine = 0f


    //TODO: could also use CardManager for autonomousEnemyGeneration()
    fun playCard(cardNumber: Int, resources: Float, coordinates: Pair<Float,Float>){
        when(cardNumber){
            1 -> if(resources > costBoat){gameManager.gameObjectList.
            add(troopFactory.getTroop(false,"boat",null, coordinates, 0f))}
            2 -> if(resources > costSubmarine){gameManager.gameObjectList.
            add(troopFactory.getTroop(false,"submarine",null, coordinates, 0f))}
        }
    }
}