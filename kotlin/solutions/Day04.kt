//package solutions
//
//import utils.println
//import utils.readInput
//
//fun main() {
//    fun parseInput(input: List<String>): Pair<List<Int>, List<Int>> {
//        return  input.map { line ->
//            line.split("\\s+".toRegex()).map(String::toInt).let { (a, b) -> a to b }
//        }.unzip()
//    }
//
//    fun findXMAS(input: List<String>): Int {
//        var found = 0
//        input.forEach { word ->
//            found += "XMAS".toRegex().findAll(word).map { it }.toList().size
//            found += "SAMX".toRegex().findAll(word).map { it }.toList().size
//        }
//
//        return found
//    }
//
//
//    fun part1(input: List<String>): Int {
//        val horizontal = mutableListOf<String>()
//        for (j in input.indices) {
//            for (i in 0..input[0].length - 4) {
//                horizontal.add("" + input[j][i] + input[j][i + 1] + input[j][i + 2] + input[j][i + 3])
//            }
//        }
//
//        val vertical = mutableListOf<String>()
//        for (j in 0..input.size - 4) {
//            for (i in 0..<input[0].length) {
//                vertical.addLast("" + input[j][i] + input[j + 1][i] + input[j + 2][i] + input[j + 3][i])
//            }
//        }
//        val diagonal = mutableListOf<String>()
//        for (i in 0..input.size - 4) {
//            for (j in 0..input[0].length - 4) {
//
//                val word = "${input[i][j]}${input[i + 1][j + 1]}${input[i + 2][j + 2]}${input[i + 3][j + 3]}"
//                diagonal.addLast(word)
//            }
//        }
//        for (i in 3..<input.size) {
//            for (j in 0..<input[0].length - 3) {
//
//                val word = "${input[i][j]}${input[i - 1][j + 1]}${input[i - 2][j + 2]}${input[i - 3][j + 3]}"
//                diagonal.Unresolved reference: addLastaddLast(word)
//            }
//        }
//        return findXMAS(horizontal) + findXMAS(vertical) + findXMAS(diagonal)
//    }
//
//    fun part2(input: List<String>): Int {
//        var solution = 0
//
//        for (i in 1..<input.size - 1) {
//            for (j in 1..<input[0].length - 1) {
//                val w1 = "${input[i - 1][j - 1]}${input[i][j]}${input[i + 1][j + 1]}"
//                val w2 = "${input[i + 1][j - 1]}${input[i][j]}${input[i - 1][j + 1]}"
//                if( (w1 == "MAS" || w1 == "SAM") && (w2 == "MAS" || w2 == "SAM"))
//                    solution+=1
//            }
//        }
//        return  solution
//    }
//
//    val testInput = readInput("Day04_test")
//    check(part1(testInput) == 18)
//    check(part2(testInput) == 9)
//
//    val input = readInput("Day04")
//    part1(input).println()
//    part2(input).println()
//}