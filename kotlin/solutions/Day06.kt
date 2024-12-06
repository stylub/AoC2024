package solutions

import utils.println
import utils.readInput
import kotlin.math.abs

fun main() {
    fun parseInput(input: List<String>): Pair<Pair<Int, Int>, MutableList<MutableList<Int>>> {
        val room  = mutableListOf<MutableList<Int>>()
        var start = 0 to 0
        for(line in input.indices){
            val currLine = mutableListOf<Int>()
            for(row in input[line].indices){
                when(input[line][row]){
                    '#' -> currLine.add(-1)
                    '.' -> currLine.add(0)
                    '^' -> {
                        currLine.add(1)
                        start = line to row
                    }
                }
            }
            room.add(currLine)
        }
        return start to room
    }

    fun part1(input: List<String>): Int {
        var (pos,room) = parseInput(input)
        var (x,y) = pos
        var i = 0

        var solution = 0

        val steps: List<Pair<Int, Int>> = listOf(
            0 to -1,
            1 to 0,
            0 to 1,
            -1 to 0
        )

        val m = input.size
        val n = input[0].length
        var (dX,dY) = steps[i]

        while(x + dX in 0..<n && y + dY in 0..<m ){
            dX = steps[i].first
            dY = steps[i].second
            when(room[x + dX][y + dY]){
                1 ->{
                    x += dX
                    y += dY
                }
                0 ->{
                    x += dX
                    y += dY
                    room[x][y] = 1
                    solution +=1
                }
                -1 ->{
                    i+=1
                    i %= 4
                }
            }
        }

        return solution;
    }

    fun part2(input: List<String>): Int {
        return 1
    }

    val testInput = readInput("Day06_test")
    check(part1(testInput) == 41)
    check(part2(testInput) == 1)

//    val input = readInput("Day06")
//    part1(input).println()
//    part2(input).println()
}