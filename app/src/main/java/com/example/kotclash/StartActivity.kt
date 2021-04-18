package com.example.kotclash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.kotclash.R
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity() {
    lateinit var startButton : Button
    lateinit var quitButton : Button
    val sampleMaps = intArrayOf(R.drawable.alabasta, R.drawable.allcake, R.drawable.skypiea)
    val sampleTroops = intArrayOf(R.drawable.luffy, R.drawable.zoro, R.drawable.sanji)
    val sampleBases = intArrayOf(R.drawable.tower1, R.drawable.tower2, R.drawable.tower3)
    var sampleChoosable = arrayOf<ImageView>()
    var i=0;var j=0;var k=0;var l=0;var m=0;var n=0;var o=0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
        setContentView(R.layout.brand)

        Handler().postDelayed({
            setContentView(R.layout.activity_start)
            sampleChoosable = arrayOf<ImageView>(mapChoose, troop1Choose, troop2Choose, troop3Choose, tower1Choose, tower2Choose, tower3Choose)
            for (choosable in sampleChoosable){
                choosable.setOnClickListener{
                    if (choosable == sampleChoosable[0]) {
                        if (i == sampleMaps.size) {
                            i = 0
                            choosable.setBackgroundResource(sampleMaps[i])
                            i++
                        } else {
                            choosable.setBackgroundResource(sampleMaps[i])
                            i++
                        }
                    }
                    else if (choosable == sampleChoosable[1] || choosable == sampleChoosable[2] || choosable == sampleChoosable[3]){
                        if (j == sampleTroops.size) {
                            j = 0
                            choosable.setBackgroundResource(sampleTroops[j])
                            j++
                        } else {
                            choosable.setBackgroundResource(sampleTroops[j])
                            j++
                        }
                    }
                    else if (choosable == sampleChoosable[4] || choosable == sampleChoosable[5] || choosable == sampleChoosable[6] ){
                        if (k == sampleBases.size) {
                            k = 0
                            choosable.setBackgroundResource(sampleBases[k])
                            k++
                        } else {
                            choosable.setBackgroundResource(sampleBases[k])
                            k++
                        }
                    }
                }
            }

        startButton = findViewById(R.id.startbutton)
        quitButton = findViewById(R.id.quitbutton)

        startButton.setOnClickListener {
            var params = listOf<Int>(i,j,k,l,m,n,o)
            val intent = Intent(this, GameActivity::class.java)
            Log.wtf("my wtf tag", "start dit "+ params.toString())
            intent.putExtra("mapChosen", i)
            intent.putExtra("troopChosen", j)
            intent.putExtra("towerChosen", k)
            startActivity(intent);
        }

        quitButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }

        }, 3000)
    }




}