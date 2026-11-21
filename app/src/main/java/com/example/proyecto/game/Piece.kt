package com.example.proyecto

/** Una casilla del tablero (fila 0 = arriba, columna 0 = izquierda). */
data class Cell(val row: Int, val col: Int)

/**
 * Pieza en juego. Es inmutable: moverla o rotarla devuelve una pieza NUEVA,
 * así se puede probar la posición candidata en el tablero sin deshacer nada.
 */
data class Piece(
    val type: PieceType,
    val row: Int = 0,
    val col: Int = type.spawnCol,
    val rotation: Int = 0
) {
    /** Casillas absolutas que ocupa la pieza en el tablero. */
    fun cells(): List<Cell> =
        type.states[rotation % type.states.size].map { Cell(row + it.row, col + it.col) }

    fun moved(dRow: Int, dCol: Int): Piece = copy(row = row + dRow, col = col + dCol)

    fun rotated(): Piece = copy(rotation = (rotation + 1) % type.states.size)
}
