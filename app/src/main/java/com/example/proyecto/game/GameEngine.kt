package com.example.proyecto

import kotlin.random.Random

/**
 * Reglas del juego, sin nada de Android. La Activity solo llama a estos métodos
 * (tick/moveLeft/...) y luego vuelve a pintar el estado.
 */
class GameEngine(
    val board: Board = Board(),
    private val random: Random = Random.Default
) {
    companion object {
        private const val LOCK_POINTS = 15      // al colocar una pieza
        private const val HARD_DROP_POINTS = 5  // al soltar con el botón "abajo"
        private const val LINE_POINTS = 100     // por línea completada
        private val KICKS = intArrayOf(0, -1, 1, -2, 2) // desplazamientos probados al rotar junto a una pared
    }

    var currentPiece: Piece? = null
        private set
    var nextType: PieceType = randomType()
        private set
    var score = 0
        private set
    var linesCleared = 0
        private set
    var isGameOver = false
        private set

    fun start() = spawn()

    fun moveLeft(): Boolean = tryReplace { it.moved(0, -1) }

    fun moveRight(): Boolean = tryReplace { it.moved(0, 1) }

    /** Rota; si choca con una pared o un bloque prueba a desplazarla 1-2 casillas. */
    fun rotate(): Boolean {
        val piece = currentPiece ?: return false
        if (isGameOver) return false
        val rotated = piece.rotated()
        for (kick in KICKS) {
            val candidate = rotated.moved(0, kick)
            if (board.fits(candidate)) {
                currentPiece = candidate
                return true
            }
        }
        return false
    }

    /** Gravedad: baja una fila; si no puede, bloquea la pieza y saca la siguiente. */
    fun tick() {
        if (isGameOver) return
        if (!tryReplace { it.moved(1, 0) }) lockCurrent()
    }

    /** Baja la pieza hasta el fondo y la bloquea de inmediato. */
    fun hardDrop(): Boolean {
        val piece = currentPiece ?: return false
        if (isGameOver) return false
        var dropped = piece
        while (board.fits(dropped.moved(1, 0))) dropped = dropped.moved(1, 0)
        currentPiece = dropped
        score += HARD_DROP_POINTS
        lockCurrent()
        return true
    }

    private fun tryReplace(change: (Piece) -> Piece): Boolean {
        val piece = currentPiece ?: return false
        if (isGameOver) return false
        val candidate = change(piece)
        if (!board.fits(candidate)) return false
        currentPiece = candidate
        return true
    }

    private fun lockCurrent() {
        val piece = currentPiece ?: return
        board.lock(piece)
        score += LOCK_POINTS
        val cleared = board.clearFullRows()
        linesCleared += cleared
        score += cleared * LINE_POINTS
        spawn()
    }

    private fun spawn() {
        val piece = Piece(nextType)
        nextType = randomType()
        if (board.fits(piece)) {
            currentPiece = piece
        } else {
            currentPiece = null
            isGameOver = true
        }
    }

    private fun randomType(): PieceType {
        val all = PieceType.values()
        return all[random.nextInt(all.size)]
    }
}
