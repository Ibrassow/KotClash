package com.example.kotclash.models

import android.graphics.RectF
import android.util.Log
import kotlin.math.*
import kotlin.properties.Delegates

open class GameObject(
        val enemy: Boolean,
        var coordinates: Pair<Float, Float>,
        open var size : Pair<Float, Float> = Pair(1f,1f),
        var currentOrientation: Float = 0f
) {

    open var type = ""
    var takingAction = false

    var endx = coordinates.first
    var endy = coordinates.second


    var dead = false
    open val range = 3
    open val damage = 0

    //Parcelable
    var rectF: RectF = RectF(coordinates.first, coordinates.second, endx, endy)

    //Don't change
    var oldRendW = 1f
    var oldRendH = 1f


    var ix : Int = getIx()

    fun startOperation(){
        takingAction = true
    }

    //X,y should be the center ..
    open fun setRect(rendW : Float, rendH : Float){
        val rx = coordinates.first
        val ry = coordinates.second

        val x = (coordinates.first / oldRendW * rendW)
        val y = (coordinates.second / oldRendH * rendH)


        if (type == "tankred"){
            Log.e("$ix", "olH $oldRendH olW $oldRendW --- ($x,$y) -- rend H : $rendH - rend W : $rendW --- first : ($rx, $ry)")
            Log.e("TANKRED", "id : $ix --- ($x,$y) -- rend H : $rendH - rend W : $rendW")
        }

        coordinates = Pair(x,y)
        endx = x + rendW
        endy = y + rendH
        oldRendW = rendW
        oldRendH = rendH
        rectF.set(x - (size.first/2f)*rendW, y - (size.second/2f)*rendH, endx + (size.first/2f)*rendW, endy + (size.second/2f)*rendH)
    }
    fun setRectpro(rendW : Float, rendH : Float) {

        val x = (coordinates.first / oldRendW  )
        val y = (coordinates.second / oldRendH  )
        coordinates = Pair(x, y)
        endx = x + rendW
        endy = y + rendH
        oldRendW = rendW
        oldRendH = rendH
        rectF.set(x - (size.first / 2f) * 1, y - (size.second / 2f) * 1, endx + (size.first / 2f) * 1, endy + (size.second / 2f) * 1)
    }


    open fun takeAction(elapsedTimeMS: Long, map: Map) {
    }

    /*fun isObstacle(){
    }*/

    fun isAlive():Boolean{
        return !dead
    }


    fun isEnemy(): Boolean{
        return enemy
    }


    fun getEnemiesInRange(map: Map): MutableList<GameObject>{
        val xx = ceil(coordinates.first.toDouble()/oldRendW).toInt()
        val yy = ceil(coordinates.second.toDouble()/oldRendH).toInt()
        val enemiesAround = map.scanArea(Pair(xx, yy), range, this)
        //Log.e("enemiesAround","$enemiesAround - $this")
        return enemiesAround
    }


    //allows to determine the closest enemy
    fun distToEnemy(entity: GameObject): Float{
        return sqrt((entity.rectF.centerX() - rectF.centerX()).pow(2) + (entity.rectF.centerY() - rectF.centerY()).pow(2))
    }


    open fun attack(entity: GameObject) {
        entity.getDamaged(damage)
    }

    open fun getDamaged(dmg: Int) {
    }


    fun isEnemyOf(obj : GameObject):Boolean{
        var isEnemy = false
        if(enemy != obj.enemy){
            isEnemy = true
        }
        return isEnemy
    }


    fun getAngleVector(initPoint:Pair<Float,Float>, finalPoint:Pair<Float,Float>):Float{
        val vector = Pair(finalPoint.first - initPoint.first, finalPoint.second - initPoint.second)

        var angle = if (vector.first == 0f){
                        if (vector.second <=0 ){
                            PI/2 }
                        else{
                            -PI/2}
                    }
                    else if(vector.first > 0){
                        -atan((vector.second/vector.first).toDouble())
                    }
                    else if (vector.first < 0 && vector.second <=0 ){
                        PI-atan((vector.second/vector.first).toDouble())
                    }
                    else{
                        -PI-atan((vector.second/vector.first).toDouble())
        }
        return angle.toFloat()
    }


    //Specific ID for each object
    companion object {
        var count:Int = 0
        private fun getIx():Int{
            count++
            return count
        }

    }


}