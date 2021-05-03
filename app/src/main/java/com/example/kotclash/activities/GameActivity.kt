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
    lateinit var mapSelected : String
    var troopSelected = mutableListOf<String>()

    var i = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        mapSelected = intent.getStringExtra("mapChosen").toString()
        troopSelected = mutableListOf<String>(intent.getStringExtra("troop1Chosen").toString(), intent.getStringExtra("troop2Chosen").toString(), intent.getStringExtra("troop3Chosen").toString())


        gameView = findViewById(R.id.gameView)
        progressBar = findViewById(R.id.progressBar)

        cardList.add(findViewById(R.id.card1))
        cardList.add(findViewById(R.id.card2))
        cardList.add(findViewById(R.id.card3))

        for (card in cardList) {
            card.setOnClickListener(this)
        }

        configureGame(mapSelected, troopSelected)
        game.start()
        val timer = Timer()

        mainHandler = Handler(Looper.getMainLooper())

    }

    private val updateBar = object : Runnable {
        override fun run() {
            progressBar.setProgress(game.resources.toInt())
            i++
            //progressBar.setProgress(i)
            mainHandler.postDelayed(this, 50)
        }
    }

    fun configureGame(mapSelected: String, cardSelected: MutableList<String>) {
        game.setMap(mapSelected)
        for (i in 0 until cardList.size) {
            cardList[i].setCard(cardSelected[i])
            /*cardList[i].setOnClickListener {
                game.playCard(i-1)
            }*/
        }
    }


    override fun onClick(v: View) {
        when (v.id) {
            R.id.card1 -> {
                game.playCard(troopSelected[0])
            }

            R.id.card2 -> {
                game.playCard(troopSelected[1])
            }

            R.id.card3 -> {
                game.playCard(troopSelected[2])
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


//TODO Send String instead with the correct name
//val mapSelected = intent.getIntExtra("mapSelected", 414) //Default value ?



//var gameState: String? = null

// recovering the instance state - in onCreate
//gameState = savedInstanceState?.getString(GAME_STATE_KEY) //Ideas for later


/*  override fun onSaveInstanceState(outState: Bundle?) {
        outState?.run {
            //Some actions
        }

        super.onSaveInstanceState(outState)
    }*/