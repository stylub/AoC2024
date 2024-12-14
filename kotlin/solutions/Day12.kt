package solutions

import utils.println
import utils.readInput
import java.util.*

fun main() {
    fun parseInput(input: List<String>): MutableList<String> {
        val map = mutableListOf<String>()
        input.forEach { line -> map.add(line) }
        return map
    }

    val steps = listOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1)

    fun part1(input: List<String>): Long {
        val map = parseInput(input)
        val processed = mutableSetOf<Pair<Int,Int>>()

        val queue: Queue<Pair<Int,Int>> = LinkedList()
        var sum = 0L
        for(i in map.indices){
            for(j in 0..<map[i].length){
                if(!processed.contains(i to j)){
                    queue.add(i to j)
                    val elementsInRegion = mutableSetOf<Pair<Int,Int>>()
                    var elementsOutRegion = 0

                    while (!queue.isEmpty()){
                        val (x,y) = queue.poll()
                        if(elementsInRegion.contains(x to y))
                            continue

                        if(!(x in 0..<map[i].length && y in map.indices)) elementsOutRegion+=1
                        else if(map[x][y] != map[i][j]) elementsOutRegion+=1
                        else{
                            processed.add(x to y)
                            elementsInRegion.add(x to y)
                            for(step in steps)
                                queue.add(x + step.first to y + step.second)
                        }
                    }
                    sum += elementsInRegion.size * elementsOutRegion
                }
            }
        }
        return sum
    }

    fun part2(input: List<String>): Long {
        val map = parseInput(input)
        val processed = mutableSetOf<Pair<Int,Int>>()

        val queue: Queue<Triple<Int,Int,Int>> = LinkedList()
        var sum = 0L
        for(i in map.indices){
            for(j in 0..<map[i].length){
                if(!processed.contains(i to j)){
                    queue.add(Triple(i,j,-1))
                    val elementsInRegion = mutableSetOf<Pair<Int,Int>>()
                    val sides = mutableSetOf<Triple<Int,Int,Int>>()

                    while (!queue.isEmpty()){
                        val (x,y,step) = queue.poll()
                        if(elementsInRegion.contains(x to y))
                            continue

                        if(!(x in 0..<map[i].length && y in map.indices) || map[x][y] != map[i][j]) {
                            sides.add(Triple(x,y,step))
                        }
                        else{
                            processed.add(x to y)
                            elementsInRegion.add(x to y)
                            for((num,s) in steps.withIndex())
                                queue.add(Triple(x + s.first,y+s.second,num))
                        }
                    }
                    var numOfSides = 0
                    var deleted = mutableSetOf<Triple<Int,Int,Int>>()
                    for(side in sides){
                        if(deleted.contains(side)) continue
                        val (x,y,step) = side
                        deleted.add(side)
                        var down : Int
                        var up : Int
                        if(steps[step].second == 0){
                            var yUp = y
                            var yDown = y
                            while((sides.contains(Triple(x,yDown - 1,step))  && !deleted.contains(Triple(x,yDown - 1,step)))
                                || (sides.contains(Triple(x,yUp + 1,step)) && !deleted.contains(Triple(x,yUp + 1,step)))){
                                if(sides.contains(Triple(x,yDown - 1,step))  && !deleted.contains(Triple(x,yDown - 1,step))){
                                    yDown-=1
                                    deleted.add(Triple(x,yDown,step))
                                }
                                if(sides.contains(Triple(x,yUp + 1,step)) && !deleted.contains(Triple(x,yUp + 1,step))){
                                    yUp +=1
                                    deleted.add(Triple(x,yUp,step))
                                }
                            }
                            numOfSides += 1
                        } else{
                            var xLeft = x
                            var xRight = x
                            while((sides.contains(Triple(xLeft - 1,y,step)) && !deleted.contains(Triple(xLeft - 1,y,step)))
                                || (sides.contains(Triple(xRight + 1,y,step)) && !deleted.contains(Triple(xRight + 1,y,step))) ){
                                if(sides.contains(Triple(xLeft-1,y,step)) && !deleted.contains(Triple(xLeft - 1,y,step))){
                                    xLeft-=1
                                    deleted.add(Triple(xLeft,y,step))
                                }
                                if(sides.contains(Triple(xRight + 1,y,step)) && !deleted.contains(Triple(xRight + 1,y,step))){
                                    xRight +=1
                                    deleted.add(Triple(xRight,y,step))
                                }
                            }
                            numOfSides += 1
                        }
                    }

                    sum += elementsInRegion.size * numOfSides
                }
            }
        }
        return sum
    }

    val testInput = readInput("Day12_test")
//    check(part1(testInput) == 140L)
    check(part2(testInput) == 1206L)

    val input = readInput("Day12")
    part1(input).println()
    part2(input).println()
}