// In retrospect, I should've just parsed this as JSON.

import kotlin.math.*
import kotlin.system.exitProcess
import kotlin.text.*
import kotlinx.serialization.*
import kotlinx.serialization.json.*

interface Thing13: Comparable<Thing13> {
    override fun compareTo(other: Thing13): Int {
        return when (this) {
            is IntThing -> when (other) {
                is IntThing -> this.value.compareTo(other.value)
                is ListThing -> {
                    ListThing(listOf(this)).compareTo(other)
                }
                else -> throw IllegalArgumentException("Unknown type: $other")
            }
            is ListThing -> when (other) {
                is IntThing -> {
                    this.compareTo(ListThing(listOf(other)))
                }
                is ListThing -> {
                    // Compare the elements of each list in order
                    val thisIterator = this.value.iterator()
                    val otherIterator = other.value.iterator()
                    while (thisIterator.hasNext() && otherIterator.hasNext()) {
                        val thisNext = thisIterator.next()
                        val otherNext = otherIterator.next()
                        val comparison = thisNext.compareTo(otherNext)
                        if (comparison != 0) {
                            return comparison
                        }
                    }
                    // If we get here, then one of the lists is a prefix of the other
                    // Pick the shorter list to go first
                    this.value.size.compareTo(other.value.size)
                }
                else -> throw IllegalArgumentException("Unknown type: $other")
            }
            else -> throw IllegalArgumentException("Unknown type: $this")
        }
    }
}

data class IntThing(val value: Int) : Thing13 {
    override fun toString(): String {
        return value.toString()
    }
}
data class ListThing(val value: List<Thing13>) : Thing13 {
    override fun toString(): String {
        return value.joinToString(",", prefix = "[", postfix = "]")
    }
}

fun parseThing(line: String): Pair<Thing13, String> {
    
    // Ints are expressed as themselves
    // Lists are expressed as [1,2,[3,4]]
    // line could either be an int or a list

    // If it's an int, just parse it
    if (line[0] != '[') {
        val num = line.takeWhile { it.isDigit() }
        return IntThing(num.toInt()) to line.substring(num.length)
    }

    // Otherwise, it's a list
    // We need to parse it recursively
    var current = line.substring(1)
    val list = mutableListOf<Thing13>()
    while (current.isNotEmpty()) {
        if (current[0] == ']') {
            // We're done
            return ListThing(list) to current.substring(1)
        } else if (current[0] == ',') {
            // Skip the comma
            current = current.substring(1)
        }
        val (thing, rest) = parseThing(current)
        list.add(thing)
        current = rest
    }
    return ListThing(list) to ""
}

private val A = object {
    fun run(lines: List<String>) {
        val pairs = lines.windowed(2, 3).map { window ->
            val first = parseThing(window[0]).first
            val second = parseThing(window[1]).first
            println(first)
            println(second)
            println()
            first to second
        }

        println(pairs.withIndex().filter {
            // Get only pairs that are in order
            it.value.first < it.value.second
        }.sumOf { it.index + 1 })
    }
}

private val B = object {
    fun run(lines: List<String>) {
        var packets = lines.windowed(2, 3).flatMap { window ->
            val first = parseThing(window[0]).first
            val second = parseThing(window[1]).first
            listOf(first, second)
        }.toMutableList()

        packets.add(ListThing(listOf(ListThing(listOf(IntThing(2))))))
        packets.add(ListThing(listOf(ListThing(listOf(IntThing(6))))))
        packets.sort()

        packets.forEach {
            println(it)
        }
        println((packets.indexOf(ListThing(listOf(ListThing(listOf(IntThing(2)))))) + 1) * (packets.indexOf(ListThing(listOf(ListThing(listOf(IntThing(6)))))) + 1))
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


