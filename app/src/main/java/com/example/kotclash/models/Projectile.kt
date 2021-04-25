package com.example.kotclash.models

import com.example.kotclash.controllers.Map
import com.example.kotclash.controllers.GameManager
import kotlin.math.ceil
import kotlin.math.cos
import kotlin.math.sin

class Projectile(enemy: Boolean,
                 val target: Entity,
                 coordinates : Pair<Float,Float>,
                 currentOrientation : Float,
                 gameManager: GameManager
) : GameObject(enemy, coordinates, currentOrientation), Movable {



    //TODO : def projectile for each troop
    val projectileDamage = 0
    val speed = 0f


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
        coordinates = Pair(coordinates.first + speed*elapsedTimeMS*cos(direction),
                coordinates.second + speed*elapsedTimeMS*sin(direction))

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


    override fun attack(entity: Entity) {
        //could depend on type of projectile -> could also repel troops
        //one option:
        entity.getDamaged(projectileDamage)
    }
}