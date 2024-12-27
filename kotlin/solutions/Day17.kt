package solutions

import utils.println
import utils.readInput

fun main() {
    fun parseInput(input: List<String>): Pair<MutableList<Int>, MutableList<Int>> {
        val registers = MutableList(3) { 0 }
        val program = mutableListOf<Int>()

        for (line in input) {
            when {
                line.startsWith("Register") -> {
                    val parts = line.split(": ")
                    val registerName = parts[0].split(" ")[1]
                    val registerValue = parts[1].toInt()
                    val registerNumber = registerName[0].code - 'A'.code
                    registers[registerNumber] = registerValue
                }
                line.startsWith("Program") -> {
                    val parts = line.split(": ")
                    program.addAll(parts[1].split(",").map { it.toInt() })
                }
            }
        }

        return registers to program
    }

    fun part1(input: List<String>): String {
        var (registers, program) = parseInput(input)

        var instructionPointer = 0
        val output = mutableListOf<Int>()

        fun getCombo(value : Int): Int {
            return when(value){
                in 0..3 -> value
                in 4..6 -> registers[value - 4]
                else -> throw IllegalArgumentException("Invalid combo operand: $value")
            }
        }
        fun processOperation(opNum: Int){
            var opcode = program[opNum]
            val literal = program[opNum + 1]
            val combo = getCombo(literal)

            when(opcode){
                0 -> registers[0] = registers[0] shr combo
                1 -> registers[1] = registers[1] xor literal
                2 -> registers[1] = combo % 8
                3 -> if(registers[0] != 0) instructionPointer = literal - 2
                4 -> registers[1] = registers[1] xor registers[2]
                5 -> output.add(combo % 8)
                6 -> registers[1] = registers[0] shr combo
                7 -> registers[2] = registers[0] shr combo
            }
        }

        while (instructionPointer < program.size){
            processOperation(instructionPointer)
            instructionPointer +=2
        }

        return output.joinToString(separator = ",")
    }

    fun part2(input: List<String>): Int {
        return 1
    }

    val testInput = readInput("Day17_test")
    check(part1(testInput) == "4,6,3,5,6,3,5,2,1,0")
    check(part2(testInput) == 1)

    val input = readInput("Day17")
    part1(input).println()
    part2(input).println()
}