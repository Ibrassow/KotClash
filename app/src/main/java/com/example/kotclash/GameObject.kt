package com.example.kotclash

import java.lang.Math.ceil
import kotlin.math.atan2


open class GameObject(
        val enemy: Boolean,
        var coordinates: Pair<Float, Float>,
        var currentOrientation: Float,
        val gameManager: GameManager
) {

    var dead = false
    val range = 0
    open val damage = 0


    open fun takeAction(elapsedTimeMS: Long, grid: Map){}


    fun isAlive():Boolean{
        return !dead
    }


    fun isEnemy(): Boolean{
        return enemy
    }


    //checks whether the 2 troops are in opposite camps
    fun isEnemyOf(entity : Entity): Boolean{
        var myEnemy = false
        if (enemy != entity.enemy){
            myEnemy = true
        }
        return myEnemy
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


    //TODO
    /*fun getEnemiesInRange(grid:Map): ArrayList<Entity>{
        val pairIdx = Pair(ceil(coordinates.first.toDouble()),ceil(coordinates.second.toDouble()))
        return grid.scanArea(pairIdx, range)
    }*/


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
}