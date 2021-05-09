package com.example.kotclash.views

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.Log
import com.example.kotclash.App
import com.example.kotclash.models.Map
import com.example.kotclash.R
import com.example.kotclash.models.GameManager


/**
 *Map drawer
 *
 * This class draw the background map
 *
 * @constructor initialize the bitmap resources
 */
class MapView() {

    var game = GameManager.gameInstance
    var paint = Paint()

    lateinit var grass: Bitmap
    lateinit var soil: Bitmap
    lateinit var wall: Bitmap
    lateinit var tree: Bitmap


    private var INIT : Boolean = false



    private fun initBitmaps() {

        when(game.currentMap){
            "spring" -> {
                grass = BitmapFactory.decodeResource(App.getContext().resources, R.drawable.grass)
                soil = BitmapFactory.decodeResource(App.getContext().resources, R.drawable.soil)
                wall = BitmapFactory.decodeResource(App.getContext().resources, R.drawable.dark_wall)
            }
            "lava" -> {
                grass = BitmapFactory.decodeResource(App.getContext().resources, R.drawable.lava_soil)
                soil = BitmapFactory.decodeResource(App.getContext().resources, R.drawable.brown_soil)
                wall = BitmapFactory.decodeResource(App.getContext().resources, R.drawable.lave)

            }
            "frost" -> {
                grass = BitmapFactory.decodeResource(App.getContext().resources, R.drawable.snow)
                soil = BitmapFactory.decodeResource(App.getContext().resources, R.drawable.snow)
                wall = BitmapFactory.decodeResource(App.getContext().resources, R.drawable.water)
                tree = BitmapFactory.decodeResource(App.getContext().resources, R.drawable.snowtree)

            }
        }

        INIT = true
    }

    /**
     * Draw the map
     */
    fun drawGrid(canvas: Canvas, map: Map) {

        if (!INIT){
            initBitmaps()
        }


        for (y in 0 until map.getRowSize()) {
            for (x in 0 until map.getColSize()) {

                val cell = map.grid[y][x]

                when (cell.tileElement) {
                    "grass" -> canvas.drawBitmap(grass, null, cell.cellRectangle, paint)
                    "soil" -> canvas.drawBitmap(soil, null, cell.cellRectangle, paint)
                    "wall" -> canvas.drawBitmap(wall, null, cell.cellRectangle, paint)
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

        map.posSetRect(rendW, rendH)
    }

}