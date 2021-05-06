package com.example.kotclash.models

import android.util.Log
import kotlin.math.*

class Projectile(enemy: Boolean,
                 val target: Entity,
                 coordinates : Pair<Float,Float>,
                 override val damage : Int
                ) : GameObject(enemy, coordinates), Movable {

    override var type = "projectile"
    val projRange = sqrt((range*oldRendW).pow(2) + (range*oldRendH).pow(2))*2
    private val speed = 1f



    override fun takeAction(elapsedTimeMS: Long, map: Map) {
        val dist = distToEnemy(target)
        if(dist < projRange){
            attack(target)
            Log.e("PROJECTILE", "TargetHIT")
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
        Log.e("PROJECTILE", "$coordinates")
        //used to update view
        rectF.offset(dx, dy)

        //map.displace(this, previousCoordinates)

    }


    override fun attack(entity: GameObject) {
        entity.getDamaged(damage)
        dead = true
        Log.e("PROJECTILE", "I'm Done")
    }

    override fun setRect(rendW : Float, rendH : Float){
        val x = (coordinates.first / oldRendW * rendW)
        val y = (coordinates.second / oldRendH * rendH)
        coordinates = Pair(x,y)
        //endx = x + rendW
        //endy = y + rendH
        oldRendW = rendW
        oldRendH = rendH
        rectF.set(x - (size.first/2f)*15f, y - (size.second/2f)*15f, x + (size.first/2f)*15f, y + (size.second/2f)*15f)
    }
}