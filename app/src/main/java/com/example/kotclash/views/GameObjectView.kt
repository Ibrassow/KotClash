package com.example.kotclash.views

import android.graphics.*
import android.util.Log
import com.example.kotclash.App
import com.example.kotclash.R
import com.example.kotclash.models.GameObject

class GameObjectView {

    lateinit var base: Bitmap

    //TODO Check P
    var paint = Paint()

    init{
        initImages()
    }




    fun initImages(){
        base = BitmapFactory.decodeResource(App.getContext().resources, R.drawable.base_palace)
    }

    // gameObject : GameObject
    fun drawObjects(canvas : Canvas){
        val rectF = RectF(500f, 100f, 800f, 300f)
        canvas.drawBitmap(base, null, rectF, paint)

    }


    /*TODO Set multi-tiles objects on map
    TODO Draw objects based on their positions
    TODO Set Rect!
    TODO





     */






}