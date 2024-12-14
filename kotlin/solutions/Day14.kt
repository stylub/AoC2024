package solutions

import utils.println
import utils.readInput
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import kotlin.math.abs

fun main() {
    fun parseInput(input: List<String>): List<Pair<Pair<Int, Int>, Pair<Int, Int>>> {
        val regex = """p=(\d+),(\d+) v=(-?\d+),(-?\d+)""".toRegex()
        return input.mapNotNull { line ->
            regex.matchEntire(line)?.destructured?.let { (px, py, vx, vy) ->
                (py.toInt() to px.toInt()) to (vy.toInt() to vx.toInt())
            }
        }
    }
    fun robotAfterSteps(pos : Pair<Int, Int>, vel : Pair<Int, Int>, boundX :Pair<Int, Int>, boundY : Pair<Int, Int>, n : Int): Pair<Int, Int> {
        var (currX, currY) = pos
        for(i in 0..<n){
            var newX = currX + vel.first
            var newY = currY + vel.second

            if(newX < boundX.first) newX = boundX.second - abs(boundX.first - newX)
            else if(newX >= boundX.second) newX = boundX.first + abs(boundX.second - newX)

            if(newY < boundY.first) newY = boundY.second - abs(boundY.first - newY)
            else if(newY >= boundY.second) newY = boundY.first + abs(boundY.second - newY)

            currX = newX
            currY = newY
        }

        return currX to currY
    }
    fun generateImageFromMatrix(matrix: MutableList<MutableList<Int>>, outputPath: String) {
        val height = matrix.size
        val width = matrix[0].size
        val image = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)

        for (y in matrix.indices) {
            for (x in matrix[y].indices) {
                val value = matrix[y][x].coerceIn(0, 255)
                val color = Color(value, value, value) // Grayscale
                image.setRGB(x, y, color.rgb)
            }
        }

        ImageIO.write(image, "png", File(outputPath))
    }

    fun part1(input: List<String>): Int {
        val quadrants = mutableListOf(0,0,0,0)
        val robots = parseInput(input)
        val wide = 101
        val tall = 103

        robots.forEach { robot ->
            val (posX,posY) = robotAfterSteps(robot.first, robot.second,0 to tall, 0 to wide,100)
            if(posX < tall/2 && posY < wide/2) quadrants[0] +=1
            else if(posX > tall/2 && posY < wide/2) quadrants[1] +=1
            else if(posX < tall/2 && posY > wide/2) quadrants[2] +=1
            else if(posX > tall/2 && posY > wide/2) quadrants[3] +=1
        }
        return quadrants[0] * quadrants[1] * quadrants[2] * quadrants[3]
    }

    fun part2(input: List<String>) {
        var robots = parseInput(input)
        val wide = 101
        val tall = 103

        for(i in 0..<10000) {
            val picture = MutableList(tall) { MutableList(wide) { 0 } }
            val newRobotsState = mutableListOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()
            robots.forEach { robot ->
                val (posX, posY) = robotAfterSteps(robot.first, robot.second, 0 to tall, 0 to wide, 1)
                newRobotsState.add((posX to posY) to robot.second)
                picture[posX][posY] = 255
            }
            robots = newRobotsState.map { (pos, vel) -> (pos.first to pos.second) to (vel.first to vel.second) }.toMutableList()
            generateImageFromMatrix(picture,"trees/image${i}.png")
        }
    }

    val testInput = readInput("Day14_test")
//    check(part1(testInput) == 12)
//    check(part2(testInput) == 1)

    val input = readInput("Day14")
    part1(input).println()
    part2(input).println()
}