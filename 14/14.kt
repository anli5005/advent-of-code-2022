import kotlin.math.*
import kotlin.system.exitProcess
import kotlin.text.*

fun parseMap(lines: List<String>): MutableSet<Pair<Int, Int>> {
    var set = mutableSetOf<Pair<Int, Int>>()
    lines.forEach {
        it.split(" -> ").map { 
            val parts = it.split(",")
            parts[0].toInt() to parts[1].toInt()
        }.windowed(2, 1).forEach {
            val a = it[0]
            val b = it[1]

            // Add all points between a and b, assuming they are on the same
            // horizontal/vertical line

            if (a.first == b.first) {
                // Vertical line
                val y1 = min(a.second, b.second)
                val y2 = max(a.second, b.second)
                for (y in y1..y2) {
                    set.add(a.first to y)
                }
            } else {
                // Horizontal line
                val x1 = min(a.first, b.first)
                val x2 = max(a.first, b.first)
                for (x in x1..x2) {
                    set.add(x to a.second)
                }
            }
        }
    }
    return set
}

private val A = object {
    fun run(lines: List<String>) {
        val rock = parseMap(lines)
        var sand = mutableSetOf<Pair<Int, Int>>()
        // Simulate sand falling from (500, 0)
        // Sand prefers to fall down
        // If it can't, it will try to flow down and to the left
        // Otherwise, it will try to flow down and to the right
        // Add the final position to the sand set
        // If it flows off the map, it's done
        while (true) {
            val obstacles = rock union sand
            var current = 500 to 0
            while (true) {
                var down = current.first to (current.second + 1)
                if (down.second > 2000) {
                    // Done
                    println(sand.size)
                    return
                }
                if (down in obstacles) {
                    // Try down left
                    var left = current.first - 1 to (current.second + 1)
                    if (left !in obstacles) {
                        current = left
                        continue
                    }

                    // Try down right
                    var right = current.first + 1 to (current.second + 1)
                    if (right !in obstacles) {
                        current = right
                        continue
                    }

                    // We're done
                    sand.add(current)
                    break
                } else {
                    current = down
                }
            }
        }
    }
}

private val B = object {
    fun run(lines: List<String>) {
        val rock = parseMap(lines)
        var sand = mutableSetOf<Pair<Int, Int>>()
        val endY = rock.maxOf { it.second }
        // Simulate sand falling from (500, 0)
        // Sand prefers to fall down
        // If it can't, it will try to flow down and to the left
        // Otherwise, it will try to flow down and to the right
        // Add the final position to the sand set
        // If it flows off the map, it's done
        while (true) {
            val obstacles = rock union sand
            var current = 500 to 0
            if (current in obstacles) {
                println(sand.size)
                return
            }
            while (true) {
                var down = current.first to (current.second + 1)
                if (down.second == endY + 2) {
                    // Done
                    sand.add(current)
                    break
                }
                if (down in obstacles) {
                    // Try down left
                    var left = current.first - 1 to (current.second + 1)
                    if (left !in obstacles) {
                        current = left
                        continue
                    }

                    // Try down right
                    var right = current.first + 1 to (current.second + 1)
                    if (right !in obstacles) {
                        current = right
                        continue
                    }

                    // We're done
                    sand.add(current)
                    break
                } else {
                    current = down
                }
            }
        }
    }
}

fun main(args: Array<String>) {
    val lines = generateSequence(::readLine).toList()

    if (args.isEmpty()) {
        println("Please specify which part to run (a or b)")
        exitProcess(1)
    }

    when (args[0].lowercase()) {
        "a" -> A.run(lines)
        "b" -> B.run(lines)
        else -> {
            println("Unknown part: ${args[0]}")
            exitProcess(1)
        }
    }
}


