package com.example.kotclash.models

import android.util.Log
import kotlin.math.*

class Projectile(enemy: Boolean,
                 var target: Entity,
                 coordinates : Pair<Float,Float>,
                 override val damage : Int
                ) : GameObject(enemy, coordinates), Movable {

    override var type = "projectile"
    private val projRange = sqrt((range*oldRendW).pow(2) + (range*oldRendH).pow(2))*2.5f
    private val speed = 1f



    override fun takeAction(elapsedTimeMS: Long, map: Map) {
        if (!target.dead){
            val dist = distToEnemy(target)
            if(dist < projRange){
                attack(target)
            }else{
                move(elapsedTimeMS)
            }
        }
        else{
            dead = true
        }


    }


    fun move(interval : Long) {
        val targCoord = Pair(target.rectF.centerX(), target.rectF.centerY())
        currentOrientation = getAngleVector(coordinates,targCoord)

        val dx = speed * interval * cos(currentOrientation)
        val dy = speed * interval * -sin(currentOrientation)

        coordinates = Pair(coordinates.first + dx, coordinates.second + dy)

        rectF.offset(dx, dy)

    }


    override fun attack(entity: GameObject) {
        entity.getDamaged(damage)
        dead = true
    }

    override fun setRect(rendW : Float, rendH : Float){
        val x = (coordinates.first / oldRendW * rendW)
        val y = (coordinates.second / oldRendH * rendH)
        coordinates = Pair(x,y)
        //endx = x + rendW
        //endy = y + rendH
        oldRendW = rendW
        oldRendH = rendH
        rectF.set(x - (size.first/2f)*20f, y - (size.second/2f)*20f, x + (size.first/2f)*20f, y + (size.second/2f)*20f)
    }
}