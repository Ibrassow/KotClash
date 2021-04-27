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

    override fun takeAction(ElapsedTimeMS: Long, grid: Map){
       /* if(target != null){
            //target = selectTarget(grid)
            if(readyForAttack() ) {  //ARTIFICE EN PRINCIPE TEMPORAIRE
                attack(target!!)
            }
        }else{
            move(ElapsedTimeMS)
        }*/

        move(ElapsedTimeMS)
    }


    /*fun move(interval : Double){
        var dy = 0f
        if (enemy) {
            dy += (speed * interval).toFloat()
        }
        else{
            dy -= (speed * interval).toFloat()
        }
        r.offset(0f, dy)
        //println(r.top)
    }*/


    fun move(interval : Long){

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

        val coordinatesIdx = Pair(ceil(coordinates.first),ceil(coordinates.second))


        if(!(ceil(coordinates.first) == ceil(previousCoordinates.first)
                        && ceil(coordinates.second) == ceil(previousCoordinates.second))){
            //grid.displace(this,coordinatesIdx, currentOrientation)

    }




    /*fun move(interval : Long){

        //lookAheadPoint=mapLoader.posBases["enemy"]!!
        if(onOwnSide()){
            lookAheadPoint = getClosestGate()
        }else{
            targetOfMotion = findTargetOfMotion()
            lookAheadPoint = targetOfMotion!!.coordinates
        } *///TODO

        //val currentOrientation = getAngleVector(Pair(coordinates.first, coordinates.second),
                //Pair(lookAheadPoint.first, lookAheadPoint.second))
        /*TODO var previousCoordinates = coordinates
        Log.wtf("temps", previousCoordinates.toString())
        /*coordinates = Pair(coordinates.first + speed*interval*cos(currentOrientation),
                coordinates.second + speed*interval*sin(currentOrientation))*/
        if (previousCoordinates.second <=5){coordinates = Pair(previousCoordinates.first- speed,previousCoordinates.second )
            currentOrientation = -4f
            Log.wtf("temps", coordinates.toString())
        }else if (1 <= previousCoordinates.second && previousCoordinates.second<5){coordinates = Pair(previousCoordinates.first- speed,previousCoordinates.second -speed )
            currentOrientation = 3f
            Log.wtf("temps", coordinates.toString())
        }
            else{coordinates = Pair(previousCoordinates.first,previousCoordinates.second - speed)
            currentOrientation = -2f
        Log.wtf("temps", coordinates.toString())}*/

        //val coordinatesIdx = Pair(ceil(coordinates.first),ceil(coordinates.second))

        /*if(!(ceil(coordinates.first) == ceil(previousCoordinates.first)
                        && ceil(coordinates.second) == ceil(previousCoordinates.second))){
            //grid.displace(this,coordinatesIdx, currentOrientation)
            //TODO
        }
    }*/
    /*private fun move(interval : Long){
        lateinit var lookAheadPoint : Pair<Float,Float>
        /*if(onOwnSide()){
            lookAheadPoint = getClosestGate()
        }else{
            targetOfMotion = findTargetOfMotion()
            lookAheadPoint = targetOfMotion!!.coordinates
        }*/

        val currentOrientation = getAngleVector(Pair(coordinates.first, coordinates.second),
                Pair(lookAheadPoint.first, lookAheadPoint.second))

        val previousCoordinates = coordinates
        val dx = speed*interval*cos(currentOrientation)
        val dy = speed*interval*sin(currentOrientation)

        //update x & y in model
        coordinates = Pair(coordinates.first + dx, coordinates.second + dy)

        //used to update view
        rectF.offset(dx,dy)

        val coordinatesIdx = Pair(ceil(coordinates.first),ceil(coordinates.second))


        if(!(ceil(coordinates.first) == ceil(previousCoordinates.first)
                        && ceil(coordinates.second) == ceil(previousCoordinates.second))){
            //grid.displace(this,coordinatesIdx, currentOrientation)
            //TODO
        }

    }*/


    fun findTargetOfMotion():Entity?{
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