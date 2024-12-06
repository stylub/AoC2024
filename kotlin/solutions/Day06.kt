package solutions

import utils.println
import utils.readInput
import kotlin.math.abs

fun main() {
    fun parseInput(input: List<String>): Pair<List<Int>, List<Int>> {
        val room = mutableListOf<List<Int>>()
        var start = 0 to 0
        for(line in input.indices){
            val curr_line = mutableListOf<Int>()
            for(row in input[line].indices){
                when(input[line][row]){
                    '#' -> curr_line.addLast(-1)
                    '.' -> curr_line.addLast(0)
                    '^' -> {
                        curr_line.addLast(1)
                        start = line to row
                    }

                }
            }
        }
    }

    fun part1(input: List<String>): Int {
        return 1;
    }

    fun part2(input: List<String>): Int {
        return 1
    }

    val testInput = readInput("Day06_test")
    check(part1(testInput) == 1)
    check(part2(testInput) == 1)

//    val input = readInput("Day06")
//    part1(input).println()
//    part2(input).println()
}