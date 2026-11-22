package com.example.proyecto.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto.audio.MusicPlayer
import com.example.proyecto.R

class MainActivity : AppCompatActivity() {
    private lateinit var music: MusicPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        music = MusicPlayer(this, R.raw.main_menu)

        val git = findViewById<Button>(R.id.github)
        val play = findViewById<Button>(R.id.btPlay)
        val score = findViewById<Button>(R.id.scores)
        val user = findViewById<EditText>(R.id.user)

        play.setOnClickListener {
            val name = user.text.toString()
            when {
                name.isEmpty() ->
                    Toast.makeText(this, "EL USUARIO NO PUEDE ESTAR VACÍO", Toast.LENGTH_SHORT).show()
                name.length != 3 ->
                    Toast.makeText(this, "EL USUARIO TIENE QUE TENER 3 CARACTERES", Toast.LENGTH_SHORT).show()
                else -> startActivity(Intent(this, Game::class.java).putExtra("userName", name))
            }
        }
        git.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/thomaswillix/Tetris")))
        }
        score.setOnClickListener {
            startActivity(Intent(this, LeaderBoard::class.java))
        }
    }

    // La música se pausa al salir de la pantalla (Game, LeaderBoard, otra app...) y se reanuda al volver
    override fun onResume() {
        super.onResume()
        music.resume()
    }

    override fun onPause() {
        super.onPause()
        music.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        music.release()
    }
}
