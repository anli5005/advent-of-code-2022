import java.io.InputStreamReader
import java.lang.System

fun main() {
    val lines = InputStreamReader(System.`in`).readText().split("\n").dropLastWhile { it.isEmpty() }
    val root = Directory(mutableMapOf(), mutableMapOf())
    var current = mutableListOf(root)
    var currentCommand: String? = null

    lines.forEach { line ->
        if (line.startsWith("$ ")) {
            val parts = line.split(" ")
            when (parts[1]) {
                "cd" -> {
                    val name = parts[2]
                    when (name) {
                        "/" -> current = mutableListOf(root)
                        ".." -> current.removeLast()
                        else -> {
                            val directory = current.last().directories[name]
                            current.add(directory!!)
                        }
                    }
                }
                "ls" -> currentCommand = "ls"
            }
        } else {
            when (currentCommand) {
                "ls" -> {
                    val parts = line.split(" ")
                    val filename = parts.subList(1, parts.size).joinToString(" ")
                    val directory = current.last()
                    if (parts[0] == "dir") {
                        val newDirectory = Directory(mutableMapOf(), mutableMapOf())
                        directory.directories[filename] = newDirectory
                    } else {
                        val size = parts[0].toInt()
                        val file = File(filename, size)
                        directory.files[filename] = file
                    }
                }
                else -> throw Exception("Unknown command: $currentCommand")
            }
        }
    }

    val unusedSpace = 70000000 - root.size()
    println(root.minSize(30000000 - unusedSpace))
}
