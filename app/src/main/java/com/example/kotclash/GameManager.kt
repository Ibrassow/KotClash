package com.example.kotclash

import android.util.Log
import com.example.kotclash.models.*

class GameManager {


    //"Singleton" - So we get a single instance of the game
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


    private val enemyGenerationFreq = 0f
    var previousEnemyGenerationTime = System.currentTimeMillis()
    var resources = 0f


    //card not played when clicked on but when player clicks on apparition spot
    //this variable stores the nb of the card clicked on
    var nbCardClicked = 0


    //TODO : define spots
    val rightGenerationSpot = listOf(0f, 0f)
    val leftGenerationSpot = listOf(0f, 0f)


    var enemyTowersDestroyed = 0
    var allyTowersDestroyed = 0


    private val resourceBar = ResourceBar()

    var timeLeft = 0.0

    /////////////////////////
    val troopFactory = TroopFactory(this)
    val cardManager = CardManager(troopFactory, this)
    val gameObjectList = mutableListOf<GameObject>()
    val enemyTowersList = mutableListOf<Entity>() //to use fctn already def for entities
    val allyTowersList = mutableListOf<Entity>()

    // -------------------- INIT ------------------- //


    init {
        initEntityList()

    }



    //TODO
    fun initEntityList() {

        //Verif - default map
        if (map.grid.isEmpty()){
            setMap("spring")
        }


        //here one base per side and two simpleTowers
        gameObjectList.add(troopFactory.getTroop(true, "base", null, mapLoader.posBases["enemy"]!!, 0f))
        gameObjectList.add(troopFactory.getTroop(false, "base", null, mapLoader.posBases["ally"]!!, 0f))
        /*gameObjectList.add(troopFactory.getTroop(true, "simpleTower", null, Pair(0f, 0f), 0f))
        gameObjectList.add(troopFactory.getTroop(false, "simpleTower", null, Pair(0f, 0f), 0f))
        gameObjectList.add(troopFactory.getTroop(true, "simpleTower", null, Pair(0f, 0f), 0f))
        gameObjectList.add(troopFactory.getTroop(false, "simpleTower", null, Pair(0f, 0f), 0f))*/



        for (elem in gameObjectList) {
            if (elem.isEnemy()) {
                enemyTowersList.add(elem as Entity)
            } else {
                allyTowersList.add(elem as Entity)
            }
        }
        //temporary, initialisation will depend on choices made by player
    }


    fun setMap(mapName: String) {
        mapLoader.loadMap(mapName)
        map = mapLoader.returnMap()
        val ss = map.grid.isNotEmpty()
        Log.d("InitGM", "got map : $ss")
    }



    fun update(elapsedTimeMS: Long) {

        timeLeft -= elapsedTimeMS / 1000.0
        Log.d("GM", "$timeLeft")

        if (timeLeft <= 0) {
            endGame()
        }
        updateResourceBar(elapsedTimeMS)
        resources = getResourceBar()
        takeAction(elapsedTimeMS, map) //TODO: might want to convert time into s
        autonomousEnemyGeneration(map)

    }


    fun takeAction(elapsedTimeMS: Long, map: Map) {
        for (entity in gameObjectList) {
            if (entity.isAlive()) {
                entity.takeAction(elapsedTimeMS, map)
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


    fun createProjectile(enemy: Boolean, type: String, target: Entity, coordinates: Pair<Float, Float>, orientation: Float) {
        gameObjectList.add(troopFactory.getTroop(enemy, type, target, coordinates, orientation))
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
        cardManager.playCard(nbCardClicked, kotlin.math.floor(resources.toDouble()), coordinates, map)
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