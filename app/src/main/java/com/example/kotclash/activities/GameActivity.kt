package com.example.kotclash.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import com.example.kotclash.models.GameManager
import com.example.kotclash.R
import com.example.kotclash.models.ResourceBar
import com.example.kotclash.views.CardView
import com.example.kotclash.views.GameView



class GameActivity : AppCompatActivity(), View.OnClickListener {

    var game = GameManager.gameInstance

    lateinit var gameView : GameView
    lateinit var progressBar : ProgressBar
    val cardList = mutableListOf<CardView>()
    //var resBar : ResourceBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        /*TODO INTENT should give the following:
        TODO - mapSelected : String
        TODO - cardList : List of 3 strings with the troop choice
        TODO - Difficulty level
         */

        val mapSelected = "spring"
        val troopSelected = mutableListOf<String>("test1", "test2", "test3")

        gameView = findViewById(R.id.gameView)
        progressBar = findViewById(R.id.progressBar)

        cardList.add(findViewById(R.id.card1))
        cardList.add(findViewById(R.id.card2))
        cardList.add(findViewById(R.id.card3))

        configureGame(mapSelected, troopSelected)

    }

    fun configureGame(mapSelected : String, cardSelected : MutableList<String>){
        game.setMap(mapSelected)
        gameView.bindToGame(game)

        for (i in 0 until cardList.size){
            cardList[i].setCard(cardSelected[i])
        }

    }

    override fun onClick(v: View) {
        /*when (v.id) {
            R.id.card1 -> {
                game.saveCard(1)
            }

            R.id.card2 -> {
                game.saveCard(2)
            }

            R.id.card3 -> {
                game.saveCard(3)
            }
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