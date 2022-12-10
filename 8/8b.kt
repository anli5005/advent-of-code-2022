import java.io.InputStreamReader
import java.lang.System

fun getScenicScore(heights: List<List<Int>>, row: Int, column: Int): Int {
    var topViewingDistance = row
    for (i in 0 until row) {
        if (heights[i][column] >= heights[row][column]) {
            topViewingDistance = row - i
        }
    }

    var leftViewingDistance = column
    for (i in 0 until column) {
        if (heights[row][i] >= heights[row][column]) {
            leftViewingDistance = column - i
        }
    }

    var rightViewingDistance = heights[row].size - column - 1
    for (i in heights[row].size - 1 downTo column + 1) {
        if (heights[row][i] >= heights[row][column]) {
            rightViewingDistance = i - column
        }
    }

    var bottomViewingDistance = heights.size - row - 1
    for (i in heights.size - 1 downTo row + 1) {
        if (heights[i][column] >= heights[row][column]) {
            bottomViewingDistance = i - row
        }
    }

    println("$row $column $topViewingDistance $leftViewingDistance $rightViewingDistance $bottomViewingDistance")
    return topViewingDistance * leftViewingDistance * rightViewingDistance * bottomViewingDistance
}

fun main() {
    val lines = InputStreamReader(System.`in`).readText().split("\n").dropLastWhile { it.isEmpty() }
    val heights = lines.map { it.map { String(charArrayOf(it)).toInt() } }
    var scenicScore = 0
    for (row in 0 until heights.size) {
        for (column in 0 until heights[row].size) {
            val score = getScenicScore(heights, row, column)
            if (score > scenicScore) {
                scenicScore = score
            }
        }
    }
    println(scenicScore)
}
