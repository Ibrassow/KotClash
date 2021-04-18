package com.example.kotclash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.kotclash.R
import com.example.kotclash.GameView
import kotlin.properties.Delegates

class GameActivity : AppCompatActivity() {

    lateinit var gameView : GameView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        val mapnb = intent.getIntExtra("mapChosen", 414)
        Log.wtf("my wtf tag", "         "+mapnb.toString())
        gameView = findViewById(R.id.gameView)


    }

    override fun onPause() {
        super.onPause()
        gameView.pause()

    }

    override fun onResume() {
        super.onResume()
        gameView.resume()

    }

}


