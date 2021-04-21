package com.example.kotclash.models

import com.example.kotclash.GameManager
import com.example.kotclash.Map
import kotlin.math.atan2


open class GameObject(
        val enemy: Boolean,
        var coordinates: Pair<Float, Float>,
        var currentOrientation: Float
) {

    var dead = false
    val range = 0
    open val damage = 0


    open fun takeAction(elapsedTimeMS: Long, grid: Map){}

    /*fun isObstacle(){
    }*/


    fun isAlive():Boolean{
        return !dead
    }


    fun isEnemy(): Boolean{
        return enemy
    }


    fun getEnemiesInRange(grid: Map): MutableList<Entity>{
        val xx = Math.ceil(coordinates.first.toDouble()).toInt()
        val yy = Math.ceil(coordinates.second.toDouble()).toInt()
        return grid.scanArea(Pair(xx, yy), range)
    }


    open fun attack(entity: Entity) {
        //entity.getDamaged(damage)
    }


    fun getAngleVector(initPoint:Pair<Float,Float>, finalPoint:Pair<Float,Float>):Float {
        val vector = Pair(finalPoint.first - initPoint.first, finalPoint.second - initPoint.second)
        val angle = getAngleBetweenVectors(Pair(1f,0f),vector)
        return angle
    }


    fun getAngleBetweenVectors(v1:Pair<Float,Float>, v2:Pair<Float,Float>): Float{
        val angle = atan2(v1.first*v2.first-v2.first*v1.second,v1.first*v2.first+v1.second*v2.second)
        return angle
    }




    //I keep this fun just in case
    /*fun getEnemiesInRange(): ArrayList<Entity>{
       val listEnemiesInRange = ArrayList<Entity>()
       for(i in -range..range){
          for(j in -range..range){
             if(grid.validIndex(iThis + i) && grid.validIndex(jThis + j)) {
                val entitiesInCell = grid.getEntitiesInCell(i,j)
                for(potentialEnemy in entitiesInCell){
                   if(isEnemyOf(potentialEnemy)){
                      listEnemiesInRange.add(potentialEnemy)
                   }
                }
             }
          }
       }
       return listEnemiesInRange
    }*/
}