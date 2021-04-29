package com.example.kotclash.models

import android.graphics.RectF
import kotlin.math.PI
import kotlin.math.atan
import kotlin.math.ceil


open class GameObject(
        val enemy: Boolean,
        open var coordinates: Pair<Float, Float>,
        var game: GameManager,
        open var size : Pair<Float, Float> = Pair(1f,1f),
        var currentOrientation: Float = 0f
) {

    open var type = ""

    var conversionH = 0f
    var conversionW = 0f

    var endx = coordinates.first
    var endy = coordinates.second



    var dead = false
    val range = 3
    val rangePix = 3*conversionW
    open val damage = 0

    //Parcelable
    var rectF: RectF = RectF(coordinates.first, coordinates.second, endx, endy)

    //Don't change
    var oldRendW = 1f
    var oldRendH = 1f


    //TODO For each "movable" object -> Offset the rectangle


    fun setRect(rendW : Float, rendH : Float){
        conversionW = rendW
        conversionH = rendH
        val x = (coordinates.first / oldRendW * rendW)
        val y = (coordinates.second / oldRendH * rendH)
        coordinates = Pair(x,y)
        endx = x + rendW
        endy = y + rendH
        oldRendW = rendW
        oldRendH = rendH
        rectF.set(x - (size.first/2f)*rendW, y - (size.second/2f)*rendH, endx + (size.first/2f)*rendW, endy + (size.second/2f)*rendH)
    }


    open fun takeAction(elapsedTimeMS: Long, grid: Map){}

    /*fun isObstacle(){
    }*/


    fun isAlive():Boolean{
        return !dead
    }


    fun isEnemy(): Boolean{
        return enemy
    }


    fun getEnemiesInRange(grid: Map): MutableList<GameObject>{
        val xx = ceil(coordinates.first.toDouble()/oldRendW).toInt()
        val yy = ceil(coordinates.second.toDouble()/oldRendH).toInt()
        return grid.scanArea(Pair(xx, yy), range)
    }


    open fun attack(entity: GameObject) {
        entity.getDamaged(damage)
    }

    open fun getDamaged(dmg: Int) {
    }


    /*fun getAngleVector(initPoint:Pair<Float,Float>, finalPoint:Pair<Float,Float>):Float {
        val vector = Pair(finalPoint.first - initPoint.first, finalPoint.second - initPoint.second)
        val angle = getAngleBetweenVectors(Pair(1f,0f),vector)
        return angle
    }


    fun getAngleBetweenVectors(v1:Pair<Float,Float>, v2:Pair<Float,Float>): Float{
        val angle = atan2(v1.first*v2.first-v2.first*v1.second,v1.first*v2.first+v1.second*v2.second)
        return angle
    }*/


    fun getAngleVector(initPoint:Pair<Float,Float>, finalPoint:Pair<Float,Float>):Float{
        val vector = Pair(finalPoint.first - initPoint.first, finalPoint.second - initPoint.second)
        var angle = 0.0
        if(vector.first >= 0){
            angle = atan(vector.second/vector.first.toDouble())
        }else{
            angle = atan(vector.second/vector.first.toDouble()) + PI
        }
        return angle.toFloat()
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