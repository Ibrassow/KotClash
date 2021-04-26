package com.example.kotclash.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import com.example.kotclash.controllers.GameManager
import com.example.kotclash.R
import com.example.kotclash.models.ResourceBar
import com.example.kotclash.views.CardView
import com.example.kotclash.views.GameView



abstract class GameActivity : AppCompatActivity() {

    var game = GameManager.gameInstance

    lateinit var gameView : GameView
    lateinit var progressBar : ProgressBar
    val cardList = mutableListOf<CardView>()
    abstract var resBar : ResourceBar



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

        for (i in 0..3 ){
            cardList[i].setOnClickListener {
                //TODO  MAY BE BETTER IN CARD MANAGER
            }
        }
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