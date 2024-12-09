package solutions

import utils.println
import utils.readInput
import java.util.*

fun main() {
    fun parseInputPart1(input: List<String>): MutableList<Int> {
        val uncompressed = mutableListOf<Int>()
        input.forEach {line ->
            line.forEachIndexed{i, c ->
                val fill = if(i%2 == 1) -1 else i/2
                for(j in 0..<(c.code - '0'.code)) uncompressed.add(fill)
            }
        }
        return uncompressed
    }

    fun parseInputPart2(input: List<String>): MutableList<Pair<Int, Int>> {
        val uncompressed = mutableListOf<Pair<Int,Int>>()
        input.forEach {line ->
            line.forEachIndexed{i, c ->
                val fill = if(i%2 == 1) -1 else i/2
                uncompressed.add(c.code - '0'.code to fill)
            }
        }
        return uncompressed
    }

    fun part1(input: List<String>): Long {
        val parsed = parseInputPart1(input)

        var start = 0
        var end = parsed.size - 1

        while(start < end){
            if(parsed[start] != -1) start +=1
            else if(parsed[end] == -1) end -=1
            else Collections.swap(parsed,start,end)
        }
        var checkSum = 0L
        for ((index, c) in parsed.withIndex()){
            if(c == -1) break;
            checkSum += c * index
        }

        return checkSum
    }

    fun part2(input: List<String>): Long {
        val parsed = parseInputPart2(input)

        for(i in parsed.size-1 downTo 0){
            if(parsed[i].second == -1) continue
            for(empty in 0..<i) {
                if(parsed[empty].second != -1) continue
                if (parsed[empty].first > parsed[i].first) {
                    parsed[empty] = parsed[empty].first - parsed[i].first to parsed[empty].second
                    parsed.add(empty, parsed[i])
                    parsed[i + 1] = parsed[i + 1].first to -1
                    break
                } else
                    if (parsed[empty].first == parsed[i].first) {
                        parsed[empty] = parsed[i]
                        parsed[i] = parsed[i].first to -1
                        break
                    }
            }
        }
        var index = 0
        var checkSum = 0L
        for(pair in parsed){
            if(pair.second == -1) index+= pair.first
            else{
                for(i in 0..<pair.first){
                    checkSum += pair.second * index
                    index +=1
                }
            }
        }
        return checkSum
    }

    val testInput = readInput("Day09_test")
    check(part1(testInput) == 1928L)
    check(part2(testInput) == 2858L)

    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}