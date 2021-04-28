package com.example.kotclash.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ProgressBar
import android.widget.Toast
import com.example.kotclash.GameManager
import com.example.kotclash.R
import com.example.kotclash.models.CardManager
import com.example.kotclash.views.CardView
import com.example.kotclash.views.GameView


//abstract so that it can implement View.OnClickListener
abstract class GameActivity : AppCompatActivity(), View.OnClickListener {

    var game = GameManager.gameInstance

    lateinit var gameView : GameView
    lateinit var progressBar : ProgressBar
    private val cardList = mutableListOf<CardView>()


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
        cardList[0].setOnClickListener(this)
        cardList.add(findViewById(R.id.card2))
        cardList[1].setCard("test2")
        cardList[1].setOnClickListener(this)
        cardList.add(findViewById(R.id.card3))
        cardList[2].setCard("test3")
        cardList[2].setOnClickListener(this)

        //Success

        //TODO :
        /*cardList[0].setOnClickListener {
            game.saveCard(1)

            Toast.makeText(this, "test", Toast.LENGTH_SHORT).show()
            //notifyViews()
        }*/

    }


    override fun onClick(v: View) {
        var size: Float

        when (v.id) {
            R.id.card1 -> {
                game.saveCard(1)
            }

            R.id.card2 -> {
                game.saveCard(2)
            }

            R.id.card3 -> {
                game.saveCard(3)
            }
        }
    }


    override fun onPause() {
        super.onPause()
        gameView.pause()

    }

    override fun onResume() {
        super.onResume()
        gameView.resume()
    }

    //This one is redundant
    /*override fun onDestroy(){
        super.onDestroy()
    }*/


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