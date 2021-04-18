package com.example.kotclash

import com.example.kotclash.models.*

class GameManager: Runnable {

    init{
        //initEntityList()
    }

    private lateinit var thread: Thread
    private var GAMEOVER = false
    var running = false

    val mapLoader: MapLoader = MapLoader()
    val grid = Map() //TODO: temporary


    private val enemyGenerationFreq = 0f
    var previousEnemyGenerationTime = System.currentTimeMillis()
    var resources = 0f


    //card not played when clicked on but when player clicks on apparition spot
    //this variable stores the nb of the card clicked on
    var nbCardClicked = 0


    //TODO : define spots
    val rightGenerationSpot = listOf(0f,0f)
    val leftGenerationSpot = listOf(0f,0f)


    var enemyTowersDestroyed = 0
    var allyTowersDestroyed = 0


    private val resourceBar = ResourceBar()

    var timeLeft = 0.0

    /////////////////////////
    val troopFactory = TroopFactory(this)  //requires view?
    val cardManager = CardManager(troopFactory, this) //TODO: might need to be in MainActivity instead
    val gameObjectList = ArrayList<GameObject>()
    val enemyTowersList = ArrayList<Entity>() //to use fctn already def for entities
    val allyTowersList = ArrayList<Entity>()

    fun setMap(mapName : String){

    }

    override fun run(){
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
            takeAction(elapsedTimeMS, grid) //TODO: might want to convert time into s
            autonomousEnemyGeneration(grid)
        }
    }


    fun takeAction(elapsedTimeMS: Long, grid:Map){
        for (entity in gameObjectList){
            if(entity.isAlive()){
                entity.takeAction(elapsedTimeMS,grid)
            }
        }
    }


    //TODO : define more complex generation pattern (preferably one that respects resources)
    fun autonomousEnemyGeneration(grid:Map){
        if(readyForEnemyGeneration()){
            gameObjectList.add(troopFactory.getTroop(true,"boat",null, Pair(0f,0f), 0f))
        }
    }


    //checks whether set time between two enemy creations passed
    fun readyForEnemyGeneration(): Boolean{
        var ready = false
        val currentGenerationTime = System.currentTimeMillis()
        val deltaTime = (currentGenerationTime - previousEnemyGenerationTime)
        if (deltaTime > enemyGenerationFreq){
            ready = true
            previousEnemyGenerationTime = currentGenerationTime
        }
        return ready
    }


    fun createProjectile(enemy:Boolean, type: String, target: Entity, coordinates: Pair<Float,Float>, orientation: Float){
        gameObjectList.add(troopFactory.getTroop(enemy,type,target,coordinates,orientation))
    }


    fun getResourceBar(): Float{
        return resourceBar.checkResourceBar()
    }


    fun updateResourceBar(elapsedTimeMS: Long){
        resourceBar.updateResourceBar(elapsedTimeMS)
    }


    fun updateEnemiesDestroyed(){
        enemyTowersDestroyed++
    }


    fun updateAlliesDestroyed(){
        allyTowersDestroyed++
    }


    fun saveCard(nbCard:Int){
        nbCardClicked = nbCard
    }


    fun playCard(coordinates: Pair<Float,Float>){
        cardManager.playCard(nbCardClicked,kotlin.math.floor(resources.toDouble()), coordinates, grid)
    }


    //TODO
    fun initEntityList(){
        //here one base per side and two simpleTowers
        gameObjectList.add(troopFactory.getTroop(true,"simpleTower",null, Pair(0f,0f), 0f ))
        gameObjectList.add(troopFactory.getTroop(false,"simpleTower",null, Pair(0f,0f),0f))
        gameObjectList.add(troopFactory.getTroop(true,"simpleTower",null, Pair(0f,0f),0f))
        gameObjectList.add(troopFactory.getTroop(false,"simpleTower",null, Pair(0f,0f),0f))
        gameObjectList.add(troopFactory.getTroop(true,"baseTower",null, Pair(0f,0f),0f))
        gameObjectList.add(troopFactory.getTroop(false,"baseTower",null, Pair(0f,0f),0f))


        for(elem in gameObjectList){
            if(elem.isEnemy()){
                enemyTowersList.add(elem as Entity)
            }else{
                allyTowersList.add(elem as Entity)
            }
        }
        //temporary, initialisation will depend on choices made by player
    }


    fun endGame(){
        if(allyTowersDestroyed < enemyTowersDestroyed){
            setGameOver(true)
        }else if(allyTowersDestroyed > enemyTowersDestroyed){
            setGameOver(false)
        }else
            setGameOver(null)
    }


    fun setGameOver(gameWon : Boolean?){
        //GAMEOVER = false
        GAMEOVER = true
        if(gameWon == null){
            //"Egalité"
        }else if (gameWon == true){
            //"Vous avez gagné"
        }else{
            //"Vous ave perdu"
        }
    }


    fun pause() {
        running = false
        thread.join()
    }


    fun resume() {
        running = true
        thread = Thread(this)
        thread.start()
    }

}