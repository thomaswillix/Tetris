package com.example.proyecto

import android.content.ContentValues
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {
    private var song: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        song = MediaPlayer.create(applicationContext,R.raw.main_menu)
        song!!.start()
        val git  = findViewById<Button>(R.id.github)
        val play = findViewById<Button>(R.id.btPlay)
        val score = findViewById<Button>(R.id.scores)
        val user = findViewById<EditText>(R.id.user)

        play.setOnClickListener{
            if(user.text.toString().isEmpty()){
                Toast.makeText(this, "EL USUARIO NO PUEDE ESTAR VACÍO", Toast.LENGTH_SHORT).show()
            }else if(validateUser(user.text.toString())){
                play()
            } else{
                Toast.makeText(this, "EL USUARIO TIENE QUE TENER 3 CARACTERES", Toast.LENGTH_SHORT).show()
            }
        }
        git.setOnClickListener {
            goLink("https://github.com/thomaswillix/Tetris");
        }
        score.setOnClickListener {
            stopPlayer()
            startActivity(Intent(this, LeaderBoard::class.java))
        }
    }
    private fun validateUser(user :String): Boolean{
        return user.length==3
    }
    private fun play(){
        val user = findViewById<EditText>(R.id.user)
        val intent = Intent(this, Game::class.java)
        intent.putExtra("userName", user.text.toString())
        stopPlayer()
        startActivity(intent)
    }

    private fun goLink(string: String) {
        val uri = Uri.parse(string)
        startActivity(Intent(Intent.ACTION_VIEW,uri))
    }
    private fun stopPlayer() {
        try {
            if (song != null) {
                // Log.e("Trying to Stop "," Player ");
                song!!.stop()
                song!!.release()
                song!!.reset() // causes IllegalstateException
                song = null
            }
        } catch (e: Exception) {
            song = null
            e.printStackTrace()
        }
    }

    override fun onPause() {
        super.onPause()
        song?.pause()
    }

    override fun onResume() {
        super.onResume()
        song?.start()
    }
}
