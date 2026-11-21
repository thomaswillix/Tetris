package com.example.proyecto.game

/**
 * Tablero de 15 filas x 10 columnas. Cada casilla guarda 0 (vacía) o el id del
 * tipo de pieza que se bloqueó ahí.
 */
class Board {
    companion object {
        const val ROWS = 15
        const val COLS = 10
    }

    private val grid = IntArray(ROWS * COLS)

    operator fun get(row: Int, col: Int): Int = grid[row * COLS + col]

    /** true si todas las casillas de la pieza están dentro del tablero y libres. */
    fun fits(piece: Piece): Boolean = piece.cells().all {
        it.row in 0 until ROWS && it.col in 0 until COLS && grid[it.row * COLS + it.col] == 0
    }

    /** Fija la pieza en el tablero. */
    fun lock(piece: Piece) {
        piece.cells().forEach { grid[it.row * COLS + it.col] = piece.type.id }
    }

    /**
     * Elimina todas las filas completas y baja las de arriba de una sola vez.
     * Devuelve cuántas filas se eliminaron.
     */
    fun clearFullRows(): Int {
        val keptRows = (0 until ROWS).filter { r -> (0 until COLS).any { c -> grid[r * COLS + c] == 0 } }
        val cleared = ROWS - keptRows.size
        if (cleared == 0) return 0

        val newGrid = IntArray(ROWS * COLS)
        var target = ROWS - 1
        for (r in keptRows.reversed()) {
            grid.copyInto(newGrid, target * COLS, r * COLS, (r + 1) * COLS)
            target--
        }
        newGrid.copyInto(grid)
        return cleared
    }
}
