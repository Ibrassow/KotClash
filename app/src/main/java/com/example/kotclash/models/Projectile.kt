package com.example.kotclash.models

import android.util.Log
import kotlin.math.*

class Projectile(enemy: Boolean,
                 val target: Entity,
                 coordinates : Pair<Float,Float>,
                 override val damage : Int
                ) : GameObject(enemy, coordinates), Movable {


    val projRange = 0.5f* sqrt((range*oldRendW).pow(2) + (range*oldRendH).pow(2))
    private val speed = 1f

    //Implémentation pour missiles qui touchent tt le monde aux alentours
    /*override fun takeAction(elapsedTimeMS: Long, grid: Map) {
        val enemyInRange = getEnemiesInRange(grid)
        if(enemyInRange.isNotEmpty()){
            for(entity in enemyInRange) {
                attack(entity)
            }
        }else{
            move(elapsedTimeMS)
        }
    }*/


    override fun takeAction(elapsedTimeMS: Long, map: Map) {
        val dist = distToEnemy(target)
        if(dist < projRange){
            attack(target)
        }else{
            move(elapsedTimeMS, map)
        }
    }


    fun move(interval : Long, map: Map) {

        currentOrientation = getAngleVector(coordinates,target.coordinates)
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
        //val x = coordinates.first/oldRendW
        //val y = coordinates.second/oldRendH
        //Log.e("TroopCoord", "coord : $x,$y")

        map.displace(this, previousCoordinates)

    }


    override fun attack(entity: GameObject) {
        //could depend on type of projectile -> could also repel troops
        //one option:
        entity.getDamaged(damage)
    }
}