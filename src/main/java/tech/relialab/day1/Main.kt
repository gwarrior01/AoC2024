package tech.relialab.day1

import kotlin.io.path.Path
import kotlin.io.path.readLines
import kotlin.math.abs

fun readInput(filename: String) = Path("/Users/great_warrior/IdeaProjects/AoC2024/src/main/resources/$filename").readLines()

fun main() {
    val lines = readInput("day1/input")
    val (left, right) = lines.map { line ->
        val first = line.substringBefore(" ").toLong()
        val second = line.substringAfterLast(" ").toLong()
        first to second
    }.unzip()

    left.sorted().zip(right.sorted()).sumOf { (first, second) ->
        abs(first - second)
    }.also { println("[1] $it") }

    val frequencies = right.groupingBy { it }.eachCount()
    left.sumOf { num ->
        num * frequencies.getOrDefault(num, 0)
    }.also { println("[2] $it") }
}