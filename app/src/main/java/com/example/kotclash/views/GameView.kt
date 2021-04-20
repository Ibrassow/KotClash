package com.example.kotclash.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.example.kotclash.GameManager
import com.example.kotclash.GameThread
import com.example.kotclash.Map
import com.example.kotclash.MapLoader


class GameView @JvmOverloads constructor (context: Context, attributes: AttributeSet? = null, defStyleAttr: Int = 0) : SurfaceView(context, attributes,defStyleAttr), SurfaceHolder.Callback {


    lateinit var game : GameManager

    //lateinit var canvas: Canvas
    lateinit var thread: GameThread

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

        holder.addCallback( this)
        this.isFocusable = true
        thread = GameThread(holder, this)

    }


    fun bindToGame(g : GameManager){
        game = g
    }


    /*override fun run(){
        while(drawing){
           //draw()
        }
    }*/

   /* override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        //draw() //onDraw isn't always called with invalidate.. strange
    }*/

    fun update(timeElasped: Long){
        game.update(timeElasped)
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)

        canvas!!.drawRect(0f, 0f, width.toFloat(),
                    height.toFloat(), backgroundPaint)

        mapView.drawGrid(canvas, game.grid)
        Log.d("View", "GameView drawing")
    }

    /*fun draw(){

        if (holder.surface.isValid) {
            canvas = holder.lockCanvas()
            canvas.drawRect(0f, 0f, canvas.width.toFloat(),
                    canvas.height.toFloat(), backgroundPaint)

            //mapView.drawGrid(canvas, map)
            mapView.drawGrid(canvas, game.grid)
            Log.d("View", "GameView drawing")

            //Ultra-important
            holder.unlockCanvasAndPost(canvas)
    }
}*/

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
        Log.d("GameView", "surface created")
        thread = GameThread(getHolder(), this)
        thread.setRunning(true)
        thread.start()
        //thread = Thread(this)
        //thread.start()

    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {

        thread.setRunning(false)
        thread.join()

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



