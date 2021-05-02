package com.example.kotclash.activities

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

//import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity() {
    var startButton : Button? =null
    var quitButton : Button? =null
    val sampleMaps = intArrayOf(R.drawable.springfield, R.drawable.lavafield, R.drawable.winterfield)
    val sampleTroops = intArrayOf(R.drawable.tankred, R.drawable.tankblue, R.drawable.tankgreen,R.drawable.awax,R.drawable.soldier)
    var sampleChoosable = arrayOf<ImageView?>()
    var i=0;var j=0;var k=0;var l=0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.brand)

        Handler().postDelayed({
            setContentView(R.layout.activity_start)

            sampleChoosable = arrayOf(findViewById(R.id.mapChoose), findViewById(R.id.troop1Choose), troop2Choose, troop3Choose)
            for (choosable in sampleChoosable){
                choosable?.setOnClickListener{
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
                    else {
                        if (choosable == sampleChoosable[1] ){
                            if (j == sampleTroops.size) {
                            j = 0
                            choosable.setBackgroundResource(sampleTroops[j])
                            j++
                            }
                            else {
                            choosable.setBackgroundResource(sampleTroops[j])
                            j++
                        }

                        } else if (choosable == sampleChoosable[2] ){
                            if (k == sampleTroops.size) {
                                k = 0
                                choosable.setBackgroundResource(sampleTroops[j])
                                k++
                            }
                            else {
                                choosable.setBackgroundResource(sampleTroops[j])
                                k++
                            }
                        } else if (choosable == sampleChoosable[3]){
                            if (l == sampleTroops.size) {
                                l = 0
                                choosable.setBackgroundResource(sampleTroops[j])
                                l++
                            }
                            else {
                                choosable.setBackgroundResource(sampleTroops[j])
                                l++
                            }
            }

        startButton = findViewById(R.id.startbutton)
        quitButton = findViewById(R.id.quitbutton)

        startButton?.setOnClickListener {
            var params = listOf<Int>(i,j,k,l)
            val intent = Intent(this, GameActivity::class.java)
            Log.wtf("my wtf tag", "start dit "+ params.toString())
            when (i){
                1 -> intent.putExtra("mapChosen", "spring")
                2 -> intent.putExtra("mapChosen", "lava")
                3 -> intent.putExtra("mapChosen", "lava")
            }
            intent.putExtra("troop1Chosen", "test"+j.toString())
            intent.putExtra("troop2Chosen", "test"+k.toString())
            intent.putExtra("troop3Chosen", "test"+l.toString())
            startActivity(intent);
        }

        quitButton?.setOnClickListener {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }

        }}}}, 3000)
    }




}