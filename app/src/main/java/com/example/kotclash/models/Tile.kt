package com.example.kotclash.models

import android.graphics.RectF
import android.util.Log


class Tile(val xi : Float, val yi : Float, var tileElement : String) {

    var occupants : MutableList<GameObject> = mutableListOf()

    var x = xi
    var y = yi

    var position  = Pair(xi,yi)

    var endx = x
    var endy = y

    var cellRectangle: RectF = RectF(x, y, endx, endy)


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
        }
    }


    fun setOccupant(obj : GameObject){
        if (obj !in occupants){
            occupants.add(obj)//works
        }
    }


    fun getEntity() : MutableList<GameObject>{
        return occupants
    }

    override fun toString(): String {
        return position.toString()
    }

}

