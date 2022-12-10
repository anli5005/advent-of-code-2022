import java.io.InputStreamReader
import java.lang.System

var cycle = 0
var sum = 0
var register = 1

fun incrementCycle(amount: Int) {
    for (i in 1..amount) {
        cycle++
        if ((cycle - 20) % 40 == 0) {
            sum += register * cycle
        }
    }
}

fun main() {
    val lines = InputStreamReader(System.`in`).readText().split("\n").dropLastWhile { it.isEmpty() }
    lines.forEach { line ->
        val parts = line.split(" ")
        when (parts[0]) {
            "noop" -> {
                incrementCycle(1)
            }
            "addx" -> {
                incrementCycle(1)
                incrementCycle(1)
                register += parts[1].toInt()
            }
        }
    }
    println(sum)
}
