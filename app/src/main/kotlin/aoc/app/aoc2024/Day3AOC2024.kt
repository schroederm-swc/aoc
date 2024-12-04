package aoc.app.aoc2024

import aoc.app.Utils

private const val FILE_AOC_2024_3 = "/aoc-2024-3-input.txt"

class Day3AOC2024 {
    fun solve(){
        solve2()
    }

    private fun solve1(){
        val lines = Utils.resourceToLines(FILE_AOC_2024_3)
        val sumOfMultiplications = findMulInstructions(lines[0])
            .map { (x,y) -> x*y }
            .sum()
        println("Sum of multiplications: $sumOfMultiplications")
    }

    private fun solve2(){
        val lines = Utils.resourceToLines(FILE_AOC_2024_3)
        splitByDosDonts(lines[0]).asSequence()
            .map { findMulInstructions(it) }
            .flatten()
            .map { (x, y) -> x*y }
            .sum()
            .let { println("Sum of multiplications: $it") }
    }

    private fun splitByDosDonts(line : String) : List<String>{
        val regex = "do\\(\\)|don't\\(\\)".toRegex()
        val rangeOfDosDonts = regex.findAll(line)
        var previousEnd = 0
        val list = mutableListOf<String>()
        for (range in rangeOfDosDonts){
            val start = previousEnd
            val end = range.range.first
            val section = line.substring(start, end)
            if (!section.startsWith("don't()")) {
                list.add(section)
            }
            previousEnd = end
        }
        val section = line.substring(previousEnd)
        if (!section.startsWith("don't()")) {
            list.add(section)
        }
        return list
    }

    private fun findMulInstructions (line : String) : Sequence<Pair<Int,Int>>{
        val regex = "mul\\((\\d+),(\\d+)\\)".toRegex()
        return regex.findAll(line).map {
            val (num1, num2) = it.destructured
            num1.toInt() to num2.toInt()
        }
    }
}