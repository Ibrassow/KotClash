package com.example.kotclash

import android.graphics.Canvas
import android.util.Log
import android.view.SurfaceHolder
import com.example.kotclash.views.GameView


class GameThread(private val holder: SurfaceHolder, private val gameView: GameView) : Thread() {
    private var running: Boolean = false

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

        while (running) {

            startTime = System.nanoTime()
            timeElapsed = (startTime - lastTime) / 1000000

            if (timeElapsed >= targetTime){
                canvas = null

                try {
                    // locking the canvas allows us to draw on to it
                    canvas = this.holder.lockCanvas()
                    synchronized(holder) {

                        game.update(timeElapsed)
                        gameView.draw(canvas!!)
                        Log.d("thread", "calling draw and update from thread : $timeElapsed")
                        lastTime = System.nanoTime()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    if (canvas != null) {
                        try {
                            holder.unlockCanvasAndPost(canvas)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }

            }


        }
    }

    companion object {
        private var canvas: Canvas? = null
    }

}


