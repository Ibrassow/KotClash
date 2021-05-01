package com.example.kotclash.models

//set base to true when the tower is a base (=> its destruction leads to the end of the game)
open class Tower(enemy: Boolean, coordinates : Pair<Float,Float>
) : Entity(enemy, coordinates) {

    override var size = Pair(3f,3f)

    override fun takeAction(elapsedTimeMS: Long, grid: Map) {
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