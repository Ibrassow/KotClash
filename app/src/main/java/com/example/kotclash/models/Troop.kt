package com.example.kotclash.models

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
        //onOwnSide()
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
        Log.e("RR", "prevCoord : $previousCoordinates")
        Log.e("RR", "coord : $coordinates")*/

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


    fun onOwnSide():Boolean{
        var onOwnSide = false

        //TODO Take into account the different maps
        if((coordinates.second <= 11*oldRendH && isEnemy())
                || (coordinates.second > 11*oldRendH && !isEnemy())){
            onOwnSide = true}

        return onOwnSide
    }




}