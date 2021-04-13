package com.example.kotclash

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint


class MapLoader(val map : Map, context : Context) {

    var grass : Bitmap //Don't join yet
    var ground : Bitmap

    var paint = Paint()

    init{
        // Don't touch if you don't want to die
        grass = BitmapFactory.decodeResource(context.resources, R.drawable.grass)
        ground = BitmapFactory.decodeResource(context.resources, R.drawable.ground)
    }




    fun drawGrid(canvas: Canvas) {

        for (x in 0 until map.rows) {
            for (y in 0 until map.cols) {

                //val xx = map.grid[x][y].position.first.toFloat()
                //val yy = map.grid[x][y].position.second.toFloat()

                val cell = map.grid[x][y]

                val xx = cell.position.first.toFloat()
                val yy = cell.position.second.toFloat()

                when(cell.tileElement) {

                    "grass" -> canvas.drawBitmap(grass, xx, yy, paint)
                    "ground" -> canvas.drawBitmap(ground, xx, yy, paint)

                }
            }
        }
    }


    // I repeat, Don't touch if you don't want to die
    //val grass : Bitmap = BitmapFactory.decodeResource(context.resources, com.example.kotclash.R.drawable.grass)











}