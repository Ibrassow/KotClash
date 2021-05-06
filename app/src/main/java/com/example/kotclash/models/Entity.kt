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


    //TODO : dès qu'img prête on peut lancer projectile
    override fun attack(entity: GameObject) {
        game.createProjectile(enemy, entity as Entity, coordinates, damage)
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
        //Log.e("this","$this")
        //Log.e("listEnemies","enem:$listEnemiesInRange")

        var target2 : GameObject? = null


        if (listEnemiesInRange.isNotEmpty()) {
            val closestEnemy = getClosestEnemy(listEnemiesInRange)
            val distToClosestEnemy = distToEnemy(closestEnemy!!)
            //range*oldRendH
            if (distToClosestEnemy < sqrt((range*oldRendW).pow(2) + (range*oldRendH).pow(2))) {
                target2 = closestEnemy
            //val xCoord = coordinates.first/oldRendW
            //val yCoord = coordinates.second/oldRendH
            //val targetXCoord = target2.coordinates.first/oldRendW
            //val targetYCoord = target2.coordinates.second/oldRendH
            //Log.e("target2","$target2")
            //Log.e("coordinates","$xCoord,$yCoord")
            //Log.e("targetCoordiantes","$targetXCoord,$targetYCoord")
            }
        }
        return target2
    }


    //finds closest enemy
    fun getClosestEnemy(listEnemies: MutableList<GameObject>): GameObject?{
        var smallestDist = 20000f;
        var target3:GameObject? = null

        //val m = listEnemies.size
        //Log.d("TARGETPOINTSIZE", "$m")

        for(elem in listEnemies){
            var distToEnemy = distToEnemy(elem) //TODO HERE NaN
            //Log.d("TARGETPOINTDIST", "$distToEnemy")
            if(distToEnemy < smallestDist){
                smallestDist = distToEnemy
                target3 = elem
            }
        }
        //Log.e("TARGETPOINT", "$target")
        return target3
    }


    fun getInitAngleProjectile(entity: GameObject):Float{
        val angle = getAngleVector(Pair(coordinates.first,coordinates.second),
                Pair(entity.coordinates.first,
                        entity.coordinates.second))
        return angle
    }

}


