import java.io.InputStreamReader

fun main() {
    val lines = InputStreamReader(System.`in`).readText().split("\n").dropLastWhile { it.isEmpty() }
    val numStacks = (lines[0].length + 1) / 4
    val stacks = mutableListOf<MutableList<Char>>()
    repeat(numStacks) {
        stacks.add(mutableListOf<Char>())
    }

    lines.takeWhile { !it.startsWith(" 1") }.forEach { line ->
        repeat(numStacks) {
            val char = line[it * 4 + 1]
            if (char != ' ') {
                stacks[it].add(char)
            }
        }
    }

    stacks.forEach { it.reverse() }

    lines.filter { it.startsWith("move") }.forEach { line ->
        val parts = line.split(" ")
        val count = parts[1].toInt()
        val from = parts[3].toInt() - 1
        val to = parts[5].toInt() - 1

        val crates = stacks[from].takeLast(count)
        repeat(count) {
            stacks[from].removeAt(stacks[from].size - 1)
        }
        stacks[to].addAll(crates)
    }

    println(stacks.map { it.last() }.joinToString(""))
}
