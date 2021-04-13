package com.example.kotclash



class Map(val mapWidth: Int = 20, val mapHeight: Int = 20) {


    val mapPixelWidth = mapWidth
    val mapPixelHeight = mapHeight

    val rows = mapWidth / Renderable.RENDERABLE_WIDTH
    val cols = mapHeight / Renderable.RENDERABLE_HEIGHT


    val grid = mutableListOf<MutableList<Tile>>()

     init {

        createMap()

     }

    fun createMap() {
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

    }













}

/*class Map(val size_x: Int = 10, val size_y: Int = 10) {
    val grid : Array<Array<Tile?>> = Array<Array<Tile?>>(size_x){Array<Tile?>(size_y){null} }



    //Array(size_x) { Array(size_y) {null} }

    init{
        createMap()
    }


    fun createMap() {

        for (y in 0 until size_y){
            for (x in 0 until size_x){
                grid[x][y] = Tile(x,y)
            }
        }

    }


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



//Don't even think

/*    fun drawGrid(canvas: Canvas, context : Context) {

        // Don't touch if you don't want to die
        val grass : Bitmap = BitmapFactory.decodeResource(context.resources, com.example.kotclash.R.drawable.grass)

        for (x in 0 until rows) {
            for (y in 0 until cols) {
                val xx = grid[x][y].position.first.toFloat()
                val yy = grid[x][y].position.second.toFloat()
                canvas.drawBitmap(grass, xx, yy, paint)

            }
        }
    }*/
