package com.example.kotclash.models

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import com.example.kotclash.App
import com.example.kotclash.activities.GameActivity
import com.example.kotclash.activities.StartActivity
import com.google.android.material.internal.ContextUtils.getActivity
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


    private var GAMEOVER = false
    var STARTED = false


    private val enemyGenerationFreq : Long = 10
    var previousEnemyGenerationTime = System.currentTimeMillis()
    var resources = 2000000000000f //Test



    //this variable stores the nb of the card clicked on
    var nbCardClicked = 0

    var enemyTowersDestroyed = 0
    var allyTowersDestroyed = 0


    val resourceBar = ResourceBar()

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
            if (elem.isEnemy()) {
                enemyTowersList.add(elem as Tower)
            } else {
                allyTowersList.add(elem as Tower)
            }

            map.placeTowers(elem)
        }
        //temporary, initialisation will depend on choices made by player
    }






    fun update(elapsedTimeMS: Long) {

        if (STARTED){
            timeLeft -= (elapsedTimeMS / 100.0)
            Log.d("GM", "time : $elapsedTimeMS")
            Log.d("GM", "time : $timeLeft")

            if (timeLeft <= 0) {
                endGame()
            }
            updateResourceBar(elapsedTimeMS)
            resources = getResourceBar()
            takeAction(elapsedTimeMS, map) //TODO: might want to convert time into s
            autonomousEnemyGeneration(map)

            val nn = gameObjectList.size
            Log.e("sizeObjList", "$nn")
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
            gameObjectList.add(troopFactory.getTroop(true, "submarine", map.posEnemySpawn[nbRand]!!))
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


    fun playCard(nbCard : Int) {
        val nbRand = kotlin.random.Random.Default.nextInt(3)
        cardManager.playCard(nbCard, floor(resources.toDouble()), map.posAllySpawn[nbRand]!!)

        //cardManager.playCard(nbCardClicked, floor(resources.toDouble()), coordinates)
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
            destroy()
        } else if (gameWon == true) {
            //"Vous avez gagné"
        } else {
            //"Vous avez perdu"

        }
    }


}