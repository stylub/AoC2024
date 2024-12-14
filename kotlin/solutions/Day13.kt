package solutions

import utils.println
import utils.readInput
import java.util.*

fun main() {
    fun parseInput(input: List<String>): MutableList<List<Pair<Long, Long>>> {
        val paredOutput = mutableListOf<List<Pair<Long, Long>>>()
        val regex = """Button A: X\+(\d+), Y\+(\d+)\s*Button B: X\+(\d+), Y\+(\d+)\s*Prize: X=(\d+), Y=(\d+)""".toRegex()
        var line = 0
        while (line < input.size) {
            val str = input[line] + input[line + 1] + input[line + 2]
            regex.matchEntire(str)?.destructured?.let { (ax, ay, bx, by, px, py) ->
                paredOutput.add(
                    listOf(
                    (ax.toLong() to ay.toLong()),
                    (bx.toLong() to by.toLong()),
                    (px.toLong() to py.toLong())
                    )
                )
            }
            line += 4
        }
        return paredOutput
    }

    fun calculateMinCost(machine : List<Pair<Long, Long>>) : Int{
        var won = false
        var minCost = 100_000_000
        val aButton = machine[0]
        val bButton = machine[1]
        val price = machine[2]
        for(aTimes in 0..100){
            for(bTimes in 0..100){
                val x = aTimes * aButton.first + bTimes * bButton.first
                val y = aTimes * aButton.second + bTimes * bButton.second
                if(x == price.first && y == price.second){
                    minCost = minCost.coerceAtMost(aTimes * 3 + bTimes)
                    won = true
                }
            }
        }
        return if(won) minCost else 0
    }

    fun findAB(x : Long,y : Long,z : Long,w : Long,p1 : Long,p2 : Long): Long {
        val denominator =  y * z - x * w
        val numB = y * p1 - x * p2

        if(denominator == 0L || numB % denominator != 0L){
            return 0L
        }

        val B = numB / denominator
        val numA = p1 - z * B

        val A = numA / x
        return A * 3 + B
    }



    fun part1(input: List<String>): Long {
        val machines = parseInput(input)
        var sum = 0L
        machines.forEach{machine ->
            sum += calculateMinCost(machine)
        }
        return sum
    }

    fun part2(input: List<String>): Long {
        val machines = parseInput(input)
        var sum = 0L
        machines.forEach{machine ->
            val aButton = machine[0]
            val bButton = machine[1]
            val price = machine[2].first  + 10000000000000 to machine[2].second + 10000000000000
            sum += findAB(aButton.first,aButton.second,bButton.first,bButton.second,price.first,price.second)
        }
        return sum
    }

    val testInput = readInput("Day13_test")
    check(part1(testInput) == 480L)
//    check(part2(testInput) == 480L)

    val input = readInput("Day13")
    part1(input).println()
    part2(input).println()
}