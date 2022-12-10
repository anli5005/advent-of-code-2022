import java.io.InputStreamReader
import java.lang.System

fun isTreeVisible(heights: List<List<Int>>, row: Int, column: Int): Boolean {
    var visibleTop = true
    for (i in 0 until row) {
        if (heights[i][column] >= heights[row][column]) {
            visibleTop = false
        }
    }

    var visibleLeft = true
    for (i in 0 until column) {
        if (heights[row][i] >= heights[row][column]) {
            visibleLeft = false
        }
    }

    var visibleRight = true
    for (i in column + 1 until heights[row].size) {
        if (heights[row][i] >= heights[row][column]) {
            visibleRight = false
        }
    }

    var visibleBottom = true
    for (i in row + 1 until heights.size) {
        if (heights[i][column] >= heights[row][column]) {
            visibleBottom = false
        }
    }

    return visibleTop || visibleLeft || visibleRight || visibleBottom
}

fun main() {
    val lines = InputStreamReader(System.`in`).readText().split("\n").dropLastWhile { it.isEmpty() }
    val heights = lines.map { it.map { String(charArrayOf(it)).toInt() } }
    var visibleTrees = 0
    for (row in 0 until heights.size) {
        for (column in 0 until heights[row].size) {
            if (isTreeVisible(heights, row, column)) {
                visibleTrees++
            }
        }
    }
    println(visibleTrees)
}
