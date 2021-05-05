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

        when (cardName) {
            "tankred" -> {this.setImageResource(R.drawable.tankred)}
            "tankblue" -> this.setImageResource(R.drawable.tankblue)
            "tankgreen" -> this.setImageResource(R.drawable.tankgreen)
            "bomber" -> this.setImageResource(R.drawable.bomber)
            "soldier" -> this.setImageResource(R.drawable.soldier)
        }
    }


    fun blurImg(){
        alpha = 0.3f
        isClickable = false
    }

    fun availableImg(){
        alpha = 1f
        isClickable = true
    }




    /*fun grey(){
        when (cardName){
            "test1" -> {this.setImageResource(R.drawable.gtankblue);this.setBackgroundColor(Color.GRAY)}
            "test2" -> {this.setImageResource(R.drawable.gtankgreen);this.setBackgroundColor(Color.GRAY)}
            "test3" -> {this.setImageResource(R.drawable.gtankred);this.setBackgroundColor(Color.GRAY)}
            "test4" -> {this.setImageResource(R.drawable.awax);this.setBackgroundColor(Color.GRAY)}
            "test5" -> {this.setImageResource(R.drawable.soldier);this.setBackgroundColor(Color.GRAY)}
        }
    }*/

}