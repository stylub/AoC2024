package solutions

import utils.println
import utils.readInput

fun main() {
    fun parseInput(input: List<String>) {
    }

    fun part1(input: List<String>): Int {
        return 1
    }

    fun part2(input: List<String>): Int {
        return 1
    }

    val testInput = readInput("Day??_test")
    check(part1(testInput) == 1)
    check(part2(testInput) == 1)

    val input = readInput("Day??")
    part1(input).println()
    part2(input).println()
}