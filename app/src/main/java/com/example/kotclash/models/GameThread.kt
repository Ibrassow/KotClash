package com.example.kotclash.models

import android.graphics.Canvas
import android.util.Log
import android.view.SurfaceHolder
import com.example.kotclash.models.GameManager
import com.example.kotclash.views.GameView


class GameThread(private val holder: SurfaceHolder, private val gameView: GameView) : Thread() {
    private var running: Boolean = false
    private var locked = false
    private val MAX_FPS : Long = 50
    private val game: GameManager = GameManager.gameInstance


    fun setRunning(isRunning: Boolean) {
        this.running = isRunning
    }



    override fun run() {
        var startTime: Long
        val targetTime : Long = 1000/MAX_FPS
        var timeElapsed: Long

        var lastTime: Long = System.nanoTime() / 1000000

        while (running && !game.GAMEOVER) {

            startTime = System.nanoTime() / 1000000
            timeElapsed = (startTime - lastTime)

            if (timeElapsed >= targetTime){ // target time ??
                if (holder.surface.isValid) {
                try {
                    // locking the canvas allows us to draw on to it
                    if (!locked){
                    canvas = holder.lockCanvas()
                    locked = true}

                    synchronized(holder) {
                        game.update(timeElapsed)
                        gameView.draw(canvas!!)
                        Log.d("thread", "calling draw and update from thread : $timeElapsed")
                        lastTime = System.nanoTime() / 1000000

                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    if (canvas != null) {
                        try {
                            //Unlocking
                            if (locked){
                                holder.unlockCanvasAndPost(canvas)
                                locked = false
                            }

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
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



