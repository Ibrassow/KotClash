package com.example.kotclash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import com.example.kotclash.view.CardView
import com.example.kotclash.view.GameView
import com.example.kotclash.view.ViewManager


class GameActivity : AppCompatActivity() {

    lateinit var viewManager : ViewManager

    lateinit var gameView : GameView
    lateinit var progressBar : ProgressBar
    val cardList = mutableListOf<CardView>() //Specify type after



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        gameView = findViewById(R.id.gameView)
        progressBar = findViewById(R.id.progressBar)

        cardList.add(findViewById(R.id.card1))
        cardList.add(findViewById(R.id.card2))
        cardList.add(findViewById(R.id.card3))

        viewManager = ViewManager(gameView, cardList, progressBar)

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