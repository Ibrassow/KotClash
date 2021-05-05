package com.example.kotclash.models

import kotlin.math.floor


class CardManager(private val troopFactory: TroopFactory, var game : GameManager) {
    val costTank: Int = 20
    val costSoldier : Int = 10
    val costBomber : Int = 40

    //TODO: could also use CardManager for autonomousEnemyGeneration()
    fun playCard(cardName: String, coordinates: Pair<Float,Float>){
        val resources = floor(game.resources)

        when(cardName){
            "tankred" -> {
                if(resources > costTank){game.gameObjectList.add(troopFactory.getTroop(false,"tankred", coordinates))
                game.useResource(costTank)}
            }
            "tankblue" -> {
                if(resources > costTank){game.gameObjectList.add(troopFactory.getTroop(false,"tankblue", coordinates))
                game.useResource(costTank)}
            }
            "tankgreen" -> {
                if(resources > costTank){game.gameObjectList.add(troopFactory.getTroop(false,"tankgreen", coordinates))
                game.useResource(costTank)}
            }
            "bomber" -> {
                if(resources > costBomber){game.gameObjectList.add(troopFactory.getTroop(false,"bomber", coordinates))
                game.useResource(costBomber)} }

            "soldier" -> {
                if(resources > costSoldier){game.gameObjectList.add(troopFactory.getTroop(false,"soldier", coordinates))
                game.useResource(costSoldier)}
            }

        }
    }


    fun isAvailable(nmCard : String): Boolean{

        var check : Boolean = false

        when (nmCard){
            "tankred", "tankblue", "tankgreen" -> check = game.resources > costTank
            "soldier" -> check = game.resources > costSoldier
            "bomber" -> check = game.resources > costBomber
        }

        return check
    }


}