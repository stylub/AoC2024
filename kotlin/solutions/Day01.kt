import utils.println
import utils.readInput
import kotlin.math.abs

fun main() {
    fun parseInput(input: List<String>): Pair<List<Int>, List<Int>> {
        return  input.map { line ->
            line.split("\\s+".toRegex()).map(String::toInt).let { (a, b) -> a to b }
        }.unzip()
    }

    fun part1(input: List<String>): Int {
        val (firstList, secondList) = parseInput(input)
        val sortedFirstList = firstList.sorted()
        val sortedSecondList = secondList.sorted()

        var sum = 0;
        sortedFirstList.zip(sortedSecondList).forEach { (first, second) ->
            sum += abs(first - second)
        }

        return sum;
    }

    fun part2(input: List<String>): Int {
        val (firstList, secondList) = parseInput(input)
        val occurrences = secondList.groupingBy { it }.eachCount()

        var soultion = 0;

        firstList.forEach { number ->

            soultion += number * occurrences.getOrDefault(number,0)
        }

        return soultion
    }

    val testInput = readInput("Day01_test")
    check(part1(testInput) == 11)
    check(part2(testInput) == 31)

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}