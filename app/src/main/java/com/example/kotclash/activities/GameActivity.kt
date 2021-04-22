package com.example.kotclash.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.ProgressBar
import android.widget.Toast
import com.example.kotclash.GameManager
import com.example.kotclash.R
import com.example.kotclash.views.CardView
import com.example.kotclash.views.GameView



class GameActivity : AppCompatActivity() {

    var game = GameManager.gameInstance

    lateinit var gameView : GameView
    lateinit var progressBar : ProgressBar
    val cardList = mutableListOf<CardView>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_game)

        //TODO Send String instead with the correct name
        //val mapSelected = intent.getIntExtra("mapSelected", 414) //Default value ?
        //Log.d("Map received", "         "+mapnb.toString())
        val mapSelected = "spring"
        game.setMap(mapSelected)

        gameView = findViewById(R.id.gameView)
        gameView.bindToGame(game)

        progressBar = findViewById(R.id.progressBar)
        cardList.add(findViewById(R.id.card1))
        cardList[0].setCard("test1")
        cardList.add(findViewById(R.id.card2))
        cardList[1].setCard("test2")
        cardList.add(findViewById(R.id.card3))
        cardList[2].setCard("test3")

        //Success
        /*cardList[0].setOnClickListener{
                Toast.makeText(this, "test", Toast.LENGTH_SHORT).show()
                notifyViews()
        }*/


    }

    override fun onPause() {
        super.onPause()
        gameView.pause()

    }

    override fun onResume() {
        super.onResume()
        gameView.resume()
    }

    override fun onDestroy(){
        super.onDestroy()
    }




    //This will be called to update everything
    /*fun notifyViews(){
        //gameView.invalidate()
        //gameView.draw() //Temp
        progressBar.invalidate()
        for (card in cardList){
            card.invalidate()
        }
        Log.d("Push", "Views notified")
    }*/









}