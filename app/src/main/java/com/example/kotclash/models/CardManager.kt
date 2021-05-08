package com.example.kotclash.models

import kotlin.math.floor


class CardManager(var game : GameManager) {
    private val costTank: Int = 20
    private val costSoldier : Int = 5
    private val costBomber : Int = 40


    fun playCard(cardName: String, side: Int){
        val resources = floor(game.resources)

        when(cardName){
            "tankred" -> {
                if(resources > costTank){
                    game.addTroop(false, "tankred", side)
                    game.useResource(costTank)}
            }
            "tankblue" -> {
                if(resources > costTank){game.addTroop(false, "tankblue", side)
                game.useResource(costTank)}
            }
            "tankgreen" -> {
                if(resources > costTank){game.addTroop(false, "tankgreen", side)
                game.useResource(costTank)}
            }
            "bomber" -> {
                if(resources > costBomber){game.addTroop(false, "bomber", side)
                game.useResource(costBomber)} }

            "soldier" -> {
                if(resources > costSoldier){game.addTroop(false, "soldier", side)
                game.useResource(costSoldier)}
            }

        }
    }


    fun isAvailable(nmCard : String): Boolean{

        var check = false

        when (nmCard){
            "tankred", "tankblue", "tankgreen" -> check = game.resources > costTank
            "soldier" -> check = game.resources > costSoldier
            "bomber" -> check = game.resources > costBomber
        }

        return check
    }


}