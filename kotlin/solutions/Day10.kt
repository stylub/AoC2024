package solutions


import utils.println
import utils.readInput

fun main() {
    fun parseInput(input: List<String>): Pair<MutableMap<Pair<Int, Int>, MutableList<Pair<Int, Int>>>, MutableList<MutableList<Int>>> {
        val map = mutableListOf<MutableList<Int>>()
        input.forEach{line ->
            val lineInt = mutableListOf<Int>()
            line.forEach{value ->
               lineInt.add(value.code - '0'.code)
            }
            map.add(lineInt)
        }
        val graph = mutableMapOf<Pair<Int,Int>,MutableList<Pair<Int,Int>>>()
        map.forEachIndexed{i, line ->
            line.forEachIndexed{j,value ->
                graph[i to j] = mutableListOf()
                if(i > 0 && map[i-1][j] == value + 1){
                    graph[i to j]?.add(i-1 to j)
                }
                if(i + 1 < map[0].size && map[i+1][j] == value + 1){
                    graph[i to j]?.add(i+1 to j)
                }
                if(j > 0 && map[i][j - 1] == value + 1){
                    graph[i to j]?.add(i to j - 1)
                }
                if(j  + 1 < map.size && map[i][j + 1] == value + 1){
                    graph[i to j]?.add(i to j + 1)
                }
            }
        }

        return graph to map
    }

    fun findAllSummits(graph: MutableMap<Pair<Int, Int>, MutableList<Pair<Int, Int>>>,
                     map: MutableList<MutableList<Int>>,
                     visited: MutableList<MutableList<Boolean>>,
                     summits : MutableSet<Pair<Int, Int>>,
                     pos: Pair<Int, Int>){
        visited[pos.first][pos.second] = true

        val neighbors = graph[pos] ?: mutableListOf()
        var hasUnvisitedNeighbors = false

        for (neighbor in neighbors) {
            if (!visited[neighbor.first][neighbor.second]) {
                hasUnvisitedNeighbors = true
                findAllSummits(graph, map, visited,summits, neighbor)
            }
        }

        if (!hasUnvisitedNeighbors && map[pos.first][pos.second] == 9) {
            summits.add(pos.first to pos.second)
        }

        visited[pos.first][pos.second] = false
    }
    fun findAllPaths(graph: MutableMap<Pair<Int, Int>, MutableList<Pair<Int, Int>>>,
                     map: MutableList<MutableList<Int>>,
                     start: Pair<Int, Int>): Int {
        val visited = mutableSetOf<Pair<Int, Int>>()

        var countPaths = 0

        fun dfs(current: Pair<Int, Int>) {
            if (map[current.first][current.second] == 9) {
                countPaths +=1
                return
            }

            visited.add(current)

            for (neighbor in graph[current] ?: mutableListOf()) {
                if (!visited.contains(neighbor)) {
                    dfs(neighbor)
                }
            }

            visited.remove(current)
        }

        dfs(start)

        return  countPaths
    }

    fun part1(input: List<String>): Int {
        val (graph,map) = parseInput(input)
        val visited = MutableList(input.size) { MutableList(input[0].length) { false } }

        var sum = 0
        for((i, line) in map.withIndex()){
            for((j, value ) in line.withIndex()){
                if(value == 0){
                    val summits = mutableSetOf<Pair<Int,Int>>()
                    findAllSummits(graph, map,visited, summits, i to j)
                    sum += summits.count()
                }
            }
        }

        return sum
    }

    fun part2(input: List<String>): Int {
        val (graph,map) = parseInput(input)
        val visited = MutableList(input.size) { MutableList(input[0].length) { false } }

        var sum = 0
        for((i, line) in map.withIndex()){
            for((j, value ) in line.withIndex()){
                if(value == 0){
                    sum += findAllPaths(graph, map, i to j)
                }
            }
        }

        return sum
    }

    val testInput = readInput("Day10_test")
    check(part1(testInput) == 36)
    check(part2(testInput) == 81)

    val input = readInput("Day10")
    part1(input).println()
    part2(input).println()
}