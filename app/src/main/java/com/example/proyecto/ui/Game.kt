package com.example.proyecto.ui

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.proyecto.audio.MusicPlayer
import com.example.proyecto.data.ScoreRepository
import com.example.proyecto.game.Board
import com.example.proyecto.game.GameEngine
import com.example.proyecto.game.PieceType

/**
 * Solo UI: pinta el estado del GameEngine y le pasa los clicks.
 * No contiene reglas del juego.
 */
class Game : AppCompatActivity() {
    companion object {
        private const val TICK_MS = 800L
        private const val GAME_OVER_KEY = 8
        // Índice = id de PieceType (0 = casilla vacía), el último es el bloque de game over.
        private val BACKGROUNDS = intArrayOf(
            R.drawable.block, R.drawable.orange, R.drawable.yellow, R.drawable.green,
            R.drawable.purple, R.drawable.pink, R.drawable.blue, R.drawable.red,
            R.drawable.game_over_block
        )
    }

    private val engine = GameEngine()
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var music: MusicPlayer
    private lateinit var scores: ScoreRepository

    private val cells = ArrayList<TextView>(Board.ROWS * Board.COLS)
    private val preview = ArrayList<TextView>(12)
    private lateinit var pointsText: TextView
    private lateinit var userName: String

    // Caché de lo último pintado para no reasignar fondos que no han cambiado
    private val drawn = IntArray(Board.ROWS * Board.COLS) { -1 }
    private var drawnNext: PieceType? = null
    private var gameOverHandled = false

    private val tick = object : Runnable {
        override fun run() {
            engine.tick()
            render()
            if (engine.isGameOver) onGameOver() else handler.postDelayed(this, TICK_MS)
        }
    }

    // Notificación (sin cambios respecto a tu versión)
    private val channelID = "channelID"
    private val channelName = "channelName"
    private val notificationId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game)

        userName = intent.getStringExtra("userName") ?: "???"
        findViewById<TextView>(R.id.username).text = userName
        pointsText = findViewById(R.id.points)

        music = MusicPlayer(this, R.raw.boss_music)
        scores = ScoreRepository(this)

        bindGrid()
        bindControls()

        engine.start()
        render()
    }

    // --- Ciclo de vida: aquí se sustituye el exitProcess(0) ---

    override fun onResume() {
        super.onResume()
        if (!engine.isGameOver) {
            music.resume()
            handler.removeCallbacks(tick)
            handler.postDelayed(tick, TICK_MS)
        }
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(tick)
        music.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
        music.release()
    }

    // --- Vistas ---

    @SuppressLint("DiscouragedApi")
    private fun bindGrid() {
        for (i in 1..(Board.ROWS * Board.COLS)) {
            cells.add(findViewById(resources.getIdentifier("B$i", "id", packageName)))
        }
        for (i in 1..12) {
            preview.add(findViewById(resources.getIdentifier("bt$i", "id", packageName)))
        }
    }

    private fun bindControls() {
        findViewById<Button>(R.id.left).setOnClickListener { if (engine.moveLeft()) render() }
        findViewById<Button>(R.id.right).setOnClickListener { if (engine.moveRight()) render() }
        findViewById<Button>(R.id.spin).setOnClickListener { if (engine.rotate()) render() }
        findViewById<Button>(R.id.down).setOnClickListener {
            if (engine.hardDrop()) {
                render()
                if (engine.isGameOver) {
                    onGameOver()
                } else {
                    // Reinicia el temporizador para que la nueva pieza no baje de golpe
                    handler.removeCallbacks(tick)
                    handler.postDelayed(tick, TICK_MS)
                }
            }
        }
    }

    private fun render() {
        val total = Board.ROWS * Board.COLS
        val frame = IntArray(total) { engine.board[it / Board.COLS, it % Board.COLS] }

        engine.currentPiece?.let { piece ->
            piece.cells().forEach { frame[it.row * Board.COLS + it.col] = piece.type.id }
        }
        if (engine.isGameOver) {
            for (col in 3..6) frame[col] = GAME_OVER_KEY
        }
        for (i in 0 until total) {
            if (drawn[i] != frame[i]) {
                drawn[i] = frame[i]
                cells[i].setBackgroundResource(BACKGROUNDS[frame[i]])
            }
        }

        val next = engine.nextType
        if (drawnNext != next) {
            drawnNext = next
            for (i in preview.indices) {
                val key = if (i in next.previewCells) next.id else 0
                preview[i].setBackgroundResource(BACKGROUNDS[key])
            }
        }

        pointsText.text = "Points: ${engine.score}"
    }

    // --- Fin de partida ---

    private fun onGameOver() {
        if (gameOverHandled) return
        gameOverHandled = true
        handler.removeCallbacks(tick)
        music.release()
        MusicPlayer.playOnce(this, R.raw.game_over_ut)
        MusicPlayer.playOnce(this, R.raw.game_over_ut2)
        if (scores.saveIfBest(userName, engine.score)) notificationHS()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelID, channelName, NotificationManager.IMPORTANCE_HIGH
            ).apply {
                lightColor = Color.RED
                enableLights(true)
            }
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    @SuppressLint("MissingPermission")
    private fun notificationHS() {
        createNotificationChannel()
        val notification = NotificationCompat.Builder(this, channelID).also {
            it.setSmallIcon(R.drawable.notification)
            it.setContentTitle("NEW HIGH SCORE!")
            it.setContentText("Go check if you're on the LeaderBoard!!")
            it.priority = NotificationCompat.PRIORITY_HIGH
        }.build()
        NotificationManagerCompat.from(this).notify(notificationId, notification)
    }
}
