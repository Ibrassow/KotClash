package com.example.kotclash.models

import android.util.Log


class CardManager(val troopFactory: TroopFactory, var game : GameManager) {



    val costBoat = 0f
    val costSubmarine = 0f


    //TODO: could also use CardManager for autonomousEnemyGeneration()
    fun playCard(cardNumber: Int, resources: Double, coordinates: Pair<Float,Float>){
        var i : Boolean = (resources > costSubmarine)
        Log.e("BOOL", "$i")
        when(cardNumber){
            1 -> {if(resources > costSubmarine){game.gameObjectList.add(troopFactory.getTroop(false,"submarine", coordinates))}
            Log.e("cardM", "please")
                val nn = game.gameObjectList.size
                Log.e("sizeObjListCM", "$nn")}
            2 -> {if(resources > costSubmarine){game.gameObjectList.add(troopFactory.getTroop(false,"submarine", coordinates))}
                Log.e("cardM", "please")}
            3 -> {if(resources > costSubmarine){game.gameObjectList.add(troopFactory.getTroop(false,"submarine", coordinates))}
                Log.e("cardM", "please")}
        }
    }


}