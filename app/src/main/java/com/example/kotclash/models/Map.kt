package com.example.kotclash.models

import java.lang.IndexOutOfBoundsException
import kotlin.math.ceil
import kotlin.math.pow
import kotlin.math.sqrt


class Map{


    val grid = mutableListOf<MutableList<Tile>>()

    //Only the base - doesn't care about the specific type of tower
    val posBases = mutableMapOf<String, Pair<Float, Float>>()
    val posAllyTower = mutableMapOf<Int, Pair<Float, Float>>() //Keep indices to match with future user choice
    val posEnemyTower = mutableMapOf<Int, Pair<Float, Float>>()
    val posAllySpawn = mutableMapOf<Int, Pair<Float, Float>>() //Keep indices to match with future user choice
    val posEnemySpawn = mutableMapOf<Int, Pair<Float, Float>>()
    val posGate = mutableMapOf<Int, Pair<Float, Float>>()
    val wallTag = mutableMapOf<Int, Pair<Float, Float>>()


    var slope : Float = 0f
    var originLine : Float = 0f

    //Don't change
    private var oldRendW = 1f
    private var oldRendH = 1f

    lateinit var name : String

    fun clearAllPos(){
        posBases.clear()
        posAllyTower.clear()
        posEnemyTower.clear()
        posAllySpawn.clear()
        posEnemySpawn.clear()
        posGate.clear()
        wallTag.clear()
    }

    fun getRowSize() : Int {
        return grid.size
    }

    fun getColSize() : Int {
        return grid[0].size
    }

    fun clearMap(){
        grid.clear()
    }


    //Should be used at the beginning for the tower
    fun placeTower(obj: GameObject){
        val xx = ((obj.coordinates.first)/obj.oldRendW).toInt()
        val yy = ((obj.coordinates.second)/obj.oldRendH).toInt()


        try {
            grid[yy][xx].setOccupant(obj)
            //Log.e("initTowersInMap","$obj")
        }
        catch(e: IndexOutOfBoundsException){
            //Log.d("Exception grid - place", "Index out of bounds")
        }


    }



    //returns the entities found in the given range
    fun scanArea(actualPos : Pair<Int, Int>, range: Int, obj: GameObject): MutableList<GameObject> {

        val objectFound = mutableListOf<GameObject>()

        val x = actualPos.first
        val y = actualPos.second

        for (row in y-range..y+range+1){
            for (column in x-range..x+range+1){
                try {
                    if (grid[row][column].isOccupied()) {
                        val entitiesScanned = grid[row][column].getEntity()
                        for(entityScanned in entitiesScanned){
                            if(entityScanned.isEnemyOf(obj)) {
                                objectFound.add(entityScanned)
                            }
                        }
                    }
                }catch(e:IndexOutOfBoundsException){}
            }
        }
        return objectFound
    }


    fun displace(obj : GameObject, prevCoord : Pair<Float, Float>){


        val oldX = ceil(prevCoord.first/obj.oldRendW).toInt()
        val oldY = ceil(prevCoord.second/obj.oldRendH).toInt()

        val newX = ceil(obj.coordinates.first/obj.oldRendW).toInt()
        val newY = ceil(obj.coordinates.second/obj.oldRendH).toInt()

        if(newX != oldX || newY != oldY){
            try {
                grid[newY][newX].setOccupant(obj)
                grid[oldY][oldX].removeOccupant(obj)
            }
            catch(e: IndexOutOfBoundsException){}

        }
    }


    private fun setCoeffFrontier(){

        slope = (wallTag[1]!!.second - wallTag[0]!!.second) / (wallTag[1]!!.first - wallTag[0]!!.first)
        if (name == "frost"){
            originLine = wallTag[1]!!.second
        } else {
            originLine = wallTag[0]!!.second
        }


    }


    private fun calculateFrontierPt(x : Float) : Float{
        return slope * x + originLine
    }


    fun onOwnSide(obj: GameObject): Boolean{
        val onMySide : Boolean

        val x = obj.coordinates.first
        val y = obj.coordinates.second
        val correspondingFrontierPt = calculateFrontierPt(x)

        onMySide = if (obj.isEnemy()){
            correspondingFrontierPt >= y
        }
        else{
            correspondingFrontierPt < y
        }

        return onMySide

    }


    fun posSetRect(rendW: Float, rendH: Float){

       posGate.forEach { (gate, pos) ->
           posGate[gate] = Pair(pos.first/oldRendW*rendW, pos.second/oldRendH*rendH)
       }
        wallTag.forEach { (gate, pos) ->
            wallTag[gate] = Pair(pos.first/oldRendW*rendW, pos.second/oldRendH*rendH)
        }

        /*posAllySpawn.forEach { (spawn, pos) ->
            posGate[spawn] = Pair(pos.first/oldRendW*rendW, pos.second/oldRendH*rendH)
        }*/

        setCoeffFrontier()

        oldRendW = rendW
        oldRendH = rendH

    }


    fun killEntity(obj : GameObject){
        val x = ceil(obj.coordinates.first/obj.oldRendW).toInt()
        val y = ceil(obj.coordinates.second/obj.oldRendH).toInt()
        grid[y][x].removeOccupant(obj)
    }


    private fun dist(c1: Pair<Float, Float>, c2: Pair<Float, Float>): Float {
    return sqrt((c1.first - c2.first).pow(2) + (c1.first - c2.first).pow(2))
    }



    fun getClosestGate(obj: GameObject): Pair<Float, Float>? {
        var gateChoice : Pair<Float, Float>? = null
        var minDist = 5000000f


        var currDist: Float

        posGate.forEach { (gate, _) ->
            currDist = dist(obj.coordinates, posGate[gate]!!)
            if (currDist<minDist){
                minDist = currDist
                gateChoice = posGate[gate]!!
            }
        }

        if (posGate.isEmpty()){
            gateChoice = null
        }


        return gateChoice
    }






}

