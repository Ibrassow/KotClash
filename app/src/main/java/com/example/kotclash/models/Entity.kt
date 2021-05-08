package com.example.kotclash.models

import android.util.Log
import kotlin.math.*

open class Entity(enemy: Boolean, coordinates : Pair<Float,Float>)
    : GameObject(enemy, coordinates) {


    open var health = 0

    open val freqShoot = 1500f
    var previousAttackTime = System.currentTimeMillis()
    var game = GameManager.gameInstance

    var target : GameObject? = null


    override fun attack(entity: GameObject) {
        game.createProjectile(this, entity as Entity)
    }


    //substracts healthpoints, and sets dead = true when dies
    override fun getDamaged(dmg: Int) {
        health -= dmg  //different from member variable damage
        //Log.e("health","$health")
        if (health <= 0) {
            dead = true
            game.map.killEntity(this)
            Log.e("DEAD", "They killed me.. a $type")
        }
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



    fun selectTarget(map: Map) : GameObject? {

        val listEnemiesInRange = getEnemiesInRange(map)
        var target2 : GameObject? = null

        if (listEnemiesInRange.isNotEmpty()) {
            val closestEnemy = getClosestEnemy(listEnemiesInRange)
            val distToClosestEnemy = distToEnemy(closestEnemy!!)
            if (distToClosestEnemy < sqrt((range*oldRendW).pow(2) + (range*oldRendH).pow(2))) {
                target2 = closestEnemy
            }
        }
        return target2
    }


    //finds closest enemy
    fun getClosestEnemy(listEnemies: MutableList<GameObject>): GameObject?{
        var smallestDist = 20000f;
        var target3:GameObject? = null

        for(elem in listEnemies){
            var distToEnemy = distToEnemy(elem)
            //Log.d("TARGETPOINTDIST", "$distToEnemy")
            if(distToEnemy < smallestDist){
                smallestDist = distToEnemy
                target3 = elem
            }
        }

        return target3
    }


    fun getInitAngleProjectile(entity: GameObject):Float{
        val angle = getAngleVector(Pair(coordinates.first,coordinates.second),
                Pair(entity.coordinates.first,
                        entity.coordinates.second))
        return angle
    }

}


