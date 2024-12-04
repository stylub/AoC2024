package solutions

import utils.println
import utils.readInput
import kotlin.math.abs

fun main() {
    fun parseInput(input: String): MutableList<Pair<Int, String>> {
        val regex = """mul\(\d+,\d+\)""".toRegex()
        return regex.findAll(input).map { Pair(it.range.first,"mul") }.toMutableList()
    }

    fun part1(input:List<String>): Int {
        var text = ""
        input.forEach{line ->
            text += line
        }
        val found = parseInput(text)

        var solution = 0
        for (pair in found) {
            val substring = text.substring(pair.first)
            val match = """mul\((\d+),(\d+)\)""".toRegex().find(substring)
            if (match != null) {
                val (num1, num2) = match.destructured
                solution += num1.toInt() * num2.toInt()
            }
        }
        return solution;
    }

    fun part2(input: List<String>): Int {
        var text = ""
        input.forEach{line ->
            text += line
        }

        val found = parseInput(text)

        found.addAll("""do\(\)""".toRegex().findAll(text).map { Pair(it.range.first,"do") }.toList())
        found.addAll("""don't\(\)""".toRegex().findAll(text).map { Pair(it.range.first,"dont") }.toList())

        val sortedList = found.sortedBy{ it.first }
        var solution = 0

        var enabled = true

        for (pair in sortedList) {
            val index = pair.first
            val type = pair.second
            if(type == "dont"){
                enabled = false
            }
            else if(type == "do"){
                enabled = true
            }else if(enabled){

                val substring = text.substring(index)
                val match = """mul\((\d+),(\d+)\)""".toRegex().find(substring)
                if (match != null) {
                    val (num1, num2) = match.destructured
                    solution += num1.toInt() * num2.toInt()
                }
            }
        }

        return solution
    }

    val testInput = readInput("Day03_test")
    check(part1(testInput) == 161)
    check(part2(testInput) == 48)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}