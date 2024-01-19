package com.example.proyecto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlin.system.exitProcess

class LeaderBoard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leader_board)
    }
    override fun onPause() {
        super.onPause()
        exitProcess(0)
    }
}