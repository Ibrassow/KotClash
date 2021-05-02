package com.example.kotclash.activities

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.example.kotclash.R
import com.example.kotclash.models.GameManager
import com.example.kotclash.views.CardView
import com.example.kotclash.views.GameView
import java.util.*
import kotlin.concurrent.timerTask


class GameActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var mainHandler: Handler

    val game = GameManager.gameInstance
    lateinit var gameView: GameView

    lateinit var progressBar : ProgressBar

    val cardList = mutableListOf<CardView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        /*TODO INTENT should give the following:
        TODO - mapSelected : String
        TODO - cardList : List of 3 strings with the troop choice
        TODO - Initial set of towers!
        TODO - Difficulty level
         */

        val mapSelected = intent.getStringExtra("mapChosen").toString()
        val troopSelected = mutableListOf(intent.getStringExtra("troop1Chosen").toString(), intent.getStringExtra("troop2Chosen").toString(), intent.getStringExtra("troop3Chosen").toString())


        gameView = findViewById(R.id.gameView)
        progressBar = findViewById(R.id.progressBar)

        cardList.add(findViewById(R.id.card1))
        cardList.add(findViewById(R.id.card2))
        cardList.add(findViewById(R.id.card3))


        configureGame(mapSelected, troopSelected)
        game.start()


        mainHandler = Handler(Looper.getMainLooper())

    }



    private val updateBar = object : Runnable {
        override fun run() {
            progressBar.progress = game.resources.toInt()
            mainHandler.postDelayed(this, 10)
        }
    }

    fun configureGame(mapSelected: String, cardSelected: MutableList<String>) {
        game.setMap(mapSelected)
        for (i in 0 until cardList.size) {
            cardList[i].setCard(cardSelected[i])
            cardList[i].setOnClickListener(this)
        }
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.card1 -> {
                game.playCard(1)
            }

            R.id.card2 -> {
                game.playCard(2)
            }

            R.id.card3 -> {
                game.playCard(3)
            }
        }
    }


    override fun onPause() {
        super.onPause()
        gameView.pause()
        mainHandler.removeCallbacks(updateBar)

    }

    override fun onResume() {
        super.onResume()
        gameView.resume()
        mainHandler.post(updateBar)
    }

    override fun onDestroy() {
        super.onDestroy()
        GameManager.destroy()
    }








}





//var gameState: String? = null

// recovering the instance state - in onCreate
//gameState = savedInstanceState?.getString(GAME_STATE_KEY) //Ideas for later


/*  override fun onSaveInstanceState(outState: Bundle?) {
        outState?.run {
            //Some actions
        }

        super.onSaveInstanceState(outState)
    }*/