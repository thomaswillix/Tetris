package com.example.proyecto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView

class Game : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game)

        val nom = intent.getStringExtra("userName");
        val user = findViewById<TextView>(R.id.username)
        user.text  = "$nom"
        val pause = findViewById<Button>(R.id.pause)

        pause.setOnClickListener {
            //Options for pausing and exiting the game to the main screen.
        }
    }
}