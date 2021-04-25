package com.example.kotclash.views

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.ImageView
import com.example.kotclash.R


@SuppressLint("AppCompatCustomView")
class CardView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : ImageView(context, attrs, defStyle) {

    val paint = Paint()
    //lateinit var imageCard : Bitmap


    fun setCard(cardName: String){
        initView(cardName)
    }


    fun initView(cardName : String) {

        //imageCard = BitmapFactory.decodeResource(context.resources, R.drawable.imtest1)

        when (cardName) {
            "test1" -> this.setImageResource(R.drawable.tankblue)
            "test2" -> this.setImageResource(R.drawable.tankgreen)
            "test3" -> this.setImageResource(R.drawable.tankred)
        }

    }

}