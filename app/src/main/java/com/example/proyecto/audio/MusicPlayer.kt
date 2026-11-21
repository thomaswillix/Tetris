package com.example.proyecto.audio

import android.content.Context
import android.media.MediaPlayer
import androidx.annotation.RawRes

/**
 * Envuelve un MediaPlayer con el ciclo de vida correcto:
 *   onResume -> resume()   onPause -> pause()   onDestroy -> release()
 */
class MusicPlayer(
    context: Context,
    @RawRes private val resId: Int,
    private val looping: Boolean = true
) {
    private val appContext = context.applicationContext
    private var player: MediaPlayer? = null

    /** Arranca la música (la crea si hace falta) o la reanuda si estaba en pausa. */
    fun resume() {
        val p = player ?: MediaPlayer.create(appContext, resId)?.also {
            it.isLooping = looping
            player = it
        }
        if (p != null && !p.isPlaying) p.start()
    }

    fun pause() {
        player?.takeIf { it.isPlaying }?.pause()
    }

    fun release() {
        player?.release()
        player = null
    }

    companion object {
        /** Efecto de sonido de una sola vez; se libera solo al terminar. */
        fun playOnce(context: Context, @RawRes resId: Int) {
            MediaPlayer.create(context.applicationContext, resId)?.apply {
                setOnCompletionListener { it.release() }
                start()
            }
        }
    }
}
