package com.example.kotclash.models

//set base to true when the tower is a base (=> its destruction leads to the end of the game)
open class Tower(enemy: Boolean,
                 coordinates : Pair<Float,Float>,
                 game: GameManager
            ) : Entity(enemy, coordinates, game) {

    override var size = Pair(3f,3f)

    /*override fun takeAction(elapsedTimeMS: Long, map: Map) {
        if(readyForAttack()){
            if(target != null){
                attack(target!!)
            }else {
                target = selectTarget(map)
                if (target != null) {   //ARTIFICE EN PRINCIPE TEMPORAIRE
                    attack(target!!)
                }
            }
        }
    }*/

    override fun takeAction(elapsedTimeMS: Long, map: Map) {
        /*if(readyForAttack()){
            target = selectTarget(map)
            if (target != null) {
                attack(target!!)
                previousAttackTime = System.currentTimeMillis()
            }
        }*/
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