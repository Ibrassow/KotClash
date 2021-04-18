package com.example.kotclash.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.example.kotclash.Map
import com.example.kotclash.MapLoader
import com.example.kotclash.GameManager


class GameView @JvmOverloads constructor (context: Context, attributes: AttributeSet? = null, defStyleAttr: Int = 0) : SurfaceView(context, attributes,defStyleAttr), SurfaceHolder.Callback {


    lateinit var game : GameManager

    lateinit var canvas: Canvas
    lateinit var thread: Thread

    //Map
    var map : Map = Map()
    var mapLoader: MapLoader = MapLoader()
    val mapView = MapView()

    val backgroundPaint = Paint()

    var screenWidth = 0f
    var screenHeight = 0f
    var drawing : Boolean = true

    init{

        //Temporary
        mapLoader.loadMap("spring")
        map = mapLoader.returnMap()
        backgroundPaint.color = Color.WHITE
        Log.d("map", "test")
    }


    fun bindToGame(g : GameManager){
        game = g
    }


    /*override fun run(){
        while(drawing){
           //draw()
        }
    }*/

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        draw() //onDraw isn't always called with invalidate.. strange
    }



    fun draw(){

        if (holder.surface.isValid) {
            canvas = holder.lockCanvas()
            canvas.drawRect(0f, 0f, canvas.width.toFloat(),
                    canvas.height.toFloat(), backgroundPaint)

            //mapView.drawGrid(canvas, map)
            mapView.drawGrid(canvas, game.grid)

            //Ultra-important
            holder.unlockCanvasAndPost(canvas)
    }
}

    //Future
    fun changeMap(mapName : String){
        mapLoader.loadMap("spring")
        map = mapLoader.returnMap()
    }


    override fun onSizeChanged(w:Int, h:Int, oldw:Int, oldh:Int) {

        super.onSizeChanged(w, h, oldw, oldh)

        screenWidth = w.toFloat()
        screenHeight = h.toFloat()

        //mapView.setRects(map, screenWidth, screenHeight)
        mapView.setRects(game.grid, screenWidth, screenHeight)
        //Log.d("ow", "screenwWidth : $screenWidth")

    }


    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        //thread = Thread(this)
        //thread.start()

    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        //thread.join()
    }


    /*fun pause() {
        drawing = false
        thread.join()
    }

    fun resume() {
        drawing = true
        thread = Thread(this)
        thread.start()

    }*/


}


