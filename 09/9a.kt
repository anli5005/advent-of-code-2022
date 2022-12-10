import java.io.InputStreamReader
import java.lang.System
import kotlin.math.abs

fun isTouching(tail: Pair<Int, Int>, head: Pair<Int, Int>): Boolean {
    if (abs(tail.first - head.first) <= 1 && abs(tail.second - head.second) <= 1) {
        return true
    }

    return false
}

fun main() {
    val lines = InputStreamReader(System.`in`).readText().split("\n").dropLastWhile { it.isEmpty() }
    var visitedByTail = mutableSetOf<Pair<Int, Int>>()
    var tailPos = Pair(0, 0)
    var headPos = Pair(0, 0)

    lines.forEach { line ->
        visitedByTail.add(tailPos)
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
// See if the tail is 2 steps away from the head in a direction
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

visitedByTail.add(tailPos)

        }
    }

    print(visitedByTail.size)
}
