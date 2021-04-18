package com.example.kotclash


//set base to true when the tower is a base (=> its destruction leads to the end of the game)
open class Tower(enemy: Boolean,
                 coordinates : Pair<Float,Float>,
                 gameManager: GameManager, currentOrientation: Float,
) : Entity(enemy, coordinates, gameManager, currentOrientation) {    //open var view:GameObjectView,


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
            gameManager.updateEnemiesDestroyed()
        }else if(dead && !enemy){
            gameManager.updateAlliesDestroyed()
        }
    }

}