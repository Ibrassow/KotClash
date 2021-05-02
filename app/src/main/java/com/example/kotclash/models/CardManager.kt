package com.example.kotclash.models

import android.util.Log


class CardManager(private val troopFactory: TroopFactory, var game : GameManager) {

    val costBoat = 0f
    val costTank = 0f
    lateinit var list: Any
    lateinit var troopsCard : Any

    //TODO: could also use CardManager for autonomousEnemyGeneration()
    fun playCard(cardNumber: Int, resources: Double, coordinates: Pair<Float,Float>){
        val i : Boolean = (resources > costTank)

        Log.e("BOOL", "$i")

        when(cardNumber){
            1 -> {if(resources > costTank){game.gameObjectList.add(troopFactory.getTroop(false,"tank", coordinates))
                game.useResource(10)}
                val nn = game.gameObjectList.size
                Log.e("sizeObjListCM", "$nn")}

            2 -> {if(resources > costTank){game.gameObjectList.add(troopFactory.getTroop(false,"tankblue", coordinates))
                game.useResource(10)} }

            3 -> {if(resources > costTank){game.gameObjectList.add(troopFactory.getTroop(false,"tank", coordinates))
                game.useResource(10)} }
        }
    }



}