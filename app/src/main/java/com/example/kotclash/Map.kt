package com.example.kotclash



class Map(val mapWidth: Int = 10, val mapHeight: Int = 10)  {

    //I keep them to remind me something
    val mapPixelWidth = mapWidth
    val mapPixelHeight = mapHeight
    val rows = mapWidth / Renderable.RENDERABLE_WIDTH
    val cols = mapHeight / Renderable.RENDERABLE_HEIGHT

    val grid = mutableListOf<MutableList<Tile>>()


    /*fun createMap() {
        var x = 0
        var y = 0
        for (i in 0..rows){
            grid.add(mutableListOf())
            for (j in 0..cols){
                //grid[i][j] = Tile(x,y)
                grid[i].add(Tile(x,y))
                y += Renderable.RENDERABLE_HEIGHT
            }
            y = 0
            x += Renderable.RENDERABLE_WIDTH
        }
    }*/

    fun getRowSize() : Int {
        return grid.size
    }

    fun getColSize() : Int {
        return grid[0].size
    }

    /*fun scanArea(actualPos : Pair<Double, Double>, range: Int): ArrayList<Entity>{
        //TODO

    }*/












}

/*class Map(val size_x: Int = 10, val size_y: Int = 10) {
    val grid : Array<Array<Tile?>> = Array<Array<Tile?>>(size_x){Array<Tile?>(size_y){null} }
    //@ return the enttities found within the given range
    fun scanArea(actualPos : Pair<Int, Int>, range: Int): MutableList<Entity>? {
        val entityFound = mutableListOf<Entity>()
        val x = actualPos.first
        val y = actualPos.second
        //TODO add conditions for walls -- existence of cells
        for (column in y-range..y+range+1){
            for (row in x-range..x+range+1){
                if (grid[row][column]?.isOccupied() == true){
                    grid[row][column]?.getEntity()?.let { entityFound.add(it) }
                }
            }
        }
        return entityFound
    }
    fun showOccupancyGrid(){
        for (y in 0 until size_y){
            for (x in 0 until size_x){
                //print(grid[x][y]?.isOccupied())
                if (grid[x][y]?.isOccupied() == true){
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
        var actualPos = entity.position
        grid[actualPos.first][actualPos.second]?.removeOccupant() //free cell
        grid[actualPos.first + dx][actualPos.second + dy]?.setOccupant(entity)
    }*/
//}