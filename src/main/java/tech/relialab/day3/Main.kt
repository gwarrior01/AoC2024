package tech.relialab.day3

import tech.relialab.day1.readInput

private const val mulRegex = """mul\((\d{1,3}),(\d{1,3})\)"""
private const val doRegex = """do\(\)"""
private const val dontRegex = """don`t\(\)"""
private const val mulRegexLabels = """mul\((?<first>\d{1,3}),(?<second>\d{1,3})\)"""

fun main() {
    val input = readInput("day3/input")
    val sum = input.sumOf { section ->
        mulRegex.toRegex().findAll(section).sumOf {
            val (first, second) = it.destructured
            first.toLong() * second.toLong()
        }
    }
    val sumLabels = input.sumOf { section ->
        mulRegexLabels.toRegex().findAll(section).sumOf { match ->
            val first = match.groups["first"]?.value?.toLong() ?: 0
            val second = match.groups["second"]?.value?.toLong() ?: 0
            first * second
        }
    }
    println("[1] mul = $sum")
    println("[1] mulLabels = $sumLabels")

    var sum2 = 0L
    var enabled = true
    input.forEach {
        """$mulRegex|$doRegex|$dontRegex""".toRegex().findAll(it).forEach { match ->
            when (match.value) {
                "don`t()" -> enabled = false
                "do()" -> enabled = true
                else -> if (enabled) sum2 += match.multiple()
            }
        }
    }

    println("[2] mul = $sum2")
}

private fun MatchResult.multiple(): Long {
    val (first, second) = destructured
    return first.toLong() * second.toLong()
}