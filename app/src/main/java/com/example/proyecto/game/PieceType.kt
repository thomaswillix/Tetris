package com.example.proyecto.game

private fun cells(vararg rc: Pair<Int, Int>): List<Cell> = rc.map { Cell(it.first, it.second) }

/** Genera las rotaciones en sentido horario dentro de una caja size x size. */
private fun rotations(base: List<Cell>, size: Int, count: Int): List<List<Cell>> {
    val result = mutableListOf(base)
    repeat(count - 1) {
        result.add(result.last().map { Cell(it.col, size - 1 - it.row) })
    }
    return result
}

/**
 * id: 1..7, coincide con el índice del drawable de color (0 = casilla vacía).
 * previewCells: índices (0..11) en el grid pequeño de "siguiente pieza" (3 columnas x 4 filas).
 * states: casillas de cada rotación, relativas a la esquina superior izquierda de la pieza.
 */
enum class PieceType(
    val id: Int,
    val spawnCol: Int,
    val previewCells: List<Int>,
    val states: List<List<Cell>>
) {
    L(1, 3, listOf(5, 6, 7, 8), rotations(cells(0 to 2, 1 to 0, 1 to 1, 1 to 2), 3, 4)),
    Z(2, 3, listOf(3, 4, 7, 8), rotations(cells(0 to 0, 0 to 1, 1 to 1, 1 to 2), 3, 2)),
    O(3, 4, listOf(4, 5, 7, 8), rotations(cells(0 to 0, 0 to 1, 1 to 0, 1 to 1), 2, 1)),
    J(4, 3, listOf(3, 6, 7, 8), rotations(cells(0 to 0, 1 to 0, 1 to 1, 1 to 2), 3, 4)),
    S(5, 3, listOf(4, 5, 6, 7), rotations(cells(0 to 1, 0 to 2, 1 to 0, 1 to 1), 3, 2)),
    T(6, 3, listOf(4, 6, 7, 8), rotations(cells(0 to 1, 1 to 0, 1 to 1, 1 to 2), 3, 4)),
    I(
        7, 3, listOf(1, 4, 7, 10),
        listOf(
            cells(0 to 0, 0 to 1, 0 to 2, 0 to 3),
            cells(0 to 1, 1 to 1, 2 to 1, 3 to 1)
        )
    )
}