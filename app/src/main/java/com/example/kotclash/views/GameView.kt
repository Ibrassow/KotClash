package com.example.kotclash.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.example.kotclash.models.*
import com.example.kotclash.models.Map
import kotlin.math.floor


class GameView @JvmOverloads constructor (context: Context, attributes: AttributeSet? = null, defStyleAttr: Int = 0) : SurfaceView(context, attributes,defStyleAttr), SurfaceHolder.Callback {


    var game : GameManager = GameManager.gameInstance

    //lateinit var canvas: Canvas
    var thread: GameThread

    //Map
    var map : Map = Map()
    val mapView = MapView()

    val objectDrawer : GameObjectView = GameObjectView(this)

    //misc
    val backgroundPaint = Paint()
    var screenWidth = 0f
    var screenHeight = 0f


    init{
        backgroundPaint.color = Color.WHITE


        holder.addCallback( this)
        this.isFocusable = true
        thread = GameThread(holder, this)
    }



    /*override fun onTouchEvent(e: MotionEvent): Boolean {
        when (e.action) {
            MotionEvent.ACTION_DOWN -> {
                val x = e.rawX - 100f
                val y = e.rawY - 300f
                cardManager.playCard(game.nbCardClicked, game.resources.toDouble(), Pair(x,y),map)
            }
        }
        return true
    }*/

    //Temporary solution

    var objListSize = game.gameObjectList.size

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        canvas!!.drawRect(0f, 0f, width.toFloat(), //Not necessary
                    height.toFloat(), backgroundPaint)
        var time = game.timeLeft
        var minute = ( floor(time/60))
        var second = (time - minute*60)
        mapView.drawGrid(canvas, game.map)

        if (objListSize != game.gameObjectList.size){
            objectDrawer.setRect(game.gameObjectList)
            objListSize = game.gameObjectList.size
        }

        objectDrawer.drawObjects(canvas, game.gameObjectList)
        Log.d("GameView", "Check Screen Size -- W : $width -- H : $height")

        backgroundPaint.textSize =(screenWidth/20f)
        if (time <= 20f && (time%2).toInt()==0){backgroundPaint.color = Color.RED}
        canvas.drawText("0${minute.toInt()} : ${second.toInt()} ",30f, 50f, backgroundPaint)
        backgroundPaint.color = Color.WHITE }



    override fun onSizeChanged(w:Int, h:Int, oldw:Int, oldh:Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        screenWidth = w.toFloat()
        screenHeight = h.toFloat()
        mapView.setRects(game.map, screenWidth, screenHeight)
        objectDrawer.setRect(game.gameObjectList)
    }


    override fun surfaceCreated(holder: SurfaceHolder) {

        while (!game.STARTED){
            Log.d("GameView", "Waiting game to start")
        }
        Log.d("GameView", "surface created")
        thread = GameThread(getHolder(), this)
        thread.setRunning(true)
        thread.start()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
    }


    fun resume() {
        Log.d("GameView", "resumed")
        thread = GameThread(getHolder(), this)
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


}



