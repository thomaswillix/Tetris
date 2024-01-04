package com.example.proyecto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import kotlin.random.Random
import kotlin.random.nextInt

class Game : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game)

        val nom = intent.getStringExtra("userName");
        val user = findViewById<TextView>(R.id.username)
        user.text = "$nom"
        val pause = findViewById<Button>(R.id.pause)

        next_shape()

        pause.setOnClickListener {
            //Options for pausing and exiting the game to the main screen.
        }
    }
    var nextUp:Int = 0
    var starting = 0
    var num1 = 0
    var num2 = 0
    var num3 = 0
    var num4 = 0
    var shape_is = 0
    var stop = 0
    var lines = 0
    var once = 0

    private fun next_shape(){

        val b1 = findViewById<TextView>(R.id.b1);val b2 = findViewById<TextView>(R.id.b2);val b3 = findViewById<TextView>(R.id.b3);val b4 = findViewById<TextView>(R.id.b4)
        val b5 = findViewById<TextView>(R.id.b1);val b6 = findViewById<TextView>(R.id.b2);val b7 = findViewById<TextView>(R.id.b3);val b8 = findViewById<TextView>(R.id.b4)
        val b9 = findViewById<TextView>(R.id.b1);val b10 = findViewById<TextView>(R.id.b2);val b11 = findViewById<TextView>(R.id.b3);val b12 = findViewById<TextView>(R.id.b4)
        /* 1   2   3
        *  4   5   6
        *  7   8   9
        *  10  11  12*/
        b1.setBackgroundResource(R.drawable.block); b2.setBackgroundResource(R.drawable.block); b3.setBackgroundResource(R.drawable.block); b4.setBackgroundResource(R.drawable.block);
        b5.setBackgroundResource(R.drawable.block); b6.setBackgroundResource(R.drawable.block); b7.setBackgroundResource(R.drawable.block); b8.setBackgroundResource(R.drawable.block);
        b9.setBackgroundResource(R.drawable.block); b10.setBackgroundResource(R.drawable.block); b11.setBackgroundResource(R.drawable.block); b12.setBackgroundResource(R.drawable.block);

        val random = Random.nextInt(1..8)
        when (random){
            1 ->{b6.setBackgroundResource(R.drawable.orange); b7.setBackgroundResource(R.drawable.orange); b8.setBackgroundResource(R.drawable.orange);
                b9.setBackgroundResource(R.drawable.orange)}
            2 ->{b4.setBackgroundResource(R.drawable.yellow);b5.setBackgroundResource(R.drawable.yellow);b8.setBackgroundResource(R.drawable.yellow);
                b9.setBackgroundResource(R.drawable.yellow);}
            3 ->{}
            4 ->{}
            5 ->{}
            6 ->{}
            7 ->{}
        }
    }
}