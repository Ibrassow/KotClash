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
    var STARTED = true


    private val enemyGenerationFreq = 0f
    var previousEnemyGenerationTime = System.currentTimeMillis()
    var resources = 2000000000000f //Test



    //this variable stores the nb of the card clicked on
    var nbCardClicked = 0


    //TODO : define spots
    val rightGenerationSpot = listOf(0f, 0f)
    val leftGenerationSpot = listOf(0f, 0f)


    var enemyTowersDestroyed = 0
    var allyTowersDestroyed = 0


    val resourceBar = ResourceBar()

    var timeLeft = 180.0

    lateinit var currentMap:String

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
        currentMap = mapName
    }


    fun initializeObjects() {

        //default map
        if (map.grid.isEmpty()){
            setMap("spring")
        }


        //Two bases - One per side
        gameObjectList.add(troopFactory.getTroop(true, "base", mapLoader.posBases["enemy"]!!))
        gameObjectList.add(troopFactory.getTroop(false, "base", mapLoader.posBases["ally"]!!))

        //Additional towers for the enemy side
        for (position in mapLoader.posEnemyTower){
            gameObjectList.add(troopFactory.getTroop(true, "simpleTower", position.value))
        }

        //Additional towers for the ally side
        for (position in mapLoader.posAllyTower){
            gameObjectList.add(troopFactory.getTroop(false, "simpleTower", position.value))
        }



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
            timeLeft -= (elapsedTimeMS / 1000.0)
            Log.d("GM", "time : $elapsedTimeMS")
            Log.d("GM", "time : $timeLeft")

            if (timeLeft <= 0) {
                endGame()
            }
            updateResourceBar(elapsedTimeMS)
            resources = getResourceBar()
            takeAction(elapsedTimeMS, map) //TODO: might want to convert time into s
            //autonomousEnemyGeneration(map)

            val nn = gameObjectList.size
            Log.e("sizeObjList", "$nn")
        }


    }


    fun takeAction(elapsedTimeMS: Long, map: Map) {
        for (obj in gameObjectList) {
            if (obj.isAlive()) {
                obj.takeAction(elapsedTimeMS, map)
                Log.d("GM", "ACTION TAKEN FOR " + obj.toString())
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
        cardManager.playCard(nbCard, floor(resources.toDouble()), mapLoader.posAllySpawn[nbRand]!!)
        resourceBar.useResource(15)
        //cardManager.playCard(nbCardClicked, floor(resources.toDouble()), coordinates)
    }




    //Pas trop compris ici
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