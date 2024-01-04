package com.example.proyecto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
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
    var textview = findViewById<TextView>(R.id.textView3)
    val b1 = findViewById<TextView>(R.id.b1);val b2 = findViewById<TextView>(R.id.b2);val b3 = findViewById<TextView>(R.id.b3);val b4 = findViewById<TextView>(R.id.b4)
    val b5 = findViewById<TextView>(R.id.b5);val b6 = findViewById<TextView>(R.id.b6);val b7 = findViewById<TextView>(R.id.b7);val b8 = findViewById<TextView>(R.id.b8)
    val b9 = findViewById<TextView>(R.id.b9);val b10 = findViewById<TextView>(R.id.b10);val b11 = findViewById<TextView>(R.id.b11);val b12 = findViewById<TextView>(R.id.b12)
    val B1 = findViewById<TextView>(R.id.B1);val B2 = findViewById<TextView>(R.id.B2);val B3 = findViewById<TextView>(R.id.B3);val B4 = findViewById<TextView>(R.id.B4)
    val B5 = findViewById<TextView>(R.id.B5);val B6 = findViewById<TextView>(R.id.B6);val B7 = findViewById<TextView>(R.id.B7);val B8 = findViewById<TextView>(R.id.B8)
    val B9 = findViewById<TextView>(R.id.B9);val B10 = findViewById<TextView>(R.id.B10);val B11 = findViewById<TextView>(R.id.B11);val B12 = findViewById<TextView>(R.id.B12)
    val B13 = findViewById<TextView>(R.id.B13);val B14 = findViewById<TextView>(R.id.B14);val B15 = findViewById<TextView>(R.id.B15);val B16 = findViewById<TextView>(R.id.B16)
    val B17 = findViewById<TextView>(R.id.B17);val B18 = findViewById<TextView>(R.id.B18);val B19 = findViewById<TextView>(R.id.B19);val B20 = findViewById<TextView>(R.id.B20)
    val B21 = findViewById<TextView>(R.id.B21);val B22 = findViewById<TextView>(R.id.B22);val B23 = findViewById<TextView>(R.id.B23);val B24 = findViewById<TextView>(R.id.B24)

    private fun loseALine(){}

    private fun next_shape(){

        /* 1   2   3
        *  4   5   6
        *  7   8   9
        *  10  11  12*/
        b1.setBackgroundResource(R.drawable.block); b2.setBackgroundResource(R.drawable.block); b3.setBackgroundResource(R.drawable.block); b4.setBackgroundResource(R.drawable.block);
        b5.setBackgroundResource(R.drawable.block); b6.setBackgroundResource(R.drawable.block); b7.setBackgroundResource(R.drawable.block); b8.setBackgroundResource(R.drawable.block);
        b9.setBackgroundResource(R.drawable.block); b10.setBackgroundResource(R.drawable.block); b11.setBackgroundResource(R.drawable.block); b12.setBackgroundResource(R.drawable.block);

        val random = Random.nextInt(1..7)
        when (random){
            //L
            1 ->{b6.setBackgroundResource(R.drawable.orange); b7.setBackgroundResource(R.drawable.orange); b8.setBackgroundResource(R.drawable.orange);b9.setBackgroundResource(R.drawable.orange)}
            //Inverted S
            2 ->{b4.setBackgroundResource(R.drawable.yellow);b5.setBackgroundResource(R.drawable.yellow);b8.setBackgroundResource(R.drawable.yellow);b9.setBackgroundResource(R.drawable.yellow);}
            //square
            3 ->{b5.setBackgroundResource(R.drawable.green);b6.setBackgroundResource(R.drawable.green);b8.setBackgroundResource(R.drawable.green);b9.setBackgroundResource(R.drawable.green);}
            //Inverted L
            4 ->{b4.setBackgroundResource(R.drawable.purple);b7.setBackgroundResource(R.drawable.purple);b8.setBackgroundResource(R.drawable.purple);b9.setBackgroundResource(R.drawable.purple);}
            //S
            5 ->{b6.setBackgroundResource(R.drawable.pink); b5.setBackgroundResource(R.drawable.pink); b8.setBackgroundResource(R.drawable.pink);b7.setBackgroundResource(R.drawable.pink)}
            // _-_
            6 ->{b5.setBackgroundResource(R.drawable.blue); b7.setBackgroundResource(R.drawable.blue); b8.setBackgroundResource(R.drawable.blue);b9.setBackgroundResource(R.drawable.blue)}
            // |
            7 ->{b2.setBackgroundResource(R.drawable.red); b5.setBackgroundResource(R.drawable.red); b8.setBackgroundResource(R.drawable.red);b11.setBackgroundResource(R.drawable.red)}
        }
        nextUp  = random
        if (once == 0){
            b1.setBackgroundResource(R.drawable.block); b2.setBackgroundResource(R.drawable.block); b3.setBackgroundResource(R.drawable.block); b4.setBackgroundResource(R.drawable.block);
            b5.setBackgroundResource(R.drawable.block); b6.setBackgroundResource(R.drawable.block); b7.setBackgroundResource(R.drawable.block); b8.setBackgroundResource(R.drawable.block);
            b9.setBackgroundResource(R.drawable.block); b10.setBackgroundResource(R.drawable.block); b11.setBackgroundResource(R.drawable.block); b12.setBackgroundResource(R.drawable.block);
        }
        if (starting == 0){
            Handler().postDelayed({shapes()}, 1500)
            starting = 1
        }
    }
    private fun shapes(){}
    val arrayCollectPreviousOne = arrayListOf<TextView>()
    val array = arrayListOf<TextView>()
    val downArray: ArrayList<TextView> get() = arrayListOf<TextView>(textview, b1, b2)
    var a = 0
    var b = 0
    var c = 0

    private fun list(){}
    var x = 0
    private fun list2(){}
    private fun R_L(){}
    var points  = 0
    private fun landing(){}
    private fun colors(){}
}