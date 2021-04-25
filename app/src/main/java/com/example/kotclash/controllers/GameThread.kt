package com.example.kotclash.controllers

import android.graphics.Canvas
import android.util.Log
import android.view.SurfaceHolder
import com.example.kotclash.views.GameView


class GameThread(private val holder: SurfaceHolder, private val gameView: GameView) : Thread() {
    private var running: Boolean = false
    private var locked = false

    private val MAX_FPS = 50
    private val game: GameManager = GameManager.gameInstance

    init {
        Log.d("thread", "created")

    }

    fun setRunning(isRunning: Boolean) {
        this.running = isRunning
    }


    override fun run() {
        var startTime: Long
        val targetTime = (1000 / MAX_FPS).toLong()
        var timeElapsed: Long
        var lastTime: Long = 0
        lateinit  var canvas : Canvas
        while (running) {

            startTime = System.nanoTime()
            timeElapsed = (startTime - lastTime) / 1000000

            if (timeElapsed >= targetTime){ //Null pointer exception is our friend

                try {
                    // locking the canvas allows us to draw on to it

                    synchronized(holder) {
                        canvas = this.holder.lockCanvas()
                        game.update(timeElapsed)
                        gameView.draw(canvas!!)
                        Log.d("thread", "calling draw and update from thread : $timeElapsed")
                        lastTime = System.nanoTime()
                        locked = true
                    }
                    holder.unlockCanvasAndPost(canvas)
                    Log.wtf("essaie", "il a unlock")
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                }




        }
    }

    /*companion object {
        private var canvas: Canvas? = null
    }*/

}
/*private boolean cLocked = false;


//Locking
 if (!cLocked){
    canvas = this.surfaceHolder.lockCanvas();
    cLocked = true;
}

//Unlocking
if (cLocked) {
    surfaceHolder.unlockCanvasAndPost(canvas);
    cLocked = false;
}*/