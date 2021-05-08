package com.example.kotclash.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.example.kotclash.models.*
import kotlin.math.floor


class GameView @JvmOverloads constructor (context: Context, attributes: AttributeSet? = null, defStyleAttr: Int = 0) : SurfaceView(context, attributes,defStyleAttr), SurfaceHolder.Callback {


    var game : GameManager = GameManager.gameInstance

    var thread: GameThread

    //Specific views
    private val mapView = MapView()
    private val objectDrawer : GameObjectView = GameObjectView(this)

    //misc
    private val backgroundPaint = Paint()
    var screenWidth = 0f
    var screenHeight = 0f


    init{
        backgroundPaint.color = Color.WHITE
        holder.addCallback(this)
        this.isFocusable = true
        thread = GameThread(holder, this)
    }


    override fun onTouchEvent(e: MotionEvent): Boolean {
        when (e.action) {
            MotionEvent.ACTION_DOWN -> {
                Log.e("clickSucceed","Yes!")
                val x = e.rawX
                if (x <= screenWidth/2f){
                    game.playCard(0)
                }
                else{
                    game.playCard(1)
                }
            }
        }
        return true
    }


    //Temporary solution

    private var objListSize = game.gameObjectList.size
    private var minute : Double = 0.0
    private var second : Double = 0.0


    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        canvas!!.drawRect(0f, 0f, width.toFloat(), //Not necessary
                    height.toFloat(), backgroundPaint)

        mapView.drawGrid(canvas, game.map)

        if (objListSize != game.gameObjectList.size){
            objectDrawer.setRect()
            objListSize = game.gameObjectList.size
        }

        objectDrawer.drawObjects(canvas)


        minute = (floor(game.timeLeft/60.0))
        second = (game.timeLeft - minute*60.0)

        backgroundPaint.textSize = (screenWidth/20f)
        if (game.timeLeft <= 20.0 && (game.timeLeft%2.0).toInt()==0) {backgroundPaint.color = Color.RED}
        if (second < 10.0){
            canvas.drawText("0${minute.toInt()} : 0${second.toInt()} ",30f, 50f, backgroundPaint)
        }
        else{
            canvas.drawText("0${minute.toInt()} : ${second.toInt()} ",30f, 50f, backgroundPaint)
        }
        backgroundPaint.color = Color.WHITE
        }


    override fun onSizeChanged(w:Int, h:Int, oldw:Int, oldh:Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        screenWidth = w.toFloat()
        screenHeight = h.toFloat()
        mapView.setRects(game.map, screenWidth, screenHeight)
        objectDrawer.setRect()
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



