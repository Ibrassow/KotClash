package com.example.kotclash.views

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.ImageView
import com.example.kotclash.R



class CardView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : androidx.appcompat.widget.AppCompatImageView(context, attrs, defStyle) {

    lateinit var cardName: String
    //lateinit var imageCard : Bitmap

    fun setCard(cardname: String){
        cardName = cardname
        initView(cardName)
    }

    fun initView(cardName : String) {
        //imageCard = BitmapFactory.decodeResource(context.resources, R.drawable.imtest1)
        when (cardName) {
            "test1" -> {this.setImageResource(R.drawable.tankred)}
            "test2" -> this.setImageResource(R.drawable.tankblue)
            "test3" -> this.setImageResource(R.drawable.tankgreen)
            "test4" -> this.setImageResource(R.drawable.awax)
            "test5" -> this.setImageResource(R.drawable.soldier)
        }
    }

    fun grey(){
        when (cardName){
            "test1" -> {this.setImageResource(R.drawable.gtankblue);this.setBackgroundColor(Color.GRAY)}
            "test2" -> {this.setImageResource(R.drawable.gtankgreen);this.setBackgroundColor(Color.GRAY)}
            "test3" -> {this.setImageResource(R.drawable.gtankred);this.setBackgroundColor(Color.GRAY)}
            "test4" -> {this.setImageResource(R.drawable.awax);this.setBackgroundColor(Color.GRAY)}
            "test5" -> {this.setImageResource(R.drawable.soldier);this.setBackgroundColor(Color.GRAY)}
        }
    }

}