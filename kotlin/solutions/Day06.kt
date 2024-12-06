package solutions

import utils.println
import utils.readInput

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
                        currLine.add(0)
                        start = line to row
                    }
                }
            }
            room.add(currLine)
        }
        return start to room
    }

    fun simulate(pos :Pair<Int, Int>, room: MutableList<MutableList<Int>> ): Pair<Int, MutableList<MutableList<Int>>>{
        val steps: List<Pair<Int, Int>> = listOf(
            0 to -1,
            1 to 0,
            0 to 1,
            -1 to 0
        )

        val positionDirection = HashMap<Pair<Int, Int>,MutableSet<Int>>()

        var (y,x) = pos
        var i = 0

        var solution = 0
        val m = room.size
        val n = room[0].size
        var (dX,dY) = steps[i]

        while(x + dX in 0..<n && y + dY in 0..<m ){
            while(room[y + dY][x + dX] == -1){
                i = (i + 1)%4
                dX = steps[i].first
                dY = steps[i].second
            }
            if(room[y][x] == 0){
                room[y][x] = 1
                solution +=1
            }else if(positionDirection.containsKey(y to x)
                && positionDirection[y to x]?.contains(i) == true){
                return -1 to room
            }
            if(positionDirection.containsKey(y to x)){
                positionDirection[y to x]?.add(i)
            }else{
                positionDirection[y to x] = mutableSetOf(i)
            }


            x += dX
            y += dY
        }
        room[y][x] = 1
        return solution to room
    }

    fun part1(input: List<String>): Int {
        val (pos,room) = parseInput(input)
        val (solution, roomAfter) = simulate(pos,room)
        return solution + 1
    }

    fun part2(input: List<String>): Int {
        val (pos,room) = parseInput(input)
        val org = room.map { it.toMutableList() }.toMutableList()
        var loops = 0
        for (i in org.indices) {
            for (j in org[i].indices) {
                if (org[i][j] == 0 && i to j != pos ) {
                    val modifiedRoom = org.map { it.toMutableList() }.toMutableList()
                    modifiedRoom[i][j] = -1
                    if(simulate(pos,modifiedRoom).first == -1){
                        loops +=1
                    }
                }
            }
        }

        return loops
    }

    val testInput = readInput("Day06_test")
    check(part1(testInput) == 41)
    check(part2(testInput) == 6)

    val input = readInput("Day06")
    part1(input).println()
    part2(input).println()
}
