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

    fun setRect(renderableWidth : Float, renderableHeight : Float){
        x = (xi * renderableWidth)
        y = (yi * renderableHeight)
        position = Pair(x,y)
        endx = x + renderableWidth
        endy = y + renderableHeight
        cellRectangle.set(x, y, endx, endy)
    }

    fun removeOccupant(obj: GameObject){
        var idx : Int? = null
        for (i in 0 until (occupants.size)){
            if (occupants[i].ix == obj.ix){
                idx = i
            }
        }

        if (idx != null){
            occupants.removeAt(idx)
            //Log.e("occupant Removed","$occupants in ($xi,$yi)")
        }
    }


    fun setOccupant(obj : GameObject){
        if (obj !in occupants){
            occupants.add(obj)//works
        }
        //Log.e("occupant Set","$occupants in ($xi,$yi)")
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

