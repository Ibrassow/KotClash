package com.example.kotclash.models

import kotlin.math.ceil
import kotlin.math.cos
import kotlin.math.sin

class Projectile(enemy: Boolean,
                 val target: Entity,
                 coordinates : Pair<Float,Float>,
                 game: GameManager
                ) : GameObject(enemy, coordinates, game), Movable {



    //TODO : def projectile for each troop
    private val projectileDamage = 0
    private val speed = 0f


    override fun takeAction(elapsedTimeMS: Long, grid: Map) {
        val enemyInRange = getEnemiesInRange(grid)
        if(enemyInRange.isNotEmpty()){
            for(entity in enemyInRange) {
                attack(entity)
            }
        }else{
            move(elapsedTimeMS)
        }
    }


    //implement interface for movement
    fun move(elapsedTimeMS: Long){
        //will need getDirection() -> towards target
        val direction = getDirection()

        val previousCoordinates = coordinates
        val dx = speed*elapsedTimeMS*cos(currentOrientation)
        val dy = speed*elapsedTimeMS*sin(currentOrientation)
        //make sure to homogenise order of magnitude of speeds (unit time: s/ms?)

        //update x & y in model
        coordinates = Pair(coordinates.first + dx, coordinates.second + dy)

        //used to update view
        rectF.offset(dx,dy)

        //notify view of movement
        if(!(ceil(coordinates.first) == ceil(previousCoordinates.first)
                        && ceil(coordinates.second) == ceil(previousCoordinates.second))){
            //grid.displace(this,coordinatesIdx,currentOrientation)
            //TODO
        }
    }


    //find direction of movement
    fun getDirection():Float{
        val direction = getAngleVector(coordinates,target.coordinates)
        return direction
    }


    override fun attack(entity: GameObject) {
        //could depend on type of projectile -> could also repel troops
        //one option:
        entity.getDamaged(projectileDamage)
    }
}