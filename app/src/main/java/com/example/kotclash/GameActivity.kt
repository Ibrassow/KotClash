package com.example.kotclash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import android.widget.Toast
import com.example.kotclash.view.CardView
import com.example.kotclash.view.GameView



class GameActivity : AppCompatActivity() {

    var game = GameManager()


    lateinit var gameView : GameView
    lateinit var progressBar : ProgressBar
    val cardList = mutableListOf<CardView>() //Specify type after



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        //TODO Send String instead with the correct name
        //val mapSelected = intent.getIntExtra("mapSelected", 414) //Default value ?
        //Log.d("Map received", "         "+mapnb.toString())
        val mapSelected = "spring"

        game.setMap(mapSelected)


        gameView = findViewById(R.id.gameView)
        progressBar = findViewById(R.id.progressBar)

        cardList.add(findViewById(R.id.card1))
        cardList.add(findViewById(R.id.card2))
        cardList.add(findViewById(R.id.card3))


        //Success
        cardList[0].setOnClickListener{
                Toast.makeText(this, "test", Toast.LENGTH_SHORT).show()
                notifyViews()
        }


    }

    override fun onPause() {
        super.onPause()
        //gameView.pause()

    }

    override fun onResume() {
        super.onResume()
        //gameView.resume()
    }


    //This will be called to update everything
    fun notifyViews(){
        //gameView.invalidate()
        gameView.draw() //Temp
        progressBar.invalidate()
        for (card in cardList){
            card.invalidate()
        }
        Log.d("Push", "View notified")
    }









}