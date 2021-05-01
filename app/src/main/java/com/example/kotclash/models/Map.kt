package com.example.kotclash.models

import android.util.Log
import java.lang.IndexOutOfBoundsException
import kotlin.math.ceil


class Map()  {

    val grid = mutableListOf<MutableList<Tile>>()

    //Only the base - doesn't care about the specific type of tower
    val posBases = mutableMapOf<String, Pair<Float, Float>>()
    val posAllyTower = mutableMapOf<Int, Pair<Float, Float>>() //Keep indices to match with future user choice
    val posEnemyTower = mutableMapOf<Int, Pair<Float, Float>>()
    val posAllySpawn = mutableMapOf<Int, Pair<Float, Float>>() //Keep indices to match with future user choice
    val posEnemySpawn = mutableMapOf<Int, Pair<Float, Float>>()
    val posGate = mutableMapOf<Int, Pair<Float, Float>>()

    fun clearAllPos(){
        posBases.clear()
        posAllyTower.clear()
        posEnemyTower.clear()
        posAllySpawn.clear()
        posEnemySpawn.clear()
        posGate.clear()
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


    fun placeNewObject(gameObject: GameObject){
        //TODO opti after
        val sz = gameObject.size
        val szx = sz.first/2
        val szy = sz.second/2
        val xx = gameObject.coordinates.first.toInt()
        val yy = gameObject.coordinates.second.toInt()

        //TODO Clean here
        for (x in (xx-szx).toInt()..(xx+szx).toInt()){
            for (y in (yy-szy).toInt()..(yy+szy).toInt()){
                //to account for non-existing tiles
                try {
                    grid[y][x].setOccupant(gameObject)
                }
                catch(e: IndexOutOfBoundsException){
                    Log.d("Exception grid - place", "Index out of bounds")
                }
            }
        }
    }



    //returns the entities found in the given range
    fun scanArea(actualPos : Pair<Int, Int>, range: Int, obj: GameObject): MutableList<GameObject> {

        val objectFound = mutableListOf<GameObject>()

        //val x = actualPos.first
        //val y = actualPos.second

        val x = ceil(obj.coordinates.first.toDouble()/obj.oldRendW).toInt()
        val y = ceil(obj.coordinates.second.toDouble()/obj.oldRendH).toInt()

        //TODO add conditions for walls -- existence of cells
        for (column in y-range..y+range+1){
            for (row in x-range..x+range+1){
                try {
                    if (grid[row][column].isOccupied()) {
                        grid[row][column].getEntity().let { objectFound.addAll(it) }
                    }
                }catch(e:IndexOutOfBoundsException){
                    Log.d("E: ScanArea", "Index out of bounds")
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
                Log.d("E: Grid displace", "Index out of bounds")
            }


        }



    }







}

