package solutions

import utils.println
import utils.readInput

fun main() {
    fun parseInput(input: List<String>): MutableList<String> {
        val map = mutableListOf<String>()
        input.forEach { line -> map.add(line) }
        return map
    }

    fun part1(input: List<String>): Int {
        val processed = mutableMapOf<Pair<Int,Int>,Int>()
        val regions = mutableMapOf<Int,Int>()


        return 1
    }

    fun part2(input: List<String>): Int {
        return 1
    }

    val testInput = readInput("Day12_test")
    check(part1(testInput) == 1)
    check(part2(testInput) == 1)

    val input = readInput("Day12")
    part1(input).println()
    part2(input).println()
}