package com.example.kotclash.models

import com.example.kotclash.Map
import com.example.kotclash.GameManager


class CardManager(private val troopFactory: TroopFactory, val game: GameManager) {

    //TODO: could call parameter in class instead -> better
    private val costBoat = 0f
    private val costSubmarine = 0f


    //TODO: could also use CardManager for autonomousEnemyGeneration()
    fun playCard(cardNumber: Int, resources: Float, coordinates: Pair<Float,Float>){
        when(cardNumber){
            1 -> if(resources > costBoat){
                game.gameObjectList.add(troopFactory.getTroop(false,"boat",null, coordinates, 0f))
                game.resourceBar.useResources(costBoat)
                }
            2 -> if(resources > costSubmarine){
                game.gameObjectList.add(troopFactory.getTroop(false,"submarine",null, coordinates, 0f))
                game.resourceBar.useResources(costSubmarine)
                }
        }
    }
}