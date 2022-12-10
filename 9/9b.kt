import java.io.InputStreamReader
import java.lang.System
import kotlin.math.abs

fun getNewTailPos(tail: Pair<Int, Int>, headPos: Pair<Int, Int>): Pair<Int, Int> {
    var tailPos = tail
    if (tailPos.first == headPos.first && abs(tailPos.second - headPos.second) == 2) {
    // Move the tail one step in the direction of the head
    if (tailPos.second < headPos.second) {
        tailPos = Pair(tailPos.first, tailPos.second + 1)
    } else {
        tailPos = Pair(tailPos.first, tailPos.second - 1)
    }
} else if (tailPos.second == headPos.second && abs(tailPos.first - headPos.first) == 2) {
    if (tailPos.first < headPos.first) {
        tailPos = Pair(tailPos.first + 1, tailPos.second)
    } else {
        tailPos = Pair(tailPos.first - 1, tailPos.second)
    }
} else if (!isTouching(tailPos, headPos)) {
    val positions =
            listOf(
                    Pair(tailPos.first + 1, tailPos.second + 1),
                    Pair(tailPos.first - 1, tailPos.second - 1),
                    Pair(tailPos.first - 1, tailPos.second + 1),
                    Pair(tailPos.first + 1, tailPos.second - 1)
            )

    tailPos = positions.first { isTouching(it, headPos) }

}
return tailPos

}

fun main() {
    val lines = InputStreamReader(System.`in`).readText().split("\n").dropLastWhile { it.isEmpty() }
    var visitedByTail = mutableSetOf<Pair<Int, Int>>()
    var tails = mutableListOf<Pair<Int, Int>>()
    for (i in 1..9) {
        tails.add(Pair(0, 0))
    }
    var headPos = Pair(0, 0)

    visitedByTail.add(Pair(0, 0))

    lines.forEach { line ->
        val parts = line.split(" ")
        val direction = parts[0]
        val count = parts[1].toInt()
        for (i in 1..count) {
            when (direction) {
                "R" -> {
                    headPos = Pair(headPos.first, headPos.second + 1)
                }
                "L" -> {
                    headPos = Pair(headPos.first, headPos.second - 1)
                }
                "U" -> {
                    headPos = Pair(headPos.first + 1, headPos.second)
                }
                "D" -> {
                    headPos = Pair(headPos.first - 1, headPos.second)
                }
            }
            for (j in tails.size - 1 downTo 0) {
                tails[j] = getNewTailPos(tails[j], if (j == tails.size - 1) headPos else tails[j + 1])
            }
            visitedByTail.add(tails[0])
        }
    }

    print(visitedByTail.toSet().size)
}
