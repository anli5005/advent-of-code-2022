import kotlin.math.*
import kotlin.system.exitProcess
import java.util.ArrayDeque

private val A = object {
    fun run(lines: List<String>) {
        var start = 0 to 0
        var end = 0 to 0
        val heightmap = lines.withIndex().map { line -> line.value.withIndex().map {
            when (it.value) {
                'S' -> {
                    start = line.index to it.index
                    0
                }
                'E' -> {
                    end = line.index to it.index
                    25
                }
                else -> it.value.code - 'a'.code
            }
        } }

        // Run BFS to find the shortest path
        val queue = ArrayDeque<Pair<Int, Int>>()
        queue.add(start)
        val visited = mutableSetOf<Pair<Int, Int>>()
        val distances = mutableMapOf(start to 0)
        while (queue.isNotEmpty()) {
            val (x, y) = queue.removeFirst()
            if (visited.contains(x to y)) continue
            visited.add(x to y)
            val distance = distances[x to y]!!
            if (x to y == end) {
                println(distance)
                break
            }
            val height = heightmap[x][y]
            // This British spelling was Copilot's doing
            val neighbours = listOf(
                x to y - 1,
                x to y + 1,
                x - 1 to y,
                x + 1 to y
            ).filter { it.first in heightmap.indices && it.second in heightmap[0].indices }
            neighbours.forEach { neighbour ->
                val neighbourHeight = heightmap[neighbour.first][neighbour.second]
                if (height + 1 >= neighbourHeight) {
                    queue.add(neighbour)
                    distances[neighbour] = distance + 1
                }
            }
        }
    }
}

private val B = object {
    fun run(lines: List<String>) {
        var end = 0 to 0
        val heightmap = lines.withIndex().map { line -> line.value.withIndex().map {
            when (it.value) {
                'S' -> 0
                'E' -> {
                    end = line.index to it.index
                    25
                }
                else -> it.value.code - 'a'.code
            }
        } }

        // Run BFS to find the shortest path
        val queue = ArrayDeque<Pair<Int, Int>>()
        queue.add(end)
        val visited = mutableSetOf<Pair<Int, Int>>()
        val distances = mutableMapOf(end to 0)
        while (queue.isNotEmpty()) {
            val (x, y) = queue.removeFirst()
            if (visited.contains(x to y)) continue
            visited.add(x to y)
            val distance = distances[x to y]!!
            if (heightmap[x][y] == 0) {
                println(distance)
                break
            }
            val height = heightmap[x][y]
            // This British spelling was Copilot's doing
            val neighbours = listOf(
                x to y - 1,
                x to y + 1,
                x - 1 to y,
                x + 1 to y
            ).filter { it.first in heightmap.indices && it.second in heightmap[0].indices }
            neighbours.forEach { neighbour ->
                val neighbourHeight = heightmap[neighbour.first][neighbour.second]
                if (neighbourHeight + 1 >= height) {
                    queue.add(neighbour)
                    distances[neighbour] = distance + 1
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


