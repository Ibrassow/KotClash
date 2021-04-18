package com.example.kotclash

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import java.util.*
import android.graphics.RectF
import android.util.Log

/*interface Renderable {
    fun draw(canvas: Canvas?)

    companion object {
        const val RENDERABLE_WIDTH = 50
        const val RENDERABLE_HEIGHT = 50
    }
}*/

class Tile(val xi : Float, val yi : Float, var tileElement : String) {

    var occupants : MutableList<GameObject> = mutableListOf()

    var x = xi
    var y = yi

    var position  = Pair(xi,yi)

    //TODO change this attribute when reading the map
    var walkable : Boolean = true

    var endx = x + Renderable.RENDERABLE_WIDTH
    var endy = y + Renderable.RENDERABLE_HEIGHT

    var cellRectangle: RectF = RectF(x, y, endx, endy)

    init{
        if (tileElement == "grass"){
            walkable = false //for testing purposes
        }
    }

    // TODO A* Search
    /*val parentCell: Cell? = null
    var gCost = 10
    var hCost = 0
    var fCost = 0*/


    fun isOccupied(): Boolean{
        return occupants.isNotEmpty()
    }

    fun setRect(renderable_Width : Float, renderable_Height : Float){
        x = (xi * renderable_Width).toFloat()
        y = (yi * renderable_Height).toFloat()
        position = Pair(x,y)
        endx = x + renderable_Width
        endy = y + renderable_Height
        cellRectangle.set(x, y, endx, endy)
        Log.d("InTile", "x : $x - y : $y")
    }











    /*var tileSize : Int
    lateinit var myBitmap: Bitmap
    init{
        //var a: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.TileView)
        //tileSize = a.getDimensionPixelSize(R.styleable.TileView_tileSize, 12)
        tileSize = 200
        loadTile(1, this.getContext().getResources().getDrawable(R.drawable.grass))
    }
    fun draw(){
        loadTile(1, this.getContext().getResources().getDrawable(R.drawable.grass))
    }
    fun loadTile(key: Int, tile: Drawable) {
        val bitmap = Bitmap.createBitmap(
            tileSize,
            tileSize,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        tile.setBounds(
            0,
            0,
            tileSize,
            tileSize
        )
        tile.draw(canvas)
        myBitmap = bitmap
        //mTileArray.get(key) = bitmap
    }
     override fun onDraw(canvas : Canvas)
    {
        super.onDraw(canvas)
        /*canvas.drawBitmap(
            mTileArray.get(mTileGrid.get(x).get(y)),
            XOffset + x * tileSize.toFloat(),
            YOffset + y * tileSize.toFloat(),
            paint
        )*/
        val rectangle = Rect(0, 0, 100, 100)
        canvas.drawBitmap(myBitmap, Rect(0, 0, 100, 100), rectangle, paint)
    }*/





    /*@JvmName("setOccupant1") //Overwriting to allow the change of position, want to keep it verbose
    fun setOccupant(entity : Entity){
        occupant = entity
        occupant!!.position = position
    }
    fun removeOccupant(){
        occupant = null
    }
    fun isOccupied(): Boolean{
        //print(occupant != null)
        return (occupant != null)
    }
    fun dist(posE: Tile): Double {
        return sqrt((posE.x - x).toDouble().pow(2) + (posE.y - y).toDouble().pow(2))
    }
    fun getEntity() : Entity?{
        return occupant
    }*/

    override fun toString(): String {
        return position.toString()
    }




}