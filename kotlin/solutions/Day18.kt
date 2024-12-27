package solutions

import utils.println
import utils.readInput
import java.util.*

fun main() {
    fun parseInput(input: List<String>): List<Pair<Int, Int>> {
        return input
            .filter { it.isNotBlank() }
            .map { line ->
                val (x, y) = line.split(",").map { it.trim().toInt() }
                x to y
            }
    }
    var dangerousBytes = emptyList<Pair<Int,Int>>()

    fun part1(side : Int,firstBytes : Int): Int {
        val map = List(side) { MutableList(side) {'.'} }
        for(i in 0..<firstBytes){
            val (x,y) = dangerousBytes[i]
            map[x][y] = '#'
        }

        fun getNeighbours(now : Pair<Int,Int>): MutableList<Pair<Int, Int>> {
            val neighbours = mutableListOf<Pair<Int,Int>>()
            for(step in listOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1)){
                val (nX, nY) = step.first + now.first to step.second + now.second
                if(nX in 0..<side && nY in 0..<side && map[nX][nY] == '.')
                    neighbours.add(nX to nY)
            }
            return neighbours
        }

        fun dijkstra(): Int {
            val distances = mutableMapOf<Pair<Int,Int>,Int>().withDefault { Int.MAX_VALUE }
            val priorityQueue = PriorityQueue(compareBy<Pair<Int,Pair<Int, Int>>> { it.first })

            distances[0 to 0] = 0
            priorityQueue.add(0 to (0 to 0))

            while (priorityQueue.isNotEmpty()) {
                val (currentDistance, currentNode) = priorityQueue.poll()

                if (currentDistance > distances.getValue(currentNode)) continue

                for (neighbour in getNeighbours(currentNode)) {
                    val newDistance = currentDistance + 1
                    if (newDistance < distances.getValue(neighbour)) {
                        distances[neighbour] = newDistance
                        priorityQueue.add(newDistance to neighbour)
                    }
                }
            }

            return distances.getValue(side - 1 to side - 1)
        }

        return dijkstra()
    }

    fun part2(side : Int,firstBytes : Int): Pair<Int, Int> {
        var delta = 1
        while(part1(side,firstBytes + delta) != Int.MAX_VALUE){
            delta +=1
        }
        return dangerousBytes[firstBytes + delta - 1]
    }

    val testInput = readInput("Day18_test")
    dangerousBytes = parseInput(testInput)
    check(part1(7,12) == 22)
    check(part2(7,12) == 6 to 1)


    val input = readInput("Day18")
    dangerousBytes = parseInput(input)
    part1(71,1024).println()
    part2(71,1024).println()
}