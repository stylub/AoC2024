package solutions

import utils.println
import utils.readInput

fun main() {
    fun parseInput(input: List<String>): MutableMap<Char, MutableList<Pair<Int, Int>>> {
        val antennas =  mutableMapOf<Char, MutableList<Pair<Int,Int>>>()

        for(i in input.indices){
            for(j in input.indices){
                if(input[i][j] != '.'){
                    if(antennas.containsKey(input[i][j])){
                        antennas[input[i][j]]?.add(j to i)
                    }
                    else{
                        antennas[input[i][j]] = mutableListOf(j to i)
                    }
                }
            }
        }

        return antennas
    }

    fun part1(input: List<String>): Int {
        var antinodes = mutableSetOf<Pair<Int,Int>>()
        var antennas = parseInput(input)

        val width = input[0].length
        val height = input.size

        for ((k, locations) in antennas) {
            locations.forEachIndexed { i, _ ->
                for(j in i+1..<locations.size){
                    var dY = locations[j].first - locations[i].first
                    var dX = locations[j].second - locations[i].second
                    if(locations[i].first - dY in 0..<height &&
                        locations[i].second - dX in 0..<width)
                        antinodes.add(locations[i].first - dY to locations[i].second - dX)

                    if(locations[j].first + dY in 0..<height &&
                        locations[j].second + dX in 0..<width)
                        antinodes.add(locations[j].first + dY to locations[j].second + dX)
                }
            }
        }

        return antinodes.size
    }

    fun part2(input: List<String>): Int {
        var antinodes = mutableSetOf<Pair<Int,Int>>()
        var antennas = parseInput(input)

        val width = input[0].length
        val height = input.size

        for ((k, locations) in antennas) {
            locations.forEachIndexed { i, _ ->
                for(j in i+1..<locations.size){
                    val deltaY = locations[j].first - locations[i].first
                    val deltaX = locations[j].second - locations[i].second

                    var posY = locations[i].first
                    var posX = locations[i].second
                    while (posY + deltaY in 0..<height && posX + deltaX in 0..<width) {
                        antinodes.add(posY + deltaY to posX + deltaX)
                        posY += deltaY
                        posX += deltaX
                    }

                    posY = locations[j].first
                    posX = locations[j].second
                    while (posY - deltaY in 0..<height && posX - deltaX in 0..<width) {
                        antinodes.add(posY - deltaY to posX - deltaX)
                        posY -= deltaY
                        posX -= deltaX
                    }
                }
            }
        }

        return antinodes.size
    }

    val testInput = readInput("Day08_test")
    check(part1(testInput) == 14)
    check(part2(testInput) == 34)

    val input = readInput("Day08")
    part1(input).println()
    part2(input).println()
}