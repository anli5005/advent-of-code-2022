import java.io.InputStreamReader
import java.lang.System

fun main() {
    val lines = InputStreamReader(System.`in`).readText().split("\n").dropLastWhile { it.isEmpty() }
    val transmission = lines[0]
    for (i in 14..transmission.length) {
        if (transmission.substring(i - 14, i).toSet().size == 14) {
            println(i)
            break
        }
    }
}
