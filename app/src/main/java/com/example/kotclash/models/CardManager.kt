package com.example.kotclash.models

import android.util.Log


class CardManager(val troopFactory: TroopFactory, var game : GameManager) {

    val costBoat = 0f
    val costTank = 0f
    lateinit var list: Any
    lateinit var troopsCard : Any

    //TODO: could also use CardManager for autonomousEnemyGeneration()
    fun playCard(cardNumber: Int, resources: Double, coordinates: Pair<Float,Float>){
        var i : Boolean = (resources > costTank)
        Log.e("BOOL", "$i")
        when(cardNumber){
            1 -> {if(resources > costTank){game.gameObjectList.add(troopFactory.getTroop(false,"tank", coordinates))
                //game.resourceBar.useResource(1)
                game.useResource(10)}
            Log.e("cardM", "please")
                val nn = game.gameObjectList.size
                Log.e("sizeObjListCM", "$nn")}
            2 -> {if(resources > costTank){game.gameObjectList.add(troopFactory.getTroop(false,"tank", coordinates))
                //game.resourceBar.useResource(1)
                game.useResource(10)}
                Log.e("cardM", "please")}
            3 -> {if(resources > costTank){game.gameObjectList.add(troopFactory.getTroop(false,"tank", coordinates))
                //game.resourceBar.useResource(1)
                game.useResource(10)}
                Log.e("cardM", "please")}
        }
    }



}