package com.example.proyecto.data

import android.content.ContentValues
import android.content.Context

data class Score(val user: String, val points: Int)

class ScoreRepository(context: Context) {
    private val helper = AdminSQL(context.applicationContext, "Scores", null, 1)

    /**
     * Guarda la puntuación si el usuario no existía o si supera su récord.
     * Devuelve true solo si ha batido su récord anterior.
     */
    fun saveIfBest(user: String, points: Int): Boolean {
        val db = helper.writableDatabase
        val previous: Int? = db.query(
            "Scores", arrayOf("points"), "user = ?", arrayOf(user), null, null, null
        ).use { c -> if (c.moveToFirst()) c.getInt(0) else null }

        val values = ContentValues().apply { put("points", points) }
        return when {
            previous == null -> {
                values.put("user", user)
                db.insert("Scores", null, values)
                false
            }
            points > previous -> {
                db.update("Scores", values, "user = ?", arrayOf(user))
                true
            }
            else -> false
        }
    }

    fun topScores(limit: Int = 7): List<Score> {
        val db = helper.readableDatabase
        return db.query("Scores", null, null, null, null, null, "points desc", limit.toString())
            .use { c ->
                val userIdx = c.getColumnIndexOrThrow("user")
                val pointsIdx = c.getColumnIndexOrThrow("points")
                val list = mutableListOf<Score>()
                while (c.moveToNext()) list.add(Score(c.getString(userIdx), c.getInt(pointsIdx)))
                list
            }
    }
}
