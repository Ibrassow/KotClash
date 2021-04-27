package com.example.kotclash.models

import android.util.Log
import kotlin.math.floor

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
    }


    //Map
    var map = Map()
    var mapLoader: MapLoader = MapLoader()


    private var GAMEOVER = false
    private var STARTED = false


    private val enemyGenerationFreq = 0f
    var previousEnemyGenerationTime = System.currentTimeMillis()
    var resources = 0f



    //this variable stores the nb of the card clicked on
    var nbCardClicked = 0


    //TODO : define spots
    val rightGenerationSpot = listOf(0f, 0f)
    val leftGenerationSpot = listOf(0f, 0f)


    var enemyTowersDestroyed = 0
    var allyTowersDestroyed = 0


    private val resourceBar = ResourceBar()

    var timeLeft = 40.0

    /////////////////////////
    val troopFactory = TroopFactory(this)
    val cardManager = CardManager(troopFactory, this) //TODO: might need to be in MainActivity instead
    val gameObjectList = mutableListOf<GameObject>()
    val enemyTowersList = mutableListOf<Entity>() //to use fctn already def for entities
    val allyTowersList = mutableListOf<Entity>()

    // -------------------- INIT ------------------- //



    fun start(){
        initializeObjects()
        STARTED = true
    }

    fun setMap(mapName: String) {
        mapLoader.loadMap(mapName)
        map = mapLoader.returnMap()
        val ss = map.grid.isNotEmpty()
        Log.d("InitGM", "got map : $ss")
    }


    fun initializeObjects() {

        //default map
        if (map.grid.isEmpty()){
            setMap("spring")
        }


        //here one base per side and two simpleTowers
        gameObjectList.add(troopFactory.getTroop(true, "base", mapLoader.posBases["enemy"]!!, 0f))
        gameObjectList.add(troopFactory.getTroop(false, "base", mapLoader.posBases["ally"]!!, 0f))
        /*gameObjectList.add(troopFactory.getTroop(true, "simpleTower", null, mapLoader.posSimpleTowers1["ally"]!!,  0f))
        gameObjectList.add(troopFactory.getTroop(false, "simpleTower", null, mapLoader.posSimpleTowers1["enemy"]!!,  0f))
        gameObjectList.add(troopFactory.getTroop(true, "simpleTower", null, mapLoader.posSimpleTowers2["ally"]!!,  0f))
        gameObjectList.add(troopFactory.getTroop(false, "simpleTower", null, mapLoader.posSimpleTowers2["enemy"]!!,  0f))
        gameObjectList.add(troopFactory.getTroop(false, "submarine", null,Pair(10f,10f),  0f))*/


        for (elem in gameObjectList) {
            if (elem.isEnemy()) {
                enemyTowersList.add(elem as Entity)
            } else {
                allyTowersList.add(elem as Entity)
            }
        }
        //temporary, initialisation will depend on choices made by player
    }






    fun update(elapsedTimeMS: Long) {

        if (STARTED){
            timeLeft -= elapsedTimeMS / 10000000000.0
            Log.d("GM", "$timeLeft")

            if (timeLeft <= 0) {
                endGame()
            }
            updateResourceBar(elapsedTimeMS)
            resources = getResourceBar()
            //takeAction(elapsedTimeMS, map) //TODO: might want to convert time into s
            //autonomousEnemyGeneration(map)
        }


    }


    fun takeAction(elapsedTimeMS: Long, map: Map) {
        for (entity in gameObjectList) {
            if (entity.isAlive()) {
                entity.takeAction(elapsedTimeMS, map)
                Log.d("GM", "ACTION TAKEN FOR " + entity.toString())
            }
        }
    }


    //TODO : define more complex generation pattern (preferably one that respects resources)
    fun autonomousEnemyGeneration(map: Map) {
        if (readyForEnemyGeneration()) {
            //gameObjectList.add(troopFactory.getTroop(true, "boat", null, Pair(0f, 0f), 0f))
        }
    }


    //checks whether set time between two enemy creations passed
    fun readyForEnemyGeneration(): Boolean {
        var ready = false
        val currentGenerationTime = System.currentTimeMillis()
        val deltaTime = (currentGenerationTime - previousEnemyGenerationTime)
        if (deltaTime > enemyGenerationFreq) {
            ready = true
            previousEnemyGenerationTime = currentGenerationTime
        }
        return ready
    }

    //TODO target at the end
    fun createProjectile(enemy: Boolean, type: String, target: Entity, coordinates: Pair<Float, Float>, orientation: Float) {
        gameObjectList.add(troopFactory.getTroop(enemy, type, coordinates, orientation, target))
    }


    fun getResourceBar(): Float {
        return resourceBar.checkResourceBar()
    }


    fun updateResourceBar(elapsedTimeMS: Long) {
        resourceBar.updateResourceBar(elapsedTimeMS)
    }


    fun updateEnemiesDestroyed() {
        enemyTowersDestroyed++
    }


    fun updateAlliesDestroyed() {
        allyTowersDestroyed++
    }


    fun saveCard(nbCard: Int) {
        nbCardClicked = nbCard
    }


    fun playCard(coordinates: Pair<Float, Float>) {
        cardManager.playCard(nbCardClicked, floor(resources.toDouble()), coordinates, map)
    }





    fun endGame() {
        if (allyTowersDestroyed < enemyTowersDestroyed) {
            setGameOver(true)
        } else if (allyTowersDestroyed > enemyTowersDestroyed) {
            setGameOver(false)
        } else
            setGameOver(null)
    }


    fun setGameOver(gameWon: Boolean?) {
        //GAMEOVER = false
        GAMEOVER = true
        if (gameWon == null) {
            //"Egalité"
        } else if (gameWon == true) {
            //"Vous avez gagné"
        } else {
            //"Vous ave perdu"
        }
    }


    /*override fun run(){
    var previousFrameTime = System.currentTimeMillis()

    while (running){
        val currentTime = System.currentTimeMillis()
        val elapsedTimeMS = (currentTime - previousFrameTime)
        timeLeft -= elapsedTimeMS/1000.0

        if(timeLeft <= 0){
            endGame()
        }
        updateResourceBar(elapsedTimeMS)
        resources = getResourceBar()
        takeAction(elapsedTimeMS, map)
        autonomousEnemyGeneration(map)
    }
}*/





}