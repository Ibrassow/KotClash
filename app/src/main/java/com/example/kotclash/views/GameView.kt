package com.example.kotclash.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.ProgressBar
import com.example.kotclash.GameManager
import com.example.kotclash.GameThread
import com.example.kotclash.Map
import com.example.kotclash.MapLoader
import com.example.kotclash.models.GameObject


class GameView @JvmOverloads constructor (context: Context, attributes: AttributeSet? = null, defStyleAttr: Int = 0) : SurfaceView(context, attributes,defStyleAttr), SurfaceHolder.Callback {


    lateinit var game : GameManager
    lateinit var progressBar: ProgressBar

    //lateinit var canvas: Canvas
    var thread: GameThread

    //Map
    var map : Map = Map()
    val mapLoader: MapLoader = MapLoader()
    val mapView = MapView()

    val objectDrawer : GameObjectView = GameObjectView(this)

    //misc
    val backgroundPaint = Paint()
    var screenWidth = 0f
    var screenHeight = 0f

    init{
        backgroundPaint.color = Color.WHITE

        //Temporary
        /*mapLoader.loadMap("spring")
        map = mapLoader.returnMap()*/
        //Log.d("map", "test")

        holder.addCallback( this)
        this.isFocusable = true
        thread = GameThread(holder, this, progressBar)

    }




    fun bindToGame(g : GameManager){
        game = g
    }




    //TODO MAIN FUNCTION
    override fun draw(canvas: Canvas?) {
        super.draw(canvas)

        canvas!!.drawRect(0f, 0f, width.toFloat(),
                    height.toFloat(), backgroundPaint)
        Log.d("View", "GameView drawing")
        mapView.drawGrid(canvas, game.map)
        objectDrawer.drawObjects(canvas, game.gameObjectList)
        Log.d("checking", "$width and $height")




    }



    //Future
    fun changeMap(mapName : String){
        //TODO read map from gameManager
        mapLoader.loadMap("spring")
        map = mapLoader.returnMap()
    }


    override fun onSizeChanged(w:Int, h:Int, oldw:Int, oldh:Int) {

        super.onSizeChanged(w, h, oldw, oldh)

        screenWidth = w.toFloat()
        screenHeight = h.toFloat()
        mapView.setRects(game.map, screenWidth, screenHeight)
        objectDrawer.setRect(game.gameObjectList)
    }


    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
    }



    override fun surfaceCreated(holder: SurfaceHolder) {
        Log.d("GameView", "surface created")
        thread = GameThread(getHolder(), this, progressBar)
        thread.setRunning(true)
        thread.start()
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        thread.setRunning(false)
        thread.join()
    }


    fun pause() {
        thread.setRunning(false)
        thread.join()
    }

    fun resume() {
        Log.d("GameView", "Game resumed")
        thread = GameThread(getHolder(), this, progressBar)
        thread.setRunning(true)
        thread.start()
    }



    /*override fun run(){
    while(drawing){
       //draw()
    }
}*/

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




}



