import java.io.InputStreamReader
import java.lang.System

fun main() {
    val lines = InputStreamReader(System.`in`).readText().split("\n").dropLastWhile { it.isEmpty() }
    val transmission = lines[0]
    for (i in 4..transmission.length) {
        if (transmission.substring(i - 4, i).toSet().size == 4) {
            println(i)
            break
        }
    }
}
