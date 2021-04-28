package com.example.kotclash.models

import com.example.kotclash.Map
import com.example.kotclash.GameManager
import kotlin.math.*


open class Troop(enemy: Boolean,
                 coordinates : Pair<Float,Float>,
                 currentOrientation: Float,
                 gameManager: GameManager
                    ) : Entity(enemy, coordinates, currentOrientation, gameManager), Movable{


    open val speed  = 0f
    var targetOfMotion: Entity? = null


    //serve to direct movement of troops
    private val gate1 = Pair(0f,0f)
    private val gate2 = Pair(0f,0f)


    override fun takeAction(ElapsedTimeMS: Long, grid:Map){
        val readyForAttack = readyForAttack()
        if(readyForAttack){
            target = selectTarget(grid)
            if(target != null) {
                attack(target!!)
            }
        }else{
            move(ElapsedTimeMS)
        }
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


    private fun move(interval : Long){
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


        if(ceil(coordinates.first) != ceil(previousCoordinates.first)
                        || ceil(coordinates.second) != ceil(previousCoordinates.second)){
            //grid.displace(this,coordinatesIdx, currentOrientation)
            //TODO
        }

    }


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

}