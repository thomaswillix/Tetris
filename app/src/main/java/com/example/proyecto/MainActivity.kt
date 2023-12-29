package com.example.proyecto

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val git  = findViewById<Button>(R.id.github)
        val play = findViewById<Button>(R.id.btPlay)
        play.setOnClickListener{
            startActivity(Intent(this, Game::class.java))
        }
        git.setOnClickListener {
            goLink("https://github.com/thomaswillix/Tetris");
        }
    }

    private fun goLink(string: String) {
        val uri = Uri.parse(string)
        startActivity(Intent(Intent.ACTION_VIEW,uri))
    }
}