package com.example.kotclash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.kotclash.R
import com.example.kotclash.GameView

class GameActivity : AppCompatActivity() {

    lateinit var gameView : GameView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
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