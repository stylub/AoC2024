package solutions

import utils.println
import utils.readInput

fun main() {
    fun parseInput(input: List<String>): MutableList<Long> {
        return input[0].split("\\s+".toRegex()).map(String::toLong).toMutableList()
    }

    fun blinkAtStones(stones : MutableList<Long>, numOfBlinks : Int): Long{
        var stonesNow = stones.groupingBy { it }.eachCount().mapValues { it.value.toLong() }.toMutableMap()
        for (j in 0..<numOfBlinks) {
            val stonesUpdated = mutableMapOf<Long, Long>()
            for ((key, count) in stonesNow) {
                if (key == 0L) {
                    stonesUpdated[1] = stonesUpdated.getOrDefault(1, 0) + count
                } else {
                    val currStr = key.toString()
                    if (currStr.length % 2 == 0) {
                        val left = currStr.substring(0, currStr.length / 2).toLong()
                        val right = currStr.substring(currStr.length / 2).toLong()
                        stonesUpdated[left] = stonesUpdated.getOrDefault(left, 0) + count
                        stonesUpdated[right] = stonesUpdated.getOrDefault(right, 0) + count
                    } else {
                        val newVal = key * 2024
                        stonesUpdated[newVal] = stonesUpdated.getOrDefault(newVal, 0) + count
                    }
                }
            }
            stonesNow = stonesUpdated
        }
        var sum = 0L
        for((key, count) in stonesNow){
            sum+= count
        }
        return sum
    }

    fun part1(input: List<String>): Long {
        val stones = parseInput(input)
        return blinkAtStones(stones,25)
    }

    fun part2(input: List<String>): Long {
        var stones = parseInput(input)
        return blinkAtStones(stones,75)
    }

    val testInput = readInput("Day11_test")
    check(part1(testInput) == 55312L)
//    check(part2(testInput) == 1)

    val input = readInput("Day11")
    part1(input).println()
    part2(input).println()
}