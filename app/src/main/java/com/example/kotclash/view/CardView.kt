package com.example.kotclash.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.ImageButton
import android.widget.ImageView
import com.example.kotclash.R


@SuppressLint("AppCompatCustomView")
class CardView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0, val cardName : String = "") : ImageView(context, attrs, defStyle) {




    val paint = Paint()
    lateinit var imageCard : Bitmap




    init{
        //initCard()
        this.setImageResource(R.drawable.imtest2)

    }


    fun initCard() {

        imageCard = BitmapFactory.decodeResource(context.resources, R.drawable.imtest1)

        when (cardName) {
            "test1" -> imageCard = BitmapFactory.decodeResource(context.resources, R.drawable.imtest1)
            "test2" -> imageCard = BitmapFactory.decodeResource(context.resources, R.drawable.imtest2)
            "test3" -> imageCard = BitmapFactory.decodeResource(context.resources, R.drawable.imtest3)
        }



    }


    //TODO
    fun setCard(cardName: String){

    }







}