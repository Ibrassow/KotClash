package com.example.kotclash.models

import android.util.Log
import kotlin.math.*


open class Troop(enemy: Boolean,
                 coordinates : Pair<Float,Float>
) : Entity(enemy, coordinates), Movable{

    open val speed  = 5f
    lateinit var lookAheadPoint: Pair<Float,Float>


    override fun takeAction(elapsedTimeMS: Long, map: Map){
        target = selectTarget(map)
        //val xCoord = ceil(coordinates.first/oldRendW)
        //val yCoord = ceil(coordinates.second/oldRendH)
        //Log.e("target","$this, $target, ($xCoord,$yCoord)")
        if (target != null) {
            currentOrientation = getAngleVector(coordinates,target!!.coordinates)
            if(readyForAttack()) {
                attack(target!!)
                previousAttackTime = System.currentTimeMillis()
            }
        }else{
            move(elapsedTimeMS,map)
        }
    }



    fun move(interval : Long, map: Map) {
        if (map.onOwnSide(this)) {
            lookAheadPoint = map.getClosestGate(this)!!
        }else {
            lookAheadPoint = findTargetOfMotion()
        }

        currentOrientation = getAngleVector(coordinates,lookAheadPoint)
        //Log.e("orientation","$this orientation = $currentOrientation")

        val previousCoordinates = coordinates
        val dx = speed * interval * cos(currentOrientation)
        val dy = speed * interval * -sin(currentOrientation)

        //update x & y in model
        coordinates = Pair(coordinates.first + dx, coordinates.second + dy)

        //used to update view
        rectF.offset(dx, dy)
        /*Log.e("EE", "dx : $dx, dy : $dy")
        Log.e("RR", "prevCoord : $previousCoordinates")*/
        val x = coordinates.first/oldRendW
        val y = coordinates.second/oldRendH
        //Log.e("TroopCoord", "coord : $x,$y")

        map.displace(this, previousCoordinates)

    }



    private fun findTargetOfMotion() : Pair<Float,Float>{
        if(target == null){
            if(isEnemy()) {
                target = getClosestEnemy(game.allyTowersList)
            }else{
                target = getClosestEnemy(game.enemyTowersList)
            }
        }
        return target!!.coordinates
    }





}