import kotlin.system.exitProcess
import kotlin.math.*

data class Monkey(
    val items: ArrayDeque<Long>,
    val operation: (Long) -> Long,
    val getDestination: (Long) -> Int,
    var inspections: Long = 0
)

fun parseMonkeys(lines: List<String>): Pair<List<Monkey>, Int> {
    var modulus = 1
    val monkeys = lines.windowed(6, 7).map { monkey ->
        modulus *= monkey[3].split(" ").last().toInt()
        Monkey(
            monkey[1].substringAfter("Starting items: ").split(", ").map { it.toLong() }.toCollection(ArrayDeque()),
            monkey[2].substringAfter("Operation: new = old ").let {
                val parts = it.split(" ")
                val operand = when (parts[1]) {
                    "old" -> { x: Long -> x }
                    else -> { _: Long -> parts[1].toLong() }
                }
                when (parts[0]) {
                    "+" -> { x: Long -> x + operand(x) }
                    "*" -> {
                        { x: Long -> x * operand(x) }
                    }
                    else -> throw Exception("Unknown operation: ${parts[0]}")
                }
            },
            { worry ->
                monkey[if (worry % monkey[3].split(" ").last().toLong() == 0L) 4 else 5].split(" ").last().toInt()
            }
        )
    }
    return monkeys to modulus
}

private val A = object {
    fun run(lines: List<String>) {
        val (monkeys, _) = parseMonkeys(lines)

        for (round in 1..20) {
            monkeys.forEach { monkey ->
                while (monkey.items.isNotEmpty()) {
                    val item = monkey.items.removeFirst()
                    val worry = monkey.operation(item)
                    monkeys[monkey.getDestination(worry)].items.add(worry)
                    monkey.inspections++
                }
            }
        }

        monkeys.withIndex().forEach {
            println("Monkey ${it.index + 1}: ${it.value.inspections} inspections")
        }

        val active = monkeys.map { it.inspections }.sortedDescending()
        println(active[0] * active[1])
    }
}

private val B = object {
    fun run(lines: List<String>) {
        val (monkeys, modulus) = parseMonkeys(lines)

        for (round in 1..10000) {
            monkeys.forEach { monkey ->
                while (monkey.items.isNotEmpty()) {
                    val item = monkey.items.removeFirst()
                    val worry = monkey.operation(item) % modulus
                    monkeys[monkey.getDestination(worry)].items.add(worry)
                    monkey.inspections++
                }
            }
        }

        monkeys.withIndex().forEach {
            println("Monkey ${it.index + 1}: ${it.value.inspections} inspections")
        }

        val active = monkeys.map { it.inspections }.sortedDescending()
        println(active[0] * active[1])
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