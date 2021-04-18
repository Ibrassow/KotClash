package com.example.kotclash.models

import com.example.kotclash.GameManager
import com.example.kotclash.Map
import kotlin.math.*

open class Entity(enemy: Boolean, coordinates : Pair<Float,Float>, gameManager: GameManager, currentOrientation: Float) : GameObject(enemy, coordinates, currentOrientation, gameManager) {
//open var view:GameObjectView,

    open var health = 0

    open val freqShoot = 0f
    var previousAttackTime = System.currentTimeMillis()

    var target : Entity? = null


    //getDamaged should remain an abstract method
    //substracts healthpoints, and sets dead = true when dies
    open fun getDamaged(dmg: Int) {
        health -= dmg  //different from member variable damage
        if (health <= 0){
            dead = true
        }
    }




    //TODO : should be overridden, as each troop will create its own projectile
    override fun attack(entity: Entity){
        val orientation = getInitAngleProjectile(entity)
        //gameManager.createProjectile(!enemy,"projectile",entity,coordinates, orientation)
    }


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
    fun distToEnemy(entity: Entity): Float{
        val distEnemy = sqrt((entity.coordinates.first - coordinates.first).pow(2) + (entity.coordinates.second - coordinates.second).pow(2))
        return distEnemy.toFloat()
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
            previousAttackTime = currentAttackTime
        }
        return ready
    }



    fun selectTarget(grid: Map) : Entity? {
        val listEnemiesInRange = getEnemiesInRange(grid)
        if (listEnemiesInRange.isNotEmpty()) {
            val closestEnemy = getClosestEnemy(listEnemiesInRange)
            val distToClosestEnemy = distToEnemy(closestEnemy!!)
            if (distToClosestEnemy > range){
                target = null //ATTENTION! Artifice pour pas utiliser nullable
            }else{
                target = closestEnemy
            }
        }else{
            target = null //ATTENTION! Artifice pour pas utiliser nullable
        }
        return target
    }


    //finds closest enemy and checks at the same time that it is truly within range
    fun getClosestEnemy(listEnemiesInRange: MutableList<Entity>): Entity?{
        target = null //ATTENTION ! Artifice pour pas utiliser nullable
        var minDist = range.toFloat();
        for(elem in listEnemiesInRange){
            var distToEnemy = distToEnemy(elem)
            if(distToEnemy < minDist){
                minDist = distToEnemy
                target = elem
            }
        }
        return target
    }


    fun getInitAngleProjectile(entity: Entity):Float{
        val angle = getAngleVector(Pair(coordinates.first,coordinates.second),
                Pair(entity.coordinates.first,
                        entity.coordinates.second))
        return angle
    }

}


