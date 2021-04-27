package com.example.kotclash.views

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.Log
import com.example.kotclash.App
import com.example.kotclash.models.Map
import com.example.kotclash.R


/**
 *Map drawer
 *
 * This class draw the background map
 *
 * @constructor initialize the bitmap resources
 */
class MapView() {


    var paint = Paint()

    lateinit var grass: Bitmap
    lateinit var soil: Bitmap


    init {
        initBitmaps()
    }

    fun initBitmaps() {
        //TODO Handle failure
        grass = BitmapFactory.decodeResource(App.getContext().resources, R.drawable.grass)
        soil = BitmapFactory.decodeResource(App.getContext().resources, R.drawable.soil)
    }

    /**
     * Draw the map
     */
    fun drawGrid(canvas: Canvas, map: Map) {

        for (y in 0 until map.getRowSize()) {
            for (x in 0 until map.getColSize()) {

                val cell = map.grid[y][x]
                //val xx = cell.position.first
                //val yy = cell.position.second

                when (cell.tileElement) {
                    //"grass" -> canvas.drawBitmap(grass, xx, yy, paint) //Keep for preference - different visual effect
                    //"soil" -> canvas.drawBitmap(soil, xx, yy, paint)
                    "grass" -> canvas.drawBitmap(grass, null, cell.cellRectangle, paint)
                    "soil" -> canvas.drawBitmap(soil, null, cell.cellRectangle, paint)

                }
            }
        }
    }

    /**
     * Rescale each tile to the new screen size
     */
    fun setRects(map: Map, w: Float, h: Float) {

        val cols = map.getColSize()
        val rows = map.getRowSize()

        val rendW = (w / cols)
        val rendH = (h / rows)

        for (y in 0 until rows) {
            for (x in 0 until cols) {
                Log.d("StRcts", "x : $x, y : $y")
                map.grid[y][x].setRect(rendW, rendH)
            }
        }
    }

}