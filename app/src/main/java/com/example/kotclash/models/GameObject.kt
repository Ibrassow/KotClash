package com.example.kotclash.models

import android.graphics.RectF
import android.util.Log
import kotlin.math.PI
import kotlin.math.atan
import kotlin.math.ceil
import kotlin.math.atan2

open class GameObject(
        val enemy: Boolean,
        var coordinates: Pair<Float, Float>,
        open var size : Pair<Float, Float> = Pair(1f,1f),
        var currentOrientation: Float = 0f
) {

    open var type = ""


    var endx = coordinates.first
    var endy = coordinates.second



    var dead = false
    val range = 0
    open val damage = 0

    //Parcelable
    var rectF: RectF = RectF(coordinates.first, coordinates.second, endx, endy)
    //TODO For each "movable" object -> Offset the rectangle

    //Don't change
    var oldRendW = 1f
    var oldRendH = 1f




    val ix = getIx()


    fun setRect(rendW : Float, rendH : Float){
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
        val xx = ceil(coordinates.first.toDouble()).toInt()
        val yy = ceil(coordinates.second.toDouble()).toInt()
        return grid.scanArea(Pair(xx, yy), range, this)
    }


    open fun attack(entity: GameObject) {
        //entity.getDamaged(damage)
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

        var angle = if(vector.first >= 0){
                        atan(vector.second/vector.first.toDouble())
                    }
                    else{
                        atan(vector.second/vector.first.toDouble()) + PI
                    }
        return angle.toFloat()
    }








    companion object {
        var count:Int = 0
        private fun getIx():Int{
            count++
            return count
        }

    }


}