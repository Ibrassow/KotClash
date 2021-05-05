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
        fun destroy() {
            instance = null

        }
    }


    //Map
    var map = Map()
    var mapLoader: MapLoader = MapLoader()


    var GAMEOVER = false
    var STARTED = false


    private val enemyGenerationFreq : Long = 3
    var previousEnemyGenerationTime = System.currentTimeMillis()
    var resources = 0f
    val speedFill = 1/10f
    private val RESOURCESMAX = 100f


    var enemyTowersDestroyed = 0
    var allyTowersDestroyed = 0

    var cardClicked: String? = null

    var timeLeft = 180.0

    lateinit var currentMap: String

    /////////////////////////
    val troopFactory = TroopFactory(this)
    val cardManager = CardManager(troopFactory, this)
    val gameObjectList = mutableListOf<GameObject>()
    val enemyTowersList = mutableListOf<GameObject>()
    val allyTowersList = mutableListOf<GameObject>()

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
            if (elem is Tower){ //Simple check
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
            timeLeft -= (elapsedTimeMS/1000)
            Log.d("GM", "time : $elapsedTimeMS")
            Log.d("GM", "time : $timeLeft")

            if (timeLeft <= 0) {
                endGame()
            }
            updateResource(elapsedTimeMS)
            takeAction(elapsedTimeMS, map) //TODO: might want to convert time into s
            autonomousEnemyGeneration(map)

            val nn = gameObjectList.size
            //Log.e("sizeObjList", "$nn")
        }


    }


    fun takeAction(elapsedTimeMS: Long, map: Map) {
        for (obj in gameObjectList) {
            if (obj.isAlive()) {
                obj.takeAction(elapsedTimeMS, map)
            }
        }
    }



    fun autonomousEnemyGeneration(map: Map) {
        if (readyForEnemyGeneration()) {
            val nbRand = kotlin.random.Random.Default.nextInt(3)  //TODO : define more complex generation pattern (preferably one that respects resources)
            gameObjectList.add(troopFactory.getTroop(true, "soldier", map.posEnemySpawn[nbRand]!!))
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


    //TODO target at the end
    fun createProjectile(enemy: Boolean, type: String, target: Entity, coordinates: Pair<Float, Float>) {
        gameObjectList.add(troopFactory.getTroop(enemy, type, coordinates, target))
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


    /*fun playCard(nmCard : String) {
        val nbRand = kotlin.random.Random.Default.nextInt(3)
        cardManager.playCard(nmCard, floor(resources.toDouble()), map.posAllySpawn[nbRand]!!)
        val v = map.posAllySpawn[nbRand]!!
        Log.e("OKAYBOY", "$v")

    }*/


    fun playCard(coordinates: Pair<Float, Float>){
        if(cardClicked != null){
            cardManager.playCard(cardClicked!!, floor(resources.toDouble()), coordinates)
            cardClicked = null
        }
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
            //destroy()
        } else if (gameWon == true) {
            Log.e("WIN", "YEAH")
            //"Vous avez gagné"
        } else {
            Log.e("LOSE", "No..")
            //"Vous avez perdu"

        }
    }


}