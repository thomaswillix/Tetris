package com.example.proyecto

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.core.view.size
import kotlin.system.exitProcess

class LeaderBoard : AppCompatActivity() {
    private var song: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leader_board)
        song = MediaPlayer.create(applicationContext,R.raw.leaderboard)
        song!!.start()
        highScores()
        val backButton = findViewById<Button>(R.id.back)
        backButton.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun highScores(){
        val lv = findViewById<ListView>(R.id.lv)

        val admin = AdminSQL(this, "Scores", null, 1)
        val bd = admin.readableDatabase
        val lista:MutableList<String> =ArrayList()
        val fila = bd.query("Scores", null,null,null,null,null,"points desc", "7")
        if (fila.moveToFirst()) {
            Toast.makeText(this, "Hay "+fila.count+" puntuaciones",  Toast.LENGTH_SHORT).show()
            do{

                lista.add(fila.getString(0) + "        "+fila.getInt(1) + "\n")
            }while(fila.moveToNext())
        } else{
            Toast.makeText(this, "No existen puntuaciones",  Toast.LENGTH_SHORT).show()
        }

        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista)

        lv.adapter = adapter
        fila.close()
        bd.close()
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
        stopPlayer()
        exitProcess(0)
    }
}
