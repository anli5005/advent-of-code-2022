import java.io.InputStreamReader
import java.lang.System

fun main() {
    val lines = InputStreamReader(System.`in`).readText().split("\n").dropLastWhile { it.isEmpty() }
    println(lines.count { line ->
        val assignments = line.split(",").map {
            val parts = it.split("-")
            val a = parts[0].toInt()
            val b = parts[1].toInt()
            a.rangeTo(b).toSet()
        }

        (assignments[0] intersect assignments[1]).isNotEmpty()
    })
}