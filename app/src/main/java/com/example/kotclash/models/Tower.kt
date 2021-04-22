package com.example.kotclash.models

import com.example.kotclash.Map
import com.example.kotclash.GameManager

//set base to true when the tower is a base (=> its destruction leads to the end of the game)
open class Tower(enemy: Boolean, coordinates : Pair<Float,Float>, currentOrientation: Float, gameManager: GameManager
) : Entity(enemy, coordinates, currentOrientation, gameManager) {

    override var size = Pair(3,3)

    override fun takeAction(elapsedTimeMS: Long, grid:Map) {
        if(readyForAttack()){
            target = selectTarget(grid)
            if(!(target == null)){   //ARTIFICE EN PRINCIPE TEMPORAIRE
                attack(target!!)
            }
        }
    }


    override fun getDamaged(dmg: Int) {
        super.getDamaged(dmg)
        if(dead && enemy){
            //gameManager.updateEnemiesDestroyed()
        }else if(dead && !enemy){
            //gameManager.updateAlliesDestroyed()
        }
    }

}