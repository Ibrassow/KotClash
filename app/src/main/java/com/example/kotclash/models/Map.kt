package com.example.kotclash.models

import android.util.Log
import java.lang.IndexOutOfBoundsException
import kotlin.math.ceil
import kotlin.math.pow
import kotlin.math.sqrt


class Map()  {


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

    var name : String = ""
    set(name) {
        if (name == "frost") {
            /*val p1 = posGate[0]!!
            val p2 = posGate[1]!!
            posGate.clear()
            posGate[0] = p2
            posGate[1] = p1*/

            val a1 = posAllySpawn[0]!!
            val a2 = posAllySpawn[1]!!
            posAllySpawn.clear()
            posAllySpawn[0] = a2
            posAllySpawn[1] = a1
        }
        field = name
    }

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
        }
        catch(e: IndexOutOfBoundsException){
        }


    }



    //returns the entities found in the given range
    fun scanArea(actualPos : Pair<Int, Int>, range: Int, obj: GameObject): MutableList<GameObject> {

        val objectFound = mutableListOf<GameObject>()

        val x = actualPos.first
        val y = actualPos.second

        //TODO add conditions for walls -- existence of cells
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
                }catch(e:IndexOutOfBoundsException){
                    val xx = row
                    val yy = column
                    //Log.d("E: ScanArea", "X : $xx, Y: $yy")
                    //Log.e("E: ScanArea", "Index out of bounds")
                }
            }
        }
        return objectFound
    }


    //TODO Check if movement is possible, where, etc
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
            catch(e: IndexOutOfBoundsException){
                //Log.d("E: Grid displace", "Index out of bounds : OLD : ($oldX, $oldY) - NEW : ($newX, $newY)")
            }

        }
    }


    private fun setCoeffFrontier(){

        //slope = (wallTag[1]!!.second - wallTag[0]!!.second) / (wallTag[1]!!.first - wallTag[0]!!.first)
        slope = (posGate[1]!!.second - posGate[0]!!.second) / (posGate[1]!!.first - posGate[0]!!.first)

        originLine = wallTag[0]!!.second

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
    return sqrt((c1.first - c2.first).pow(2) + (c1.second - c2.second).pow(2))
    }



    fun getClosestGate(obj: GameObject): Pair<Float, Float>? {
        var gateChoice : Pair<Float, Float>? = null
        var minDist = 500000000f


        var currDist: Float

        posGate.forEach { (gate, _) ->
            currDist = dist(obj.coordinates, posGate[gate]!!)
            val oo = obj.type
            val ee = obj.coordinates
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

