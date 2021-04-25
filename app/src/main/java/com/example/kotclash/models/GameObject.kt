package com.example.kotclash.models

import android.graphics.RectF
import com.example.kotclash.controllers.Map
import kotlin.math.atan2


open class GameObject(
        val enemy: Boolean,
        var coordinates: Pair<Float, Float>,
        var currentOrientation: Float,
        open var size : Pair<Int, Int> = Pair(5,3)
) {

    open var type = ""

    init{

    }

    //Needed for view and common to each object
    //Size for the grid
    //open var size = Pair(0,0)

    //open var name: String

    var dead = false
    val range = 0
    open val damage = 0

    //Parcelable
    var rectF: RectF = RectF(coordinates.first, coordinates.second, coordinates.first, coordinates.second)



    //TODO For each "movable" object -> Offset the rectangle






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