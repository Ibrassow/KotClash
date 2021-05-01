package com.example.kotclash.models

import android.util.Log
import kotlin.math.*


open class Troop(enemy: Boolean,
                 coordinates : Pair<Float,Float>
) : Entity(enemy, coordinates), Movable{

    open val speed  = 35f
    var targetOfMotion: Entity? = null

    //serve to direct movement of troops
    val gate1 = Pair(0f,0f)
    val gate2 = Pair(0f,0f)

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



    fun move(interval : Long, map: Map){

        lateinit var lookAheadPoint : Pair<Float,Float>
        /*if(onOwnSide()){
            lookAheadPoint = getClosestGate()
        }else{
            targetOfMotion = findTargetOfMotion()
            lookAheadPoint = targetOfMotion!!.coordinates
        }*/

        /*val currentOrientation = getAngleVector(Pair(coordinates.first, coordinates.second),
                Pair(lookAheadPoint.first, lookAheadPoint.second))*/

        val currentOrientation = getAngleVector(Pair(coordinates.first, coordinates.second),
                Pair(0f, 0f))

        val previousCoordinates = coordinates
        val dx = speed*interval*cos(currentOrientation)
        val dy = speed*interval*sin(currentOrientation)

        //update x & y in model
        coordinates = Pair(coordinates.first + dx, coordinates.second + dy)

        //used to update view
        rectF.offset(dx,dy)
        Log.e("EE", "dx : $dx, dy : $dy")
        Log.e("RR", "coord : $coordinates")

        //val coordinatesIdx = Pair(ceil(coordinates.first),ceil(coordinates.second))


        if(!(ceil(coordinates.first) == ceil(previousCoordinates.first)
                        && ceil(coordinates.second) == ceil(previousCoordinates.second))){
            map.displace(this, previousCoordinates)

    }


        /*fun move2(interval : Long, map: Map) {

            val gate1 = Pair(4f*conversionW,10f*conversionH)
            val gate2 = Pair(14f*conversionW,10f*conversionH)

            if (onOwnSide()) {
                val nbGate = getClosestGate()
                if(nbGate == 1){
                    lookAheadPoint = gate1
                }else{
                    lookAheadPoint = gate2
                }
                //Log.e("LOOKAHEADPOINT","coord $lookAheadPoint")
            }else {
                //Log.e("TARGETOFMOTION","SUCCESS")
                lookAheadPoint = findTargetOfMotion()
                //Log.e("TARGETOFMOTION","SUCCESS2")
                //lookAheadPoint = targetOfMotion!!.coordinates
                //val test = lookAheadPoint.first
                Log.e("TARGETOFMOTION","SUCCESS2")
            }

            currentOrientation = getAngleVector(coordinates,lookAheadPoint)




            val previousCoordinates = coordinates
            val dx = speed * interval * cos(currentOrientation)
            val dy = speed * interval * sin(currentOrientation)

            //update x & y in model
            coordinates = Pair(coordinates.first + dx, coordinates.second + dy)

            //used to update view
            rectF.offset(dx, dy)
            Log.e("EE", "dx : $dx, dy : $dy")
            Log.e("RR", "prevCoord : $previousCoordinates")
            Log.e("RR", "coord : $coordinates")


            map.displace(this,previousCoordinates)

        }*/




    fun findTargetOfMotion():GameObject?{
        if(target == null){
            if(isEnemy()) {
                //target = getClosestEnemy(gameManager.enemyTowersList) //TODO pass gameManager in parameters not as attribute
            }else{
                //target = getClosestEnemy(gameManager.allyTowersList)
            }
        }
        return target
    }


    //TODO: with dimensions screen known
    /*fun onOwnSide():Boolean{
        var onOwnSide = false
        if(coordinates.second > screenHeight/2 && isEnemy()
                || coordinates.second < screenHeight/2 && !isEnemy()){
            onOwnSide = true
        }
        return onOwnSide
    }*/


    fun getClosestGate():Pair<Float,Float>{
        var closestGate = Pair(0f,0f)
        val dist1 = sqrt((coordinates.first - gate1.first).pow(2) + (coordinates.second - gate1.second).pow(2))
        val dist2 = sqrt((coordinates.first - gate2.first).pow(2) + (coordinates.second - gate2.second).pow(2))

        if(dist1 > dist2){
            closestGate = gate2
        }else {  //else => if dist1 == dist2 as well
            closestGate = gate1
        }

        return closestGate
    }

}}