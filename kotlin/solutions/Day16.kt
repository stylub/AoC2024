package solutions

import utils.println
import utils.readInput
import java.util.*
import kotlin.math.min


fun main() {
    val steps = listOf(-1 to 0,0 to 1, 1 to 0, 0 to -1, )
    var start = 0 to 0
    var end = 0 to 0

    fun parseInput(input: List<String>){
        start = input.size - 2 to 1
        end = 1 to input[0].length - 2
    }
    

    fun part1(input: List<String>): Int {
        parseInput(input)
        
        fun findShortestPath(): Int {
            val distances = mutableMapOf<Pair<Pair<Int, Int>, Int>, Int>().withDefault { Int.MAX_VALUE }
            val visited = mutableSetOf<Pair<Int, Int>>()

            var minPath = Int.MAX_VALUE

            distances[start to 1] = 0

            fun dfs(pos: Pair<Int, Int>, dist: Int, stepBefore: Int) {
                if (pos == end) {
                    minPath = min(minPath, dist)
                    return
                }
                if(dist > minPath) return
                if(distances.containsKey(pos to stepBefore) && distances[pos to stepBefore]!! < dist) return
                distances[pos to stepBefore] = dist

                val (x,y) = pos

                listOf(0,1,3).forEach { nextStep ->
                    val stepNum = (stepBefore + nextStep) % 4
                    val adjacent = x + steps[stepNum].first to  y + steps[stepNum].second
                    if(input[adjacent.first][adjacent.second] != '#' && !visited.contains(adjacent)) {
                        visited.add(adjacent)
                        val weight = if (stepNum == stepBefore) 1 else 1001
                        val totalDist = dist + weight
                        dfs(adjacent, totalDist, stepNum)
                    }
                    visited.remove(adjacent)
                }

            }

            dfs(start, 0, 1)

            return minPath
        }
        return findShortestPath()
    }

    fun part2(input: List<String>): Int {
        val bestScore =  part1(input)


        fun findShortestPath(): Int {
            val distances = mutableMapOf<Pair<Pair<Int, Int>, Int>, Int>().withDefault { Int.MAX_VALUE }
            val bestTiles = mutableSetOf<Pair<Int,Int>>()

            distances[start to 1] = 0

            fun dfs(pos: Pair<Int, Int>, dist: Int, stepBefore: Int,path : MutableSet<Pair<Int,Int>>) {
                if (pos == end) {
                    if(bestScore == dist)
                        bestTiles.addAll(path)
                    return
                }
                if (dist > bestScore) return

                if(distances.containsKey(pos to stepBefore) && distances[pos to stepBefore]!! < dist) return

                distances[pos to stepBefore] = dist

                val (x, y) = pos

                listOf(0, 1, 3).forEach { nextStep ->
                    val stepNum = (stepBefore + nextStep) % 4
                    val adjacent = x + steps[stepNum].first to y + steps[stepNum].second

                    if (input[adjacent.first][adjacent.second] != '#' && !path.contains(adjacent)) {
                        path.add(adjacent)
                        val weight = if (stepNum == stepBefore) 1 else 1001
                        val totalDist = dist + weight

                        dfs(adjacent, totalDist, stepNum, path)
                        path.remove(adjacent)
                    }
                }
            }

            dfs(start, 0, 1, mutableSetOf(start))
            return bestTiles.size
        }

        return findShortestPath()
    }

    val test1Input = readInput("Day16_test1")
    check(part1(test1Input) == 7036)
    check(part2(test1Input) == 45)

    val test2Input = readInput("Day16_test2")
    check(part1(test2Input) == 11048)
    check(part2(test2Input) == 64)

    val input = readInput("Day16")
    part1(input).println()
    part2(input).println()
}