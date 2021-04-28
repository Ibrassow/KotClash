package com.example.kotclash.models

import android.util.Log
import java.lang.IndexOutOfBoundsException
import kotlin.math.ceil


class Map()  {

    val grid = mutableListOf<MutableList<Tile>>()

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
                    grid[y][x].setOccupant(gameObject as Entity) //TODO we need to decide if we add objects or entities
                }
                catch(e: IndexOutOfBoundsException){
                    Log.d("Exception grid - place", "Index out of bounds")
                }
            }
        }

    }




    //returns the entities found in the given range
    fun scanArea(actualPos : Pair<Int, Int>, range: Int): MutableList<GameObject> {

        val objectFound = mutableListOf<GameObject>()

        val x = actualPos.first
        val y = actualPos.second

        //TODO add conditions for walls -- existence of cells
        for (column in y-range..y+range+1){
            for (row in x-range..x+range+1){
                if (grid[row][column].isOccupied()){
                    grid[row][column].getEntity().let { objectFound.addAll(it) }
                }
            }
        }

        return objectFound
    }


    fun showOccupancyGrid(){
        for (y in 0 until getRowSize()){
            for (x in 0 until getColSize()){
                if (grid[x][y].isOccupied()){
                    print(1)
                }
                else{print(0)}
                print(" ")
            }
            println("")
        }
        println("--------------------")
    }

    //TODO Check if movement is possible, where, etc
    fun displace(obj : GameObject, prevCoord : Pair<Float, Float>){
        var newPos = obj.coordinates

        val oldX = ceil(prevCoord.first/obj.oldRendW).toInt()
        val oldY = ceil(prevCoord.second/obj.oldRendH).toInt()


        try {
            grid[oldY][oldX].setOccupant(obj)
            Log.e("kk", "SUCCESS")
            //grid[actualPos.first][actualPos.second].removeOccupant() //free cell
            //grid[actualPos.first + dx][actualPos.second + dy].setOccupant(entity)
        }
        catch(e: IndexOutOfBoundsException){
            Log.d("Exception grid - displace", "Index out of bounds")
        }


        //grid[actualPos.first][actualPos.second].removeOccupant() //free cell
        //grid[actualPos.first + dx][actualPos.second + dy].setOccupant(entity)

    }







}

