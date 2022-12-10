import java.io.InputStreamReader
import java.lang.System
import kotlin.math.min

data class File(
    val name: String,
    val size: Int
)

data class Directory(
    val directories: MutableMap<String, Directory> = mutableMapOf(),
    val files: MutableMap<String, File> = mutableMapOf()
) {
    fun size(): Int {
        return files.values.sumOf { it.size } + directories.values.sumOf { it.size() }
    }

    fun sumOfSizes(upperBound: Int): Int {
        val childSizes = directories.values.map { it.sumOfSizes(upperBound) }
        val size = size()
        return childSizes.sum() + if (size > upperBound) 0 else size
    }

    fun minSize(lowerBound: Int): Int? {
        val size = size()
        val minChildSize = directories.values.mapNotNull { it.minSize(lowerBound) }.minOfOrNull { it }
        
        if (size < lowerBound) {
            return minChildSize
        } else if (minChildSize == null) {
            return size
        } else {
            return min(size, minChildSize)
        }
    }
}

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

    println(root.sumOfSizes(100000))
}