package com.example.proyecto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast

class LeaderBoard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leader_board)
        highScores()
    }

    private fun highScores(){
        val lv = findViewById<ListView>(R.id.lv)

        val admin = AdminSQL(this, " Scores ", null, 1)
        val bd = admin.readableDatabase
        val lista:MutableList<String> =ArrayList()
        val fila = bd.query("Scores", null,null,null,null,null,"points", "10")
        Toast.makeText(this, "Hay "+fila.count+" Scores ",  Toast.LENGTH_SHORT).show()
        if (fila.moveToFirst()) {
            do{
                Toast.makeText(this, "entro al dowhile",  Toast.LENGTH_SHORT).show()
                lista.add(fila.getString(0) + "        "+fila.getInt(1) + "\n")
            }while(fila.moveToNext())
        } else{
            Toast.makeText(this, "NO existen puntuaciones",  Toast.LENGTH_SHORT).show()
        }

        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lista)
        lv.setAdapter(adapter)

        fila.close()
        bd.close()
    }
}
