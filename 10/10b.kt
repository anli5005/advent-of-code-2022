import java.io.InputStreamReader
import java.lang.System
import kotlin.math.abs

val drawnPositions = mutableSetOf<Int>()

var bcycle = -1

fun incrementCycleCRT(amount: Int) {
    for (i in 1..amount) {
        bcycle++
        if (abs(register - bcycle % 40) <= 1) {
            drawnPositions.add(bcycle)
        }
    }
}

fun main() {
    val lines = InputStreamReader(System.`in`).readText().split("\n").dropLastWhile { it.isEmpty() }
    incrementCycleCRT(1)
    lines.forEach { line ->
        val parts = line.split(" ")
        when (parts[0]) {
            "noop" -> {
                incrementCycleCRT(1)
            }
            "addx" -> {
                incrementCycleCRT(1)
                register += parts[1].toInt()
                incrementCycleCRT(1)
            }
        }
    }
    for (i in 0 until 6) {
        for (j in 0 until 40) {
            print(if (drawnPositions.contains(i * 40 + j)) "#" else ".")
        }
        println()
    }
}
