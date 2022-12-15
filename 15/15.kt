import kotlin.math.*
import kotlin.system.exitProcess
import kotlin.text.*

fun IntRange.intersects(other: IntRange): Boolean {
    return max(this.first, other.first) <= min(this.last, other.last)
}

fun IntRange.intersection(other: IntRange): IntRange {
    return max(this.first, other.first)..min(this.last, other.last)
}

// Implement a range set
// (I typed that comment, typed the next line, then waited for Copilot to do its thing.)
// 
// 
// It did not do its thing.
class RangeSet {
    var ranges = mutableSetOf<IntRange>()

    fun addRange(range: IntRange) {
        // See if any of the ranges intersect
        val intersectingRanges = ranges.filter { it.intersects((range.first - 1)..(range.last + 1)) }

        // Otherwise, merge the ranges
        val toMerge = intersectingRanges.toMutableSet()
        toMerge.add(range)
        ranges.removeAll(intersectingRanges)
        val newRange = (toMerge.minOf { it.first }..toMerge.maxOf { it.last })
        ranges.add(newRange)
    }

    fun intersection(other: RangeSet): RangeSet {
        val newSet = RangeSet()
        ranges.forEach { range ->
            other.ranges.forEach { otherRange ->
                if (range.intersects(otherRange)) {
                    newSet.addRange(range.intersection(otherRange))
                }
            }
        }
        return newSet
    }

    override fun toString(): String {
        return ranges.joinToString(", ") { "${it.first} to ${it.last}" }
    }
}

fun parseLines(lines: List<String>): List<Pair<Pair<Int, Int>, Pair<Int, Int>>> {
    return lines.map {
        var thing = it.substringAfter("Sensor at x=")
        val sensorX = thing.substringBefore(",").toInt()
        thing = thing.substringAfter("y=")
        val sensorY = thing.substringBefore(":").toInt()
        thing = thing.substringAfter("closest beacon is at x=")
        val beaconX = thing.substringBefore(",").toInt()
        thing = thing.substringAfter("y=")
        val beaconY = thing.substringBefore(")").toInt()
        (sensorX to sensorY) to (beaconX to beaconY)
    }
}

private val A = object {
    fun run(lines: List<String>) {
        val sensors = parseLines(lines)

        var noBeacons = RangeSet()
        sensors.forEach {
            val (a, b) = it
            val (sensorX, sensorY) = a
            val (beaconX, beaconY) = b
            
            // Calculate Manhattan distance
            val distance = abs(sensorX - beaconX) + abs(sensorY - beaconY)

            // Calculate distance from here to y = 2000000
            val y = 2000000
            val yDistance = abs(sensorY - y)

            if (yDistance <= distance) {
                val maxXDistance = distance - yDistance
                // Add all x values that are within this distance
                val minX = sensorX - maxXDistance
                val maxX = sensorX + maxXDistance
                val range = minX..maxX
                if (beaconY == y && range.contains(beaconX)) {
                    // println("Beacon at $beaconX")
                    if (minX != beaconX)
                        noBeacons.addRange(minX..(beaconX - 1))
                    if (maxX != beaconX)
                        noBeacons.addRange((beaconX + 1)..maxX)
                } else {
                    noBeacons.addRange(range)
                }
            }
        }

        println(noBeacons.ranges.sumOf { it.endInclusive - it.start + 1 })
        noBeacons.ranges.forEach {
            println("Range: ${it.start} to ${it.endInclusive}")
        }
    }
}

private val B = object {
    fun run(lines: List<String>) {
        val sensors = parseLines(lines)

        for (y in 0..4000000) {
            if (y % 100000 == 0)
                println("Y: $y")
            var noBeacons = RangeSet()
            sensors.forEach {
                val (a, b) = it
                val (sensorX, sensorY) = a
                val (beaconX, beaconY) = b
                
                // Calculate Manhattan distance
                val distance = abs(sensorX - beaconX) + abs(sensorY - beaconY)
                val yDistance = abs(sensorY - y)

                if (yDistance > distance)
                    return@forEach

                val maxXDistance = distance - yDistance
                // Add all x values that are within this distance
                val minX = sensorX - maxXDistance
                val maxX = sensorX + maxXDistance
                val range = minX..maxX
                noBeacons.addRange(range)
            }

            noBeacons = noBeacons.intersection(RangeSet().apply { addRange(0..4000000) })

            if (noBeacons.ranges.size > 1) {
                val x = noBeacons.ranges.first().last + 1
                val frequency = x.toLong() * 4000000L + y.toLong()
                println("($x, $y) -> $frequency")
                return
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


