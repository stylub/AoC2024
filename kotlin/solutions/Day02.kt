package solutions

import utils.println
import utils.readInput
import kotlin.math.abs

fun main() {
    fun parseInput(input: List<String>): List<List<Int>> {
        return  input.map { line ->
            line.split("\\s+".toRegex()).map(String::toInt)
        }
    }

    fun checkList(list: List<Int>) : Boolean{
        var last = list[0]
        val sign = if (list[0] - list[1] > 0) 1 else -1
        for(i in 1..<list.size){
            val diff = last - list[i];
            if(!(abs(diff) in 1..3 && diff * sign > 0 ))
                return false

            last = list[i]
        }
        return true;
    }


    fun part1(input: List<String>): Int {
        val parsedData = parseInput(input)
        var solution = 0;
        parsedData.forEach{list ->
            if (checkList(list)){
                solution +=1
            }
        }

        return  solution;
    }

    fun part2(input: List<String>): Int {
        val parsedData = parseInput(input)
        var solution = 0;
        parsedData.forEach{list ->
            var correct = false
            list.forEachIndexed { index, _ ->
                val newList = list.filterIndexed { i, _ -> i != index }
                if(checkList(newList)){
                    correct = true
                }
            }
            if(correct){
                solution+=1
            }
        }

        return  solution;
    }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}