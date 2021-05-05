package com.example.kotclash.activities

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
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
        troopSelected = mutableListOf(intent.getStringExtra("troop1Chosen").toString(), intent.getStringExtra("troop2Chosen").toString(), intent.getStringExtra("troop3Chosen").toString())
        Log.e("GYH", "$troopSelected")


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
            mainHandler.postDelayed(this, 50)
            if (game.GAMEOVER){
                showGameOverDialog(R.string.GameOverM)
            }
        }
    }

    fun configureGame(mapSelected: String, cardSelected: MutableList<String>) {
        game.setMap(mapSelected)
        for (i in 0 until cardList.size) {
            cardList[i].setCard(cardSelected[i])
            cardList[i].setOnClickListener(this)
        }
    }


    /*override fun onClick(v: View) {
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
    }*/


    override fun onClick(v: View) {
        when (v.id) {
            R.id.card1 -> {
                game.saveCard(troopSelected[0])
            }

            R.id.card2 -> {
                game.saveCard(troopSelected[1])
            }

            R.id.card3 -> {
                game.saveCard(troopSelected[2])
            }
        }
    }



    fun showGameOverDialog(messageId: Int) {

        class GameResult: DialogFragment() {

            override fun onCreateDialog(bundle: Bundle?): Dialog {
                val builder = AlertDialog.Builder(activity)
                builder.setTitle(resources.getString(messageId))
                builder.setMessage(
                        resources.getString(
                                R.string.GameOverM
                        )
                )

                builder.setPositiveButton(R.string.reset_game,
                        DialogInterface.OnClickListener { _, _-> newGame()}
                )
                return builder.create()
            }
        }

        this.runOnUiThread {
            val ft = this.supportFragmentManager.beginTransaction()
            val prev =
                    this.supportFragmentManager.findFragmentByTag("dialog")
            if (prev != null) {
                ft.remove(prev)
            }
            ft.addToBackStack(null)
            val gameResult = GameResult()
            gameResult.isCancelable = false
            gameResult.show(ft,"dialog")
        }

    }


    fun newGame(){

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