package com.example.proyecto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class Game : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game)

        val nom = intent.getStringExtra("userName");
        val user = findViewById<TextView>(R.id.username)
        user.text  = "$nom"

    }
}