package com.example.proyecto

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val git  = findViewById<Button>(R.id.github)
        val play = findViewById<Button>(R.id.btPlay)
        val score = findViewById<Button>(R.id.scores)
        val user = findViewById<EditText>(R.id.user)

        play.setOnClickListener{
            val intent = Intent(this, Game::class.java)
            intent.putExtra("userName", user.text.toString())
            startActivity(intent)
        }
        git.setOnClickListener {
            goLink("https://github.com/thomaswillix/Tetris");
        }
        score.setOnClickListener {
            //startActivity(Intent(this, something::class.java))
        }
    }

    private fun goLink(string: String) {
        val uri = Uri.parse(string)
        startActivity(Intent(Intent.ACTION_VIEW,uri))
    }
}