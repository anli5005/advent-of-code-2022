import kotlin.math.*
import kotlin.system.exitProcess
import kotlin.text.*

val rockPatterns = listOf(
    setOf(
        0 to 0,
        0 to 1,
        0 to 2,
        0 to 3
    ),
    setOf(
        0 to 1,
        1 to 0,
        1 to 1,
        1 to 2,
        2 to 1
    ),
    setOf(
        2 to 2,
        1 to 2,
        0 to 0,
        0 to 1,
        0 to 2
    ),
    setOf(
        0 to 0,
        1 to 0,
        2 to 0,
        3 to 0
    ),
    setOf(
        0 to 0,
        0 to 1,
        1 to 0,
        1 to 1
    )
)

fun checkMovement(rockPattern: Set<Pair<Int, Int>>, rocks: List<List<Boolean>>, position: Pair<Int, Int>): Boolean {
    return rockPattern.all {
        val row = position.first + it.first
        val col = position.second + it.second
        if (col < 0 || col >= 7) {
            false
        } else if (row >= rocks.size) {
            true
        } else if (row < 0) {
            false
        } else {
            !rocks[row][col]
        }
    }
}

private val A = object {
    fun run(lines: List<String>) {
        val movements = lines[0]
        var i = 0
        var numRocks = 0
        val rocks = mutableListOf<MutableList<Boolean>>(mutableListOf(false, false, false, false, false, false, false))
        var currentRockIndex = 0
        var currentRockPos = 3 to 2
        while (numRocks < 2022) {
            val moveDown = i % 2 == 1
            assert(checkMovement(rockPatterns[currentRockIndex], rocks, currentRockPos))
            if (moveDown) {
                val targetPos = (currentRockPos.first - 1) to currentRockPos.second
                if (checkMovement(rockPatterns[currentRockIndex], rocks, targetPos)) {
                    // println("Moving rock down")
                    currentRockPos = targetPos
                } else {
                    // Resize array as needed
                    val requiredSize = currentRockPos.first + rockPatterns[currentRockIndex].maxOf { it.first } + 1
                    if (requiredSize > rocks.size) {
                        for (i in (rocks.size + 1)..requiredSize) {
                            rocks.add(mutableListOf(false, false, false, false, false, false, false))
                        }
                    }

                    rockPatterns[currentRockIndex].forEach {
                        assert(!rocks[currentRockPos.first + it.first][currentRockPos.second + it.second])
                        rocks[currentRockPos.first + it.first][currentRockPos.second + it.second] = true
                    }

                    assert(rocks.last().contains(true))

                    // println("Dropped ${numRocks + 1} rocks: ${rocks.size} - Pattern $currentRockIndex - increase of ${rocks.size - oldSize} (req $requiredSize)")

                    currentRockPos = (rocks.size + 3) to 2
                    currentRockIndex = (currentRockIndex + 1) % 5
                    numRocks += 1
                }
            } else {
                val moveRight = movements[(i / 2) % movements.length] == '>'
                val targetPos = currentRockPos.first to (currentRockPos.second + if (moveRight) 1 else -1)
                if (checkMovement(rockPatterns[currentRockIndex], rocks, targetPos)) {
                    currentRockPos = targetPos
                }
            }
            i += 1
            if (i % 2 == 1 && ((i + 1) / 2) % movements.length == 0) {
                println("$numRocks rocks (size ${rocks.size}):")
                rocks.withIndex().reversed().take(4).forEach { row ->
                    println(row.value.withIndex().map { col ->
                        if (col.value) "#" else "."
                    }.joinToString(""))
                }
            }
        }

        println(rocks.size)
    }
}

private val B = object {
    fun run(lines: List<String>) {
        // This relies on the fact that after every repeat of the fan string,
        // the configuration of the rocks on the top and the number of rocks
        // and units of height added are the same
        // 
        // This isn't necessarily true with *every* test case, though by
        // running the above test case A on the sample input you find that
        // the sample input also exhibits this pattern every 5 or so repeats
        // of the fan string

        val movements = lines[0]
        var i = 0
        var numRocks = 0L
        val rocks = mutableListOf<MutableList<Boolean>>(mutableListOf(false, false, false, false, false, false, false))
        var currentRockIndex = 0
        var currentRockPos = 3 to 2
        var initialHeight = 0
        var initialRocks = 0L
        var rockDelta = 0L
        var heightDelta = 0
        var oldHeight = 0
        while (true) {
            val moveDown = i % 2 == 1
            assert(checkMovement(rockPatterns[currentRockIndex], rocks, currentRockPos))
            if (moveDown) {
                val targetPos = (currentRockPos.first - 1) to currentRockPos.second
                if (checkMovement(rockPatterns[currentRockIndex], rocks, targetPos)) {
                    // println("Moving rock down")
                    currentRockPos = targetPos
                } else {
                    // Resize array as needed
                    val requiredSize = currentRockPos.first + rockPatterns[currentRockIndex].maxOf { it.first } + 1
                    if (requiredSize > rocks.size) {
                        for (i in (rocks.size + 1)..requiredSize) {
                            rocks.add(mutableListOf(false, false, false, false, false, false, false))
                        }
                    }

                    rockPatterns[currentRockIndex].forEach {
                        assert(!rocks[currentRockPos.first + it.first][currentRockPos.second + it.second])
                        rocks[currentRockPos.first + it.first][currentRockPos.second + it.second] = true
                    }

                    assert(rocks.last().contains(true))

                    // println("Dropped ${numRocks + 1} rocks: ${rocks.size} - Pattern $currentRockIndex - increase of ${rocks.size - oldSize} (req $requiredSize)")

                    currentRockPos = (rocks.size + 3) to 2
                    currentRockIndex = (currentRockIndex + 1) % 5
                    numRocks += 1L
                }
            } else {
                val moveRight = movements[(i / 2) % movements.length] == '>'
                val targetPos = currentRockPos.first to (currentRockPos.second + if (moveRight) 1 else -1)
                if (checkMovement(rockPatterns[currentRockIndex], rocks, targetPos)) {
                    currentRockPos = targetPos
                }
            }
            i += 1
            if (i == movements.length * 2 - 1) {
                initialRocks = numRocks
                initialHeight = rocks.size
                println("$numRocks rocks (size ${rocks.size}):")
                rocks.withIndex().reversed().take(4).forEach { row ->
                    println(row.value.withIndex().map { col ->
                        if (col.value) "#" else "."
                    }.joinToString(""))
                }
            }
            if (i == movements.length * 4 - 1) {
                rockDelta = numRocks - initialRocks
                heightDelta = rocks.size - initialHeight
                oldHeight = rocks.size
                numRocks += (1_000_000_000_000L / rockDelta - 2L) * rockDelta
                println("Rock delta $rockDelta, heightDelta $heightDelta")
                println("$numRocks rocks (size ${rocks.size}):")
                rocks.withIndex().reversed().take(4).forEach { row ->
                    println(row.value.withIndex().map { col ->
                        if (col.value) "#" else "."
                    }.joinToString(""))
                }
            }
            if (numRocks == 1_000_000_000_000L) {
                println((rocks.size - oldHeight).toLong() + initialHeight.toLong() + heightDelta.toLong() * ((1_000_000_000_000L - initialRocks) / rockDelta))
                println("$numRocks rocks (size ${rocks.size}):")
                rocks.withIndex().reversed().take(4).forEach { row ->
                    println(row.value.withIndex().map { col ->
                        if (col.value) "#" else "."
                    }.joinToString(""))
                }
                break
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
