package com.example.kotclash

import com.example.kotclash.models.Entity


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


    //@ return the enttities found within the given range
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

