package com.example.kotclash.models

import android.util.Log
import kotlin.math.*

open class Entity(enemy: Boolean, coordinates : Pair<Float,Float>, game:GameManager)
    : GameObject(enemy, coordinates, game) {


    open var health = 0

    open val freqShoot = 2000f
    var previousAttackTime = System.currentTimeMillis()

    var target : GameObject? = null


    //getDamaged should remain an abstract method
    //substracts healthpoints, and sets dead = true when dies
    override fun getDamaged(dmg: Int) {
        health -= dmg  //different from member variable damage
        if (health <= 0) {
            dead = true
        }
    }

    //TODO : should be overridden, as each troop will create its own projectile
    /*override fun attack(entity: GameObject){
    val orientation = getInitAngleProjectile(entity)
        //gameManager.createProjectile(!enemy,"projectile",entity,coordinates, orientation)
    }*/


    //target under range
    /*fun enemyAttackable(entity : Entity): Boolean {
        var proxEnemy = false
        val distEnemy = this.distToEnemy(entity)
        if (distEnemy < range) {
            proxEnemy = true
        }
        return proxEnemy
    }*/


    //allows to determine the closest enemy
    fun distToEnemy(entity: GameObject): Float{
        val distEnemy = sqrt((entity.coordinates.first - coordinates.first).pow(2) + (entity.coordinates.second - coordinates.second).pow(2))
        return distEnemy
    }


    //target neither dead nor too far
    /*fun checkTargetValid(entity : Entity): Boolean{
        var valid = true
        if (!entity.isAlive()){
            valid = false
        }else if (!enemyAttackable(entity)){
            valid = false
        }
        return valid
    }*/


    //checks whether the set time between 2 attacks has been reached
    fun readyForAttack(): Boolean{
        var ready = false
        val currentAttackTime = System.currentTimeMillis()
        val deltaTime = (currentAttackTime - previousAttackTime)
        if (deltaTime > freqShoot) {
            ready = true
            Log.e("readyForAttack","ready $ready")
        }
        return ready
    }



    fun selectTarget(grid: Map) : GameObject? {
        //Log.e("selectTargetLaunched","launched")
        val listEnemiesInRange = getEnemiesInRange(grid)
        Log.e("listEnemies","enem:$listEnemiesInRange")
        target = null
        if (listEnemiesInRange.isNotEmpty()) {
            val closestEnemy = getClosestEnemy(listEnemiesInRange)
            val distToClosestEnemy = distToEnemy(closestEnemy!!)
            if (distToClosestEnemy < rangePix) {
                target = closestEnemy
            }
        }
        return target
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


