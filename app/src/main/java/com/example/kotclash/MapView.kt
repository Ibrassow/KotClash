package com.example.kotclash

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.Log

class MapView(val context : Context) {

    var paint = Paint()


    // I repeat, Don't touch if you don't want to die
    //val grass : Bitmap = BitmapFactory.decodeResource(context.resources, com.example.kotclash.R.drawable.grass)

    fun drawGrid(canvas: Canvas, map : Map) {

        val grass = BitmapFactory.decodeResource(context.resources, R.drawable.grass)
        val soil = BitmapFactory.decodeResource(context.resources, R.drawable.soil)

        for (x in 0 until map.getRowSize()) {
            for (y in 0 until map.getColSize()) {


                val cell = map.grid[x][y]

                val xx = cell.position.first.toFloat()
                val yy = cell.position.second.toFloat()

                when(cell.tileElement) {

                    "grass" -> canvas.drawBitmap(grass, xx, yy, paint)
                    "soil" -> canvas.drawBitmap(soil, xx, yy, paint)

                }
            }
        }
    }

    fun setRects(map : Map, w : Float, h : Float){

        val rendW = (w / map.getRowSize()).toFloat()
        val rendH = (h / map.getColSize()).toFloat()

        for (x in 0 until map.getColSize()) {
            for (y in 0 until map.getRowSize()) {
                map.grid[x][y].setRect(rendW, rendH)
                Log.d("inMapView", "Doing the setRect : $rendW --- $rendH")
            }
        }
    }



}