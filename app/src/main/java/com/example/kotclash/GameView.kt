package com.example.kotclash

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.core.content.ContextCompat


class GameView @JvmOverloads constructor (context: Context, attributes: AttributeSet? = null, defStyleAttr: Int = 0) : SurfaceView(context, attributes,defStyleAttr), SurfaceHolder.Callback, Runnable {

    lateinit var canvas: Canvas
    lateinit var thread: Thread

    lateinit var map : Map
    lateinit var mapLoader: MapLoader



    val backgroundPaint = Paint()

    //val bitmap: Bitmap





    //var grass = BitmapFactory.decodeResource(context.resources, com.example.kotclash.R.drawable.tile_drawable)

    var screenWidth = 0f
    var screenHeight = 0f



    var drawing : Boolean = true

    init{
        backgroundPaint.color = Color.WHITE
    }




    override fun run(){

        while(drawing){
            //tileView.draw()
           draw()
            //println("drawing")
        }

    }



    fun draw(){

        if (holder.surface.isValid) {
            canvas = holder.lockCanvas()
            canvas.drawRect(0f, 0f, canvas.width.toFloat(),
                    canvas.height.toFloat(), backgroundPaint)



            //map.drawGrid(canvas, context)
            mapLoader.drawGrid(canvas)


            //Ultra-important
            holder.unlockCanvasAndPost(canvas)
    }
}




    //val tt = ContextCompat.getDrawable(context, R.drawable.grass)



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

        //screenWidth = w.toFloat()
        //screenHeight = h.toFloat()


        map = Map(this.getWidth(), this.getHeight())
        mapLoader = MapLoader(map, context)





    }


}


/*    <com.example.kotclash.Tile
        android:id="@+id/tileView"
        android:layout_width="193dp"
        android:layout_height="173dp"
        app:layout_constraintBaseline_toBaselineOf="@+id/gameView"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent" />*/