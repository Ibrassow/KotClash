package com.example.kotclash.models

import android.util.Log
import kotlin.math.*

open class Entity(enemy: Boolean, coordinates : Pair<Float,Float>)
    : GameObject(enemy, coordinates) {


    open var health = 0

    open val freqShoot = 2000f
    var previousAttackTime = System.currentTimeMillis()

    var target : GameObject? = null


    //substracts healthpoints, and sets dead = true when dies
    override fun getDamaged(dmg: Int) {
        health -= dmg  //different from member variable damage
        if (health <= 0) {
            dead = true
        }
    }

    //TODO : should be overridden, as each troop will create its own projectile
    //TODO : Attack as an interface? -> close/distance/..
    /*override fun attack(entity: GameObject){
        val orientation = getInitAngleProjectile(entity)
        //gameManager.createProjectile(!enemy,"projectile",entity,coordinates, orientation)
    }*/




    //allows to determine the closest enemy
    fun distToEnemy(entity: GameObject): Float{
        return sqrt((entity.coordinates.first - coordinates.first).pow(2) + (entity.coordinates.second - coordinates.second).pow(2))
    }


    //checks whether the set time between 2 attacks has been reached
    fun readyForAttack(): Boolean{
        var ready = false
        val currentAttackTime = System.currentTimeMillis()
        val deltaTime = (currentAttackTime - previousAttackTime)
        if (deltaTime > freqShoot) {
            ready = true
        }
        return ready
    }



    fun selectTarget(grid: Map) : GameObject? {
        val listEnemiesInRange = getEnemiesInRange(grid)
        Log.e("listEnemies","enem:$listEnemiesInRange")
        target = null
        if (listEnemiesInRange.isNotEmpty()) {
            val closestEnemy = getClosestEnemy(listEnemiesInRange)
            if (closestEnemy == null){
                return null
            }
            else {
                val distToClosestEnemy = distToEnemy(closestEnemy!!)
                if (distToClosestEnemy < range*oldRendW) {
                    return closestEnemy
                }
            }

        }
        return null
    }


    //finds closest enemy
    fun getClosestEnemy(listEnemies: MutableList<GameObject>): GameObject?{
        var smallestDist = 20000f;
        for(elem in listEnemies){
            var distToEnemy = distToEnemy(elem)
            if(distToEnemy < smallestDist){
                smallestDist = distToEnemy
                target = elem
            }
        }
        return target
    }


    fun getInitAngleProjectile(entity: GameObject):Float{
        val angle = getAngleVector(Pair(coordinates.first,coordinates.second),
                Pair(entity.coordinates.first,
                        entity.coordinates.second))
        return angle
    }

}


