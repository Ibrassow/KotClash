package com.example.kotclash.models

import android.util.Log
import kotlin.math.*


open class Troop(enemy: Boolean,
                 coordinates : Pair<Float,Float>
) : Entity(enemy, coordinates), Movable{

    open val speed  = 5f
    lateinit var lookAheadPoint: Pair<Float,Float>


    override fun takeAction(elapsedTimeMS: Long, map: Map) {
        target = selectTarget(map)
        //Log.e("target","$target")
        if (target != null) {
            if(readyForAttack()) {
                attack(target!!)
                previousAttackTime = System.currentTimeMillis()
            }
        }else{
            move(elapsedTimeMS,map)
        }
        //move(elapsedTimeMS,map)
    }



    fun move(interval : Long, map: Map) {

        if (onOwnSide()) {
            lookAheadPoint = getClosestGate(map.posGate)
        }else {
            lookAheadPoint = findTargetOfMotion()
            //Log.e("lookAhead","target:$lookAheadPoint")
        }

        currentOrientation = getAngleVector(coordinates,lookAheadPoint)
        Log.e("orientation","$this orientation = $currentOrientation")

        val previousCoordinates = coordinates
        val dx = speed * interval * cos(currentOrientation)
        val dy = speed * interval * -sin(currentOrientation)

        //update x & y in model
        coordinates = Pair(coordinates.first + dx, coordinates.second + dy)

        //used to update view
        rectF.offset(dx, dy)
        /*Log.e("EE", "dx : $dx, dy : $dy")
        Log.e("RR", "prevCoord : $previousCoordinates")
        Log.e("RR", "coord : $coordinates")*/

        map.displace(this, previousCoordinates)

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

        if((coordinates.second <= 11*oldRendH && isEnemy())
                || (coordinates.second > 11*oldRendH && !isEnemy())){
            onOwnSide = true}

        return onOwnSide
    }


    fun getClosestGate(posGate: MutableMap<Int, Pair<Float, Float>>): Pair<Float, Float>{
        //TODO list - more gates
        val gate1 = posGate[0]!!
        val gate2 = posGate[1]!!

        val dist1 = sqrt((coordinates.first - gate1.first).pow(2) + (coordinates.second - gate1.second).pow(2))
        val dist2 = sqrt((coordinates.first - gate2.first).pow(2) + (coordinates.second - gate2.second).pow(2))

        lateinit var gateChoice : Pair<Float, Float>
        if(dist1 <= dist2){
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