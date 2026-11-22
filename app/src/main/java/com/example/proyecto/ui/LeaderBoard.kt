package com.example.proyecto.ui

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto.audio.MusicPlayer
import com.example.proyecto.data.ScoreRepository
import com.example.proyecto.R

class LeaderBoard : AppCompatActivity() {
    private lateinit var music: MusicPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leader_board)

        music = MusicPlayer(this, R.raw.leaderboard)
        showScores()

        // finish() vuelve a la MainActivity que ya existe en lugar de apilar otra
        findViewById<Button>(R.id.back).setOnClickListener { finish() }
    }

    private fun showScores() {
        val top = ScoreRepository(this).topScores(7)
        val message = if (top.isEmpty()) "No existen puntuaciones" else "Hay ${top.size} puntuaciones"
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

        val lines = top.map { "${it.user}        ${it.points}\n" }
        findViewById<ListView>(R.id.lv).adapter =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, lines)
    }

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
