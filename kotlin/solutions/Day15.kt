package solutions

import utils.println
import utils.readInput
import java.io.Serializable

fun main() {
    fun parseInputPart1(input: List<String>): Triple<MutableList<MutableList<Char>>, String, Pair<Int, Int>> {
        val grid = mutableListOf<MutableList<Char>>()
        var i = 0
        var atPosition = 0 to 0

        while (input[i].isNotEmpty()) {
            val row = input[i].toMutableList()
            val atIndex = row.indexOf('@')
            if (atIndex != -1) {
                atPosition = i to atIndex
            }
            grid.add(row)
            i += 1
        }

        val moves = input.dropWhile { it.isNotEmpty() }.drop(1).joinToString("")
        return Triple(grid, moves, atPosition)
    }

    fun parseInputPart2(input: List<String>): Triple<MutableList<MutableList<Char>>, String, Pair<Int, Int>> {
        val grid = mutableListOf<MutableList<Char>>()
        var i = 0
        var atPosition = 0 to 0

        while (input[i].isNotEmpty()) {
            val row = mutableListOf<Char>()
            input[i].forEachIndexed { index, c ->
                when(c){
                    '#' ->{ row.add('#'); row.add('#')}
                    '.' ->{ row.add('.'); row.add('.')}
                    'O' ->{ row.add('['); row.add(']')}
                    '@' ->{ row.add('@'); row.add('.'); atPosition = i to index*2}
                }
            }
            grid.add(row)
            i += 1
        }

        val moves = input.dropWhile { it.isNotEmpty() }.drop(1).joinToString("")
        return Triple(grid, moves, atPosition)
    }

    val dir = mapOf('^' to (-1 to 0), 'v' to (1 to 0), '>' to (0 to 1), '<' to (0 to -1))

    fun part1(input: List<String>): Long {
         val (grid,moves,start) = parseInputPart1(input)

        fun move(pos : Pair<Int,Int>,d : Char) : Boolean{
            val (dX,dY) = dir[d] ?: return false
            val (x,y) = pos

            if(grid[x][y] == '#') return false
            if(grid[x][y] == '.') return true

            return if(move(x+dX to y+dY,d)){
                grid[x+dX][y+dY] = grid[x][y]
                grid[x][y] = '.'
                true
            } else false
        }

        var (x,y) = start

        moves.forEach { d ->
            if(move(x to y,d)){
                val (dX,dY) = dir[d] ?: (0 to 0)
                x += dX
                y +=dY
            }
//            grid.forEach { line ->
//                println(line)
//            }
        }
        var sum = 0L
        grid.forEachIndexed{i, line ->
            line.forEachIndexed{j, c ->
                if(c =='O') sum += i * 100 + j
            }
        }

        return sum
    }

    fun part2(input: List<String>): Long {
        val (grid,moves,start) = parseInputPart2(input)



        fun canMove(pos : Pair<Int,Int>,d : Char) : MutableList<Pair<Int,Int>>? {
            val (dX,dY) = dir[d] ?: return null
            val (x,y) = pos

            if(grid[x][y] == '#') return null
            if(grid[x][y] == '.') return mutableListOf()


            if(grid[x][y] == '[' && (d =='^' || d == 'v')){
                val toMoveLeft = canMove(x + dX to y + dY,d) ?: return null
                toMoveLeft.add(x to y)
                val toMoveRight = canMove(x + dX to y + 1 + dY,d) ?: return null
                toMoveRight.add(x to y +1)
                toMoveLeft.addAll(toMoveRight)
                return toMoveLeft
            }
            else if(grid[x][y] == ']' && (d =='^' || d == 'v')){
                val toMoveLeft = canMove(x + dX to y - 1 + dY,d) ?: return null
                toMoveLeft.add(x to y -1)
                val toMoveRight = canMove(x + dX to y + dY,d) ?: return null
                toMoveLeft.add(x to y)
                toMoveLeft.addAll(toMoveRight)
                return toMoveLeft
            }else {
                val toMove = canMove(x + dX to y + dY, d) ?: return null
                return toMove.apply { add(x to y) }
            }
        }

        fun move(pos: Pair<Int, Int>, d: Char): Boolean {
            val (dX, dY) = dir[d] ?: return false
            val (x, y) = pos
            val toMove = canMove(x + dX to y + dY, d)?.distinct() ?: return false
            val newPositions = mutableMapOf<Pair<Int,Int>,Char>()
            toMove.forEach { p ->
                newPositions[p.first + dX to p.second + dY] = grid[p.first][p.second]
            }
            for(key in newPositions.keys){
                val c = newPositions[key] ?: '!'
                grid[key.first][key.second] = c
            }
            for(p in toMove){
                if(!newPositions.containsKey(p))
                    grid[p.first][p.second] = '.'
            }

            return true
        }

        var (x,y) = start

        moves.forEach { d ->
            val (dX,dY) = dir[d] ?: (0 to 0)

            if (move(x to y,d)){
                grid[x][y] = '.'
                x += dX
                y +=dY
                grid[x][y] = '@'
            }
//            grid.forEach { line ->
//                println(line)
//            }
        }
        var sum = 0L
        grid.forEachIndexed{i, line ->
            line.forEachIndexed{j, c ->
                if(c =='[') sum += i * 100 + j
            }
        }

        return sum
    }

    val testInput = readInput("Day15_test")
    check(part1(testInput) == 10092L)
    check(part2(testInput) == 9021L)

    val input = readInput("Day15")
    part1(input).println()
    part2(input).println()
}