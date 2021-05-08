package com.example.kotclash.models

import android.util.Log
import kotlin.math.ceil


/**
 * Main manager of the game - Singleton
 *
 * This class controls the logic of the game
 *
 * @constructor Initiliazes the first towers and the map
 */
class GameManager {

    companion object {
        private var instance: GameManager? = null
        val gameInstance: GameManager
            get() {
                if (instance == null) {
                    instance = GameManager()
                }
                return instance!!
            }
        fun destroy() {
            instance = null

        }
    }


    //Map
    var map = Map()
    var mapLoader: MapLoader = MapLoader()


    var GAMEOVER = false
    var STARTED = false


    private val enemyGenerationFreq : Long = 15
    private var previousEnemyGenerationTime : Long = System.nanoTime() / 1000000
    var resources = 0f
    private val speedFill = 1/100f
    private val RESOURCESMAX = 100f


    var enemyTowersDestroyed = 0
    var allyTowersDestroyed = 0

    var cardClicked: String? = null

    var timeLeft : Float = 130f

    lateinit var currentMap: String
    lateinit var results : String


    /////////////////////////
    val troopFactory = TroopFactory()
    val cardManager = CardManager(this)
    val gameObjectList = mutableListOf<GameObject>()
    val enemyTowersList = mutableListOf<GameObject>()
    val allyTowersList = mutableListOf<GameObject>()
    val projectileList = mutableListOf<GameObject>()

    // -------------------- INIT ------------------- //



    fun start(){
        initializeObjects()
        STARTED = true
    }

    fun setMap(mapName: String) {
        mapLoader.loadMap(mapName)
        map = mapLoader.returnMap()
        val ss = map.grid.isNotEmpty()
        currentMap = mapName
    }


    fun initializeObjects() {

        //default map
        if (map.grid.isEmpty()){
            setMap("spring")
        }


        //Two bases - One per side
        gameObjectList.add(troopFactory.getTroop(true, "base", map.posBases["enemy"]!!))
        gameObjectList.add(troopFactory.getTroop(false, "base", map.posBases["ally"]!!))

        //Additional towers for the enemy side
        for (position in map.posEnemyTower){
            gameObjectList.add(troopFactory.getTroop(true, "simpleTower", position.value))
        }

        //Additional towers for the ally side
        for (position in map.posAllyTower){
            gameObjectList.add(troopFactory.getTroop(false, "simpleTower", position.value))
        }

        for (elem in gameObjectList) {
            if (elem is Tower){
                if (elem.isEnemy()) {
                    enemyTowersList.add(elem)
                } else {
                    allyTowersList.add(elem)
                }

                map.placeTower(elem)
            }
        }

    }



    fun update(elapsedTimeMS: Long) {

        if (STARTED){
            timeLeft -= (elapsedTimeMS*3/1000f)
            if (timeLeft <= 0.0) {
                endGame()
            }
            updateResource(elapsedTimeMS)
            takeAction(elapsedTimeMS)
            autonomousEnemyGeneration()

            for (projectile in projectileList){
                gameObjectList.add(projectile)
            }
            projectileList.clear()

            gameObjectList.removeAll{ it.dead }

            gameObjectList.forEach{obj ->
                if (!obj.takingAction){
                    obj.startOperation()
                }
            }
            //if(!obj.takingAction){obj.startOperation()}

            val nn = gameObjectList.size
            Log.e("sizeObjList", "$nn")
        }


    }


    fun takeAction(elapsedTimeMS: Long) {
        for (obj in gameObjectList) {
            if (obj.isAlive() && obj.takingAction) {
                obj.takeAction(elapsedTimeMS, map)
            }
        }
    }



    fun autonomousEnemyGeneration() {
        if (readyForEnemyGeneration()) {
            val nbRand = kotlin.random.Random.Default.nextInt(2)  //TODO : define more complex generation pattern (preferably one that respects resources)
            val nbRandTroop = kotlin.random.Random.Default.nextInt(4)
            var randTroop : String = ""
            when(nbRandTroop){
                0 -> randTroop = "tankred"
                1 -> randTroop = "tankblue"
                2 -> randTroop = "tankgreen"
                3 -> randTroop = "soldier"
            }
            if (randTroop == ""){randTroop = "tankred"}
            gameObjectList.add(troopFactory.getTroop(true, randTroop, map.posEnemySpawn[nbRand]!!))
        }
    }


    private var readyEnemyGen = false
    //checks whether set time between two enemy creations passed
    fun readyForEnemyGeneration(): Boolean {
        readyEnemyGen = false

        val currentGenerationTime = System.currentTimeMillis()
        val deltaTime = (currentGenerationTime - previousEnemyGenerationTime)/1000
        if (deltaTime > enemyGenerationFreq) {
            readyEnemyGen = true
            previousEnemyGenerationTime = currentGenerationTime
        }

        return readyEnemyGen
    }


    fun createProjectile(thrower: GameObject, target: Entity) {
        val newCoordinates = Pair(ceil(thrower.coordinates.first / thrower.oldRendW), ceil(thrower.coordinates.second / thrower.oldRendH))
        projectileList.add(troopFactory.getTroop(thrower.enemy, "projectile", newCoordinates, target, dmgProjectile = thrower.damage))
    }


    private fun updateResource(elapsedTimeMS: Long) {
        if (resources <= RESOURCESMAX){
            if (resources < 0){
                resources = 0f
            }else{
                resources += elapsedTimeMS * speedFill
            }
        }
        else {
            resources = RESOURCESMAX
        }

    }


    fun useResource(price: Int) {
        resources -= price
    }

    fun addTroop(enemy : Boolean, type: String, side: Int){
        when(enemy){
            false -> gameObjectList.add(troopFactory.getTroop(enemy,type, map.posAllySpawn[side]!!))
            true -> gameObjectList.add(troopFactory.getTroop(enemy,type, map.posEnemySpawn[side]!!))
        }
    }



    fun updateEnemiesDestroyed(obj: GameObject) {
        enemyTowersDestroyed++
        var idx : Int? = null
        for (i in 0 until (enemyTowersList.size)){
            if (enemyTowersList[i].ix == obj.ix){
                idx = i
            }
        }

        if (idx != null){
            enemyTowersList.removeAt(idx)
        }

    }


    fun updateAlliesDestroyed(obj: GameObject) {
        allyTowersDestroyed++
        var idx : Int? = null
        for (i in 0 until (allyTowersList.size)){
            if (allyTowersList[i].ix == obj.ix){
                idx = i
            }
        }

        if (idx != null){
            allyTowersList.removeAt(idx)
        }
    }


    fun saveCard(card: String?) {
        cardClicked = card
    }



    fun playCard(side : Int){
        if(cardClicked != null){
            cardManager.playCard(cardClicked!!, side)
            cardClicked = null
        }
    }

    fun isCardAvailable(nmCard : String): Boolean{
        return cardManager.isAvailable(nmCard)
    }

    fun endGame() {

        if (allyTowersDestroyed < enemyTowersDestroyed) {
            setGameOver(true)
        } else if (allyTowersDestroyed > enemyTowersDestroyed) {
            Log.wtf("destroy", "$allyTowersDestroyed  $enemyTowersDestroyed")
            setGameOver(false)
        } else {
            setGameOver(null)}
    }



    fun setGameOver(gameWon: Boolean?) {
        GAMEOVER = true
        if (gameWon == false) {
            results = "Defeated"
        } else if (gameWon == true) {
            Log.e("WIN", "YEAH")
            results = "Victory"
        } else {
            Log.e("LOSE", "No..")
            results = "Equality"

        }
    }


}