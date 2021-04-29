package com.example.kotclash.models

import android.text.ParcelableSpan
import android.util.Log
import kotlin.math.*


open class Troop(enemy: Boolean,
                 coordinates : Pair<Float,Float>,
                 game: GameManager
) : Entity(enemy, coordinates, game), Movable{

    open val speed  = 35f
    var targetOfMotion: GameObject? = null

    lateinit var lookAheadPoint: Pair<Float,Float>

    /*override fun takeAction(ElapsedTimeMS: Long, map: Map) {

        if (readyForAttack()) {
            if (target != null) {
                attack(target!!)
            } else {
                target = selectTarget(map)
                if (target != null) {
                    attack(target!!)
                }else{
                    move(ElapsedTimeMS,map)
                }
            }
        } else {
            move(ElapsedTimeMS, map)
        }
    }*/

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
        //move(ElapsedTimeMS,map)
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


    fun move(interval : Long, map: Map) {

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


        //val lookAheadPoint = gate2

        /*if(isEnemy()){
            //Log.e("Isenemy", "enemy : $enemy")
            currentOrientation = getAngleVector(coordinates, lookAheadPoint)
        }else{
            currentOrientation = getAngleVector(coordinates,lookAheadPoint)
        }*/


        currentOrientation = getAngleVector(coordinates,lookAheadPoint)


        /*currentOrientation = getAngleVector(Pair(coordinates.first, coordinates.second),
                Pair(lookAheadPoint.first, lookAheadPoint.second))*/


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

        //val coordinatesIdx = Pair(ceil(coordinates.first),ceil(coordinates.second))


        //This part useless as expressed in pixels and not squares
        /*if (ceil(coordinates.first) != ceil(previousCoordinates.first)
                || ceil(coordinates.second) != ceil(previousCoordinates.second)) {
            map.displace(this, previousCoordinates)
        }*/

        map.displace(this,previousCoordinates)

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


    private fun findTargetOfMotion():Pair<Float,Float>{
        if(target == null){
            if(isEnemy()) {
                target = getClosestEnemy(game.allyTowersList)
            }else{
                target = getClosestEnemy(game.enemyTowersList)
            }
        }
        val targetCoord = target!!.coordinates
        Log.e("TARGET","TARGETCOORD $targetCoord")
        return targetCoord
    }


    fun onOwnSide():Boolean{
        var onOwnSide = false
        if(coordinates.second < 10*conversionH && isEnemy()
                || coordinates.second > 10*conversionH && !isEnemy()){
            onOwnSide = true
        }
        return onOwnSide
    }


    fun getClosestGate():Int{
        val gate1 = Pair(4f*conversionW,10f*conversionH)
        val gate2 = Pair(14f*conversionW,10f*conversionH)
        val dist1 = sqrt((coordinates.first - gate1.first).pow(2) + (coordinates.second - gate1.second).pow(2))
        val dist2 = sqrt((coordinates.first - gate2.first).pow(2) + (coordinates.second - gate2.second).pow(2))
        var nbGate :Int

        if(dist1 > dist2){
            nbGate = 2
        }else {  //else => if dist1 == dist2 as well
            nbGate = 1
        }
        Log.e("nbGate","nbGate $nbGate")
        return nbGate
    }

}