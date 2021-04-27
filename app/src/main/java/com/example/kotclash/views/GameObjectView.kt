package com.example.kotclash.views

import android.graphics.*
import android.util.Log
import com.example.kotclash.App
import com.example.kotclash.R
import com.example.kotclash.models.GameObject

class GameObjectView(val view : GameView) {

    lateinit var base: Bitmap
    lateinit var simpleTower: Bitmap
    lateinit var submarine: Bitmap
    lateinit var troop2: Bitmap
    lateinit var troop3: Bitmap
    lateinit var projectile: Bitmap

    //TODO Check P
    var paint = Paint()

    init{
        initImages()
    }

    fun initImages(){
        base = BitmapFactory.decodeResource(App.getContext().resources, R.drawable.base_palace)
        simpleTower = BitmapFactory.decodeResource(App.getContext().resources, R.drawable.tower1)
        projectile = BitmapFactory.decodeResource(App.getContext().resources, R.drawable.radiobutton_off_background)
    }

    // gameObject : GameObject
    fun drawObjects(canvas : Canvas, objectList : MutableList<GameObject>) {

        Log.wtf("draw", "ca dessine")
        for (obj in objectList) {
            when (obj.type) {
                "base" -> canvas.drawBitmap(base, null, obj.rectF, paint)
                "simpleTower" -> canvas.drawBitmap(simpleTower, null, obj.rectF, paint)
                /*"submarine" -> {
                    var  cc= obj.currentOrientation.toInt()
                    submarine = createSubImageAt(BitmapFactory.decodeResource(App.getContext().resources, R.drawable.redtank),(cc+5).toInt(),1,8,6)
                    canvas.drawBitmap(submarine, null, obj.rectF, paint)
                    Log.d("GOV", "dessin effectué pour " + obj.toString())
                }
                "projectile" -> canvas.drawBitmap(projectile, null, obj.rectF, paint)*/

            }

            Log.d("GameObjectView", "drawing object")

        }
    }
    fun createSubImageAt(image: Bitmap, row:Int, col:Int, rowCount: Int,colCount:Int): Bitmap  {
          var width = image.getWidth()/ colCount;
          var height= image.getHeight()/ rowCount;
          var subImage :Bitmap  = Bitmap.createBitmap(image, col* width, row* height ,width,height);
          return subImage;

    }

    fun setRect(objectList : MutableList<GameObject> ){

        val rendW = (view.screenWidth / view.game.map.getRowSize())
        val rendH = (view.screenHeight / view.game.map.getColSize())

        for (obj in objectList){
            val c = 3f
            val xx = obj.coordinates.first
            val yy = obj.coordinates.second
            val a =(yy+50)/(14+50)
            //Log.d("print", a.toString())
            //RESIZING
            //val a= 3f
            obj.rectF.set((xx-c/2)*rendW, (yy-c/2)* rendH*a, (xx+c/2)*rendW, (yy+c/2)* rendH*a)
            //Log.d("govi", obj.rectF.toString())

        }

    }
    /*fun setRect(objectList : MutableList<GameObject> ){

        val rendW = (view.screenWidth / view.game.map.getRowSize())
        val rendH = (view.screenHeight / view.game.map.getColSize())
        Log.d("GOV", "okay on a" + view.toString()+"-"+view.game.map.getRowSize().toString())

        for (obj in objectList){

            if (obj is GameObject){
                obj.coordinates = Pair(obj.coordinates.first * rendW + rendW/2, obj.coordinates.second * rendH + rendH/2)
            }

            val xx = obj.coordinates.first
            val yy = obj.coordinates.second
            val szx = obj.size.first
            val szy = obj.size.second
            //RESIZING
            val a= 3f
            obj.rectF.set(xx-(szx/a)*rendW, yy-(szy/a)*rendH, xx+(szx/a)*rendW, yy+(szy/a)*rendH)
            Log.d("govi", obj.rectF.toString())

        }

    }*/
}