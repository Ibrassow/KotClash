package com.example.kotclash.views

import android.content.Context
import android.util.AttributeSet
import com.example.kotclash.R



class CardView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : androidx.appcompat.widget.AppCompatImageView(context, attrs, defStyle) {

    lateinit var cardName: String

    fun setCard(cardname: String){
        cardName = cardname
        initView(cardName)
    }

    private fun initView(cardName : String) {

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

    fun unblurImg(){
        alpha = 1f
        isClickable = true
    }

}