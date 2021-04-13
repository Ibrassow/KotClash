package com.example.kotclash

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.kotclash.R

class StartActivity : AppCompatActivity() {
    lateinit var startButton : Button
    lateinit var quitButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        startButton = findViewById(R.id.startbutton)
        quitButton = findViewById(R.id.quitbutton)

        startButton.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent);
        }

        quitButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }




}