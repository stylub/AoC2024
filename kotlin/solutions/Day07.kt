package solutions

import utils.println
import utils.readInput
import kotlin.math.pow

fun main() {
    fun parseInput(input: List<String>): List<Pair<Long, List<Long>>> {
        return input.map { line ->
            val parts = line.split(":")
            val key = parts[0].trim().toLong()
            val values = parts[1].trim().split(" ").map { it.toLong() }
            key to values
        }
    }

    fun checkAllCombinationsPart1(equation : Pair<Long, List<Long>>): Long {
        val (equals, numbers) = equation
        val n = numbers.size - 1
        val totalNumbers = 1 shl n

        for (i in 0..<totalNumbers) {
            val binaryString = i.toString(2).padStart(n, '0')
            var candidate = numbers[0]
            for(j in 1..<numbers.size){
                when(binaryString[j - 1]){
                    '0' -> candidate += numbers[j]
                    '1' -> candidate *= numbers[j]
                }
            }
            if(candidate == equals) return equals
        }
        return 0
    }

    fun checkAllCombinationsPart2(equation : Pair<Long, List<Long>>): Long {
        val (equals, numbers) = equation
        val n = numbers.size - 1
        val totalNumbers = 3.0.pow(n).toLong()

        for (i in 0..<totalNumbers) {
            val binaryString = i.toString(3).padStart(n, '0')
            var candidate = numbers[0]
            for(j in 1..<numbers.size){
                when(binaryString[j - 1]){
                    '0' -> candidate += numbers[j]
                    '1' -> candidate *= numbers[j]
                    '2' -> candidate = "$candidate${numbers[j]}".toLong()
                }
            }
            if(candidate == equals) return equals
        }
        return 0
    }

    fun part1(input: List<String>): Long {
        val parsedData = parseInput(input)
        var solution = 0L
        parsedData.forEach{line ->
            solution += checkAllCombinationsPart1(line)
        }

        return solution
    }

    fun part2(input: List<String>): Long {
        val parsedData = parseInput(input)
        var solution = 0L
        parsedData.forEach{line ->
            solution += checkAllCombinationsPart2(line)
        }

        return solution
    }

    val testInput = readInput("Day07_test")
    check(part1(testInput) == 3749L)
    check(part2(testInput) == 11387L)

    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}