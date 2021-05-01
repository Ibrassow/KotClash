package com.example.kotclash.models

import android.util.Log
import kotlin.math.*


open class Troop(enemy: Boolean,
                 coordinates : Pair<Float,Float>
) : Entity(enemy, coordinates), Movable{

    open val speed  = 35f
    lateinit var lookAheadPoint: Pair<Float,Float>

    var game = GameManager.gameInstance


    override fun takeAction(ElapsedTimeMS: Long, map: Map) {
        if (readyForAttack()) {
            target = selectTarget(map)
            if (target != null) {
                attack(target!!)
                previousAttackTime = System.currentTimeMillis()
            }else{
                move(ElapsedTimeMS,map)
            }
        }else{
            move(ElapsedTimeMS,map)
        }
    }



    fun move(interval : Long, map: Map) {

        if (onOwnSide()) {
            lookAheadPoint = getClosestGate(map.posGate)
        }else {
            lookAheadPoint = findTargetOfMotion()
        }

        currentOrientation = getAngleVector(coordinates,lookAheadPoint)

        val previousCoordinates = coordinates
        val dx = speed * interval * cos(currentOrientation)
        val dy = speed * interval * sin(currentOrientation)

        //update x & y in model
        coordinates = Pair(coordinates.first + dx, coordinates.second + dy)

        //used to update view
        rectF.offset(dx, dy)
        /*Log.e("EE", "dx : $dx, dy : $dy")
        Log.e("RR", "prevCoord : $previousCoordinates")
        Log.e("RR", "coord : $coordinates")*/



        map.displace(this,previousCoordinates)

    }






    private fun findTargetOfMotion():Pair<Float,Float>{
        if(target == null){
            if(isEnemy()) {
                target = getClosestEnemy(game.allyTowersList)
            }else{
                target = getClosestEnemy(game.enemyTowersList)
            }
        }
        val targetCoord = target!!.coordinates
        return targetCoord
    }


    fun onOwnSide():Boolean{
        var onOwnSide = false
        if(coordinates.second < 10*oldRendH && isEnemy()
                || coordinates.second > 10*oldRendH && !isEnemy()){
            onOwnSide = true
        }
        return onOwnSide
    }


    fun getClosestGate(posGate: MutableMap<Int, Pair<Float, Float>>): Pair<Float, Float>{
        //TODO list - more gates
        val gate1 = posGate[0]!!
        val gate2 = posGate[1]!!

        val dist1 = sqrt((coordinates.first - gate1.first).pow(2) + (coordinates.second - gate1.second).pow(2))
        val dist2 = sqrt((coordinates.first - gate2.first).pow(2) + (coordinates.second - gate2.second).pow(2))

        lateinit var gateChoice : Pair<Float, Float>
        if(dist1 < dist2){
            gateChoice = gate1
        } else if (dist1 > dist2){
            gateChoice =  gate2
        }
        else {
            //TODO Handle the case when distances are equal
        }

        return gateChoice
    }

}