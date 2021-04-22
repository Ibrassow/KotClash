package com.example.kotclash

import android.util.Log
import com.example.kotclash.models.Entity
import com.example.kotclash.models.GameObject
import java.lang.IndexOutOfBoundsException


class Map()  {

    val grid = mutableListOf<MutableList<Tile>>()

    fun getRowSize() : Int {
        return grid[0].size
    }

    fun getColSize() : Int {
        return grid.size
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

        for (x in xx-szx..xx+szx){
            for (y in yy-szy..yy+szy){
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
    fun scanArea(actualPos : Pair<Int, Int>, range: Int): MutableList<Entity> {

        val entityFound = mutableListOf<Entity>()

        val x = actualPos.first
        val y = actualPos.second

        //TODO add conditions for walls -- existence of cells
        for (column in y-range..y+range+1){
            for (row in x-range..x+range+1){
                if (grid[row][column].isOccupied()){
                    grid[row][column].getEntity().let { entityFound.addAll(it) }
                }
            }
        }

        return entityFound
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
    fun displace(entity : Entity, dx : Int, dy : Int){
        var actualPos = entity.coordinates

        //grid[actualPos.first][actualPos.second].removeOccupant() //free cell
        //grid[actualPos.first + dx][actualPos.second + dy].setOccupant(entity)

    }







}

