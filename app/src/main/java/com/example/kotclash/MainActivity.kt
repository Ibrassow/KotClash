package com.example.kotclash

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener{

    //TODO: Singleton? Temporary version here!
    val game = GameManager()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //card1.setOnClickListener(this)    TODO:def buttons in view
        //card2.setOnClickListener(this)
    }


    override fun onClick(v:View) {
        when (v.id) {
            R.id.card1 -> {
                game.saveCard(1)
            }

            R.id.card2 -> {
                game.saveCard(2)
            }
        }
    }


    //TODO : I put this here but is supposed to be in SurfaceView
    override fun onTouchEvent(e: MotionEvent): Boolean {
        when (e.action) {
            MotionEvent.ACTION_DOWN -> {
                val x = e.rawX - 100
                val y = e.rawY - 300
                game.playCard(Pair(x,y))
            }
        }
        return true
    }
}