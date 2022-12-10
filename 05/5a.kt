import java.io.InputStreamReader

fun main() {
    val lines = InputStreamReader(System.`in`).readText().split("\n").dropLastWhile { it.isEmpty() }
    val numStacks = (lines[0].length + 1) / 4
    val stacks = mutableListOf<MutableList<Char>>()
    for (i in 1..numStacks) {
        stacks.add(mutableListOf<Char>())
    }

    lines.takeWhile { !it.startsWith(" 1") }.forEach { line ->
        for (i in 0 until numStacks) {
            val char = line[i * 4 + 1]
            if (char != ' ') {
                stacks[i].add(char)
            }
        }
    }

    stacks.forEach { it.reverse() }

    lines.filter { it.startsWith("move") }.forEach { line ->
        val parts = line.split(" ")
        val count = parts[1].toInt()
        val from = parts[3].toInt() - 1
        val to = parts[5].toInt() - 1
        for (i in 1..count) {
            stacks[to].add(stacks[from].removeAt(stacks[from].size - 1))
        }
    }

    println(stacks.map { it.last() }.joinToString(""))
}