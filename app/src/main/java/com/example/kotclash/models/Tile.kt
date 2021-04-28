package com.example.kotclash.models

import android.graphics.RectF
import android.util.Log


class Tile(val xi : Float, val yi : Float, var tileElement : String) {


    //TODO -> GameObject preferably
    var occupants : MutableList<GameObject> = mutableListOf()

    var x = xi
    var y = yi

    var position  = Pair(xi,yi)

    //TODO change this attribute when reading the map
    var walkable : Boolean = true

    var endx = x
    var endy = y

    //Will serve later on
    var cellRectangle: RectF = RectF(x, y, endx, endy)

    init{
        if (tileElement == "grass"){
            walkable = false //for testing purposes
        }
    }

    // TODO A* Search
    /*val parentCell: Cell? = null
    var gCost = 10
    var hCost = 0
    var fCost = 0*/


    fun isOccupied(): Boolean{
        return occupants.isNotEmpty()
    }

    fun setRect(renderable_Width : Float, renderable_Height : Float){
        x = (xi * renderable_Width)
        y = (yi * renderable_Height)
        position = Pair(x,y)
        endx = x + renderable_Width
        endy = y + renderable_Height
        cellRectangle.set(x, y, endx, endy)
    }

    //TODO
    fun removeOccupant(){
        //occupant = null
    }


    fun setOccupant(obj : GameObject){
        if (obj !in occupants){
            occupants.add(obj)//works
        }
        //val m = occupants.size
        //Log.e("Tile", "size : $m")
    }


    /*fun dist(posE: Tile): Double {
        return sqrt((posE.x - x).toDouble().pow(2) + (posE.y - y).toDouble().pow(2))
    }*/

    //TODO -> GameObject preferably (rename)
    fun getEntity() : MutableList<GameObject>{
        return occupants
    }

    override fun toString(): String {
        return position.toString()
    }




}

