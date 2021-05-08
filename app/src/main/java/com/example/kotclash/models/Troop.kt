package com.example.kotclash.models

import kotlin.math.*


open class Troop(enemy: Boolean,
                 coordinates : Pair<Float,Float>
) : Entity(enemy, coordinates), Movable{

    open val speed  = 5f
    lateinit var lookAheadPoint: Pair<Float,Float>


    override fun takeAction(elapsedTimeMS: Long, map: Map){
        target = selectTarget(map)
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



    private fun move(interval : Long, map: Map) {
        if (map.onOwnSide(this)) {
            lookAheadPoint = map.getClosestGate(this)!!
        }else {
            lookAheadPoint = findTargetOfMotion()
        }

        currentOrientation = getAngleVector(coordinates,lookAheadPoint)

        val previousCoordinates = coordinates
        val dx = speed * interval * cos(currentOrientation)
        val dy = speed * interval * -sin(currentOrientation)

        //update x & y in model
        coordinates = Pair(coordinates.first + dx, coordinates.second + dy)

        //used to update view
        rectF.offset(dx, dy)

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