package com.example.kotclash.models

import android.util.Log
import kotlin.math.ceil

//set base to true when the tower is a base (=> its destruction leads to the end of the game)
open class Tower(enemy: Boolean, coordinates : Pair<Float,Float>
) : Entity(enemy, coordinates) {

    override var size = Pair(3f,3f)

    override fun takeAction(elapsedTimeMS: Long, map: Map) {
        target = selectTarget(map)
        if (target != null) {
            if(readyForAttack()) {
                previousAttackTime = System.currentTimeMillis()
            }
        }
    }


    override fun getDamaged(dmg: Int) {
        super.getDamaged(dmg)
        if(dead && enemy){
            game.updateEnemiesDestroyed(this)
        }else if(dead && !enemy){
            game.updateAlliesDestroyed(this)
        }
    }

}