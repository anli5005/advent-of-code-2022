import kotlin.system.exitProcess
import kotlin.math.*

class CPUTen(program: List<String>) {
    data class State(
        var program: MutableList<String>,
        var registers: MutableMap<String, Int> = mutableMapOf("A" to 1),
        var pc: Int = 0,
        var zeroIndexedCycle: Int = -1
    ) {
        val oneIndexedCycle get() = zeroIndexedCycle + 1
        fun copy() = State(program.toMutableList(), registers.toMutableMap(), pc, zeroIndexedCycle)
    }

    fun step(): Boolean {
        if (state.pc >= state.program.size) {
            return true
        }

        val line = state.program[state.pc]
        val parts = line.split(" ")

        when (parts[0]) {
            "noop" -> { cycle {} }
            "addx" -> {
                cycle {}
                cycle {
                    state.registers["A"] = state.registers["A"]!! + parts[1].toInt()
                }
            }
        }

        state.pc += 1
        return false
    }

    fun run() {
        while (!step()) {}
    }

    fun <T> cycle(cycleHandler: () -> T): T {
        fire("beforeCycle")
        state.zeroIndexedCycle++
        fire("duringCycle")
        val result = cycleHandler()
        fire("afterCycle")
        return result
    }

    val state = State(program.toMutableList())
    val listeners = mutableMapOf<String, MutableList<(State) -> Unit>>()

    fun addListener(name: String, listener: (State) -> Unit) {
        val eventListeners = listeners.getOrPut(name) { mutableListOf() }
        eventListeners.add(listener)
    }

    fun fire(name: String) {
        listeners[name]?.forEach { it(state) }
    }

    fun beforeCycle(listener: (State) -> Unit) = addListener("beforeCycle", listener)
    fun duringCycle(listener: (State) -> Unit) = addListener("duringCycle", listener)
    fun afterCycle(listener: (State) -> Unit) = addListener("afterCycle", listener)
}

private val A = object {
    fun run(lines: List<String>) {
        val cpu = CPUTen(lines)
        var sum = 0

        cpu.duringCycle { state ->
            if ((state.oneIndexedCycle - 20) % 40 == 0) {
                sum += state.registers["A"]!! * state.oneIndexedCycle
            }
        }
        
        cpu.run()

        println(sum)
    }
}

private val B = object {
    fun run(lines: List<String>) {
        val cpu = CPUTen(lines)
        val drawnPositions = Array(6) { Array(40) { false } }

        cpu.duringCycle { state ->
            val sprite = state.registers["A"]!!
            val row = state.zeroIndexedCycle / 40
            val col = state.zeroIndexedCycle % 40
            if (abs(sprite - col) <= 1) {
                drawnPositions[row][col] = true
            }
        }

        cpu.run()

        println(drawnPositions.map {
            it.map { if (it) "#" else "." }.joinToString("")
        }.joinToString("\n"))
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