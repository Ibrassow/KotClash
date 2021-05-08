package com.example.kotclash.views

import android.graphics.*
import android.util.Log
import com.example.kotclash.App
import com.example.kotclash.R
import com.example.kotclash.models.GameManager
import com.example.kotclash.models.GameObject

class GameObjectView(private val view : GameView) {

    private lateinit var base: Bitmap
    private lateinit var simpleTower: Bitmap
    private lateinit var tankRed: Bitmap
    private lateinit var tankBlue: Bitmap
    private lateinit var tankGreen: Bitmap
    private lateinit var bomber: Bitmap
    private lateinit var soldier: Bitmap
    private lateinit var projectile: Bitmap

    var paint = Paint()

    val game = GameManager.gameInstance

    init{
        initImages()
    }

    private fun initImages(){
        base = BitmapFactory.decodeResource(App.getContext().resources, R.drawable.base_palace)
        simpleTower = BitmapFactory.decodeResource(App.getContext().resources, R.drawable.tower1)
        tankRed = BitmapFactory.decodeResource(App.getContext().resources, R.drawable.redtank)
        tankBlue= BitmapFactory.decodeResource(App.getContext().resources, R.drawable.bluetank)
        tankGreen = BitmapFactory.decodeResource(App.getContext().resources, R.drawable.greentank)
        bomber = BitmapFactory.decodeResource(App.getContext().resources, R.drawable.bomber)
        soldier = BitmapFactory.decodeResource(App.getContext().resources, R.drawable.soldiers)
        projectile = BitmapFactory.decodeResource(App.getContext().resources, R.drawable.bullets)
    }


    fun drawObjects(canvas : Canvas) {

        val objectList = game.gameObjectList.toMutableList()


        for (obj in objectList) {
            if(!obj.takingAction){obj.startOperation()}
            if (obj.isAlive()){
                when (obj.type) {
                    "base" -> canvas.drawBitmap(base, null, obj.rectF, paint)
                    "simpleTower" -> canvas.drawBitmap(simpleTower, null, obj.rectF, paint)
                    "tankred" -> {
                        val subImg = createSubImageAt(tankRed,obj.currentOrientation)
                        canvas.drawBitmap(subImg, null, obj.rectF, paint)
                    }
                    "tankblue" -> {
                        val subImg = createSubImageAt(tankBlue,obj.currentOrientation)
                        canvas.drawBitmap(subImg, null, obj.rectF, paint)
                    }
                    "tankgreen" -> {
                        val subImg = createSubImageAt(tankGreen,obj.currentOrientation)
                        canvas.drawBitmap(subImg, null, obj.rectF, paint)
                    }
                    "bomber" -> {
                        canvas.drawBitmap(bomber, null, obj.rectF, paint)
                    }
                    "soldier" -> {
                        val subImg = createSubImageAt(soldier,obj.currentOrientation)
                        canvas.drawBitmap(subImg, null, obj.rectF, paint)
                    }
                    "projectile" -> {
                        canvas.drawBitmap(projectile, null,obj.rectF, paint)
                    }
                }
            }
            Log.d("GameObjectView", "drawing object")
        }
    }


    fun setRect(){
        val objectList = game.gameObjectList
        val rendW = (view.screenWidth / game.map.getColSize())
        val rendH = (view.screenHeight / game.map.getRowSize())

        for (obj in objectList){
            obj.setRect(rendW, rendH)
        }
    }



    //TODO Do all of these cut (only the card) at the init and store them to access them during runtime!
    private fun createSubImageAt(image: Bitmap,  orientation:Float): Bitmap  {
        var row:Int=0; val col:Int=0; val rowCount: Int=8; val colCount:Int=6
        when (orientation){
            in -0.1745..0.1745 -> row = 2
            in 0.1745..1.3962 -> row = 7
            in 1.3962..1.74533 -> row = 3
            in 1.74533..2.96706 -> row = 6
            in 2.96706..3.1415 -> row = 1
            in -3.1415..-2.9670 -> row = 1
            in -2.9670..-1.7453 -> row = 4
            in -1.7453..-1.3962 -> row = 0
            in -1.3962..-0.17 -> row = 5
        }

        var width = image.getWidth()/ colCount;
        var height = image.getHeight()/ rowCount;

        var subImage :Bitmap  = Bitmap.createBitmap(image, col*width, row* height ,width,height);
        return subImage;
    }


}

