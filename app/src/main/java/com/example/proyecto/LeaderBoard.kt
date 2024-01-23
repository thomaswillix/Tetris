package com.example.proyecto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.core.view.size
import kotlin.system.exitProcess

class LeaderBoard : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leader_board)
        highScores()
        val backButton = findViewById<Button>(R.id.back)
        backButton.setOnClickListener{

        }
    }

    private fun highScores(){
        val lv = findViewById<ListView>(R.id.lv)

        val admin = AdminSQL(this, "Scores", null, 1)
        val bd = admin.readableDatabase
        val lista:MutableList<String> =ArrayList()
        val fila = bd.query("Scores", null,null,null,null,null,"points desc", "5")
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
        lv.setAdapter(adapter)
        fila.close()
        bd.close()
    }
    override fun onPause() {
        super.onPause()
        exitProcess(0)
    }
}
