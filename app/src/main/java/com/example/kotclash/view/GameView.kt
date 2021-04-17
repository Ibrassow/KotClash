package com.example.kotclash.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.example.kotclash.Map
import com.example.kotclash.MapLoader


class GameView @JvmOverloads constructor (context: Context, attributes: AttributeSet? = null, defStyleAttr: Int = 0) : SurfaceView(context, attributes,defStyleAttr), SurfaceHolder.Callback, Runnable {

    lateinit var canvas: Canvas
    lateinit var thread: Thread

    //Map
    var map : Map = Map()
    var mapLoader: MapLoader = MapLoader(context, "spring", this)
    val mapView = MapView(context)

    val backgroundPaint = Paint()


    var screenWidth = 0f
    var screenHeight = 0f
    var drawing : Boolean = true



    init{
        backgroundPaint.color = Color.WHITE
        map = mapLoader.returnMap()

    }

    fun notifyView(){

    }


    override fun run(){

        while(drawing){

           draw()
        }
    }



    fun draw(){

        if (holder.surface.isValid) {
            canvas = holder.lockCanvas()
            canvas.drawRect(0f, 0f, canvas.width.toFloat(),
                    canvas.height.toFloat(), backgroundPaint)

            mapView.drawGrid(canvas, map)

            //Ultra-important
            holder.unlockCanvasAndPost(canvas)
    }
}





    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        thread = Thread(this)
        thread.start()

    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        thread.join()
    }


    fun pause() {
        drawing = false
        thread.join()
    }

    fun resume() {
        drawing = true
        thread = Thread(this)
        thread.start()

    }



    override fun onSizeChanged(w:Int, h:Int, oldw:Int, oldh:Int) {

        super.onSizeChanged(w, h, oldw, oldh)

        screenWidth = w.toFloat()
        screenHeight = h.toFloat()

        mapView.setRects(map, screenWidth, screenHeight)
        //Log.d("ow", "screenwWidth : $screenWidth")


    }


}

interface Renderable {
    fun draw(canvas: Canvas?)

    companion object {
        const val RENDERABLE_WIDTH = 50
        const val RENDERABLE_HEIGHT = 50
    }
}


