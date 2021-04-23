package com.example.kotclash.views

import android.graphics.*
import android.util.Log
import com.example.kotclash.App
import com.example.kotclash.R
import com.example.kotclash.models.Entity
import com.example.kotclash.models.GameObject
import com.example.kotclash.models.Tower
import java.lang.IndexOutOfBoundsException

class GameObjectView(val view : GameView) {

    lateinit var base: Bitmap

    //TODO Check P
    var paint = Paint()
    //val rectF = RectF(0f,0f, 0f, 0f)

    init{
        initImages()
    }


    fun initImages(){
        base = BitmapFactory.decodeResource(App.getContext().resources, R.drawable.base_palace)
    }

    // gameObject : GameObject
    fun drawObjects(canvas : Canvas, objectList : MutableList<GameObject>){

        for (obj in objectList){

            when(obj.type){
                "base" -> canvas.drawBitmap(base, null, obj.rectF, paint)

            }

            Log.d("GameObjectView", "drawing object")

        }



    }

    fun setRect(objectList : MutableList<GameObject>){

        val rendW = (view.screenWidth / view.game.map.getRowSize())
        val rendH = (view.screenHeight / view.game.map.getColSize())


        for (obj in objectList){

            if (obj is Tower){
                obj.coordinates = Pair(obj.coordinates.first * rendW + rendW/2, obj.coordinates.second * rendH + rendH/2)
            }

            val xx = obj.coordinates.first
            val yy = obj.coordinates.second
            val szx = obj.size.first
            val szy = obj.size.second
            //RESIZING
            obj.rectF.set(xx-(szx/2f)*rendW, yy-(szy/2f)*rendH, xx+(szx/2f)*rendW, yy+(szy/2f)*rendH)

        }

    }



}