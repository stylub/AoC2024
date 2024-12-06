package solutions

import utils.println
import utils.readInput
import kotlin.math.abs

fun main() {
    fun parseInput(input: List<String>): Pair<MutableList<Pair<Int, Int>>, MutableList<List<Int>>> {
        var nextPart = false
        val  order = mutableListOf<Pair<Int, Int>>()
        val queue = mutableListOf<List<Int>>()

        input.forEach{line ->
            if(line == "") nextPart = true;
            else if(!nextPart){
                order.addLast(line.split("\\|".toRegex()).map(String::toInt).let { (a, b) -> a to b })
            }else{
                queue.addLast(line.split(",").map { it.toInt() })
            }
        }

        return order to queue
    }

    fun preProcess(order : MutableList<Pair<Int, Int>>): HashMap<Int, MutableSet<Int>> {
        val cantBefore = HashMap<Int,MutableSet<Int>>()

        order.forEach{pair ->
            val a = pair.first
            val b = pair.second

            if (cantBefore.containsKey(b)) {
                cantBefore[b]?.add(a)
            } else {
                cantBefore[b] = mutableSetOf(a)
            }
        }

        return cantBefore
    }


    fun part1(input: List<String>): Int {
        val parsed = parseInput(input)
        val order = parsed.first
        val update = parsed.second

        var cantBefore = preProcess(order)

        var solution = 0

        update.forEach{list ->
            var pass = true

            for(i in list.indices){
                for(j in 0..<i){
                    if(cantBefore[list[j]]?.contains(list[i]) == true){
                        pass = false
                    }
                }
            }
            if(pass) solution += list[(list.size-1) /2]
        }

        return solution;
    }

    fun part2(input: List<String>): Int {
        val parsed = parseInput(input)
        val order = parsed.first
        val update = parsed.second

        val cantBefore = preProcess(order)
        val pageComparator = object : Comparator<Int> {
            override fun compare(num1: Int, num2: Int): Int {
                if(cantBefore[num1]?.contains(num2) == true){
                    return 1
                }
                else
                {
                    return -1
                }
            }
        }
        var solution = 0
        update.forEach { list ->
            val listSorted = list.sortedWith(pageComparator)
            var isOk = false
            for (i in list.indices) {
                if (list[i] != listSorted[i]) {
                    isOk = true
                    break;
                }
            }
            if(isOk) solution += listSorted[(listSorted.size - 1) / 2]
        }
        return solution
    }

    val testInput = readInput("Day05_test")
    check(part1(testInput) == 143)
    check(part2(testInput) == 123)

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}