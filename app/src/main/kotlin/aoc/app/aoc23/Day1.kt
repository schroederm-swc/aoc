package aoc.app.aoc23

import aoc.app.Utils
import java.util.stream.Collectors

private const val FILE_AOC_2023_1 = "/aoc-2023-1-input.txt"
private const val FILE_AOC_2023_1_2 = "/aoc-2023-1-2-input.txt"

class Day1 {

    fun solve(){
        val lines1 = Utils.resourceToLines(FILE_AOC_2023_1)
        val lines2 = Utils.resourceToLines(FILE_AOC_2023_1_2)

        val sum = lines1.stream().mapToInt{getNumbers(it)}.sum()
        val sumHard = lines2.stream().mapToInt{getNumbersHard(it)}.sum()
        println("Sum of calibration values: $sum")
        println("Hard: $sumHard")
    }

    private fun getNumbers(word: String) : Int{
        var first:Char? = null
        var last:Char? = null
        for (char in word) {
            if (char.isDigit() && first == null) {
                first = char
            }
            if (char.isDigit()) {
                last = char
            }
        }
        return ("$first$last".toInt())
    }

    private fun getNumbersHard(word: String) : Int {
        val mapping: Map<String, Int> = mapOf(
            "one" to 1,
            "two" to 2,
            "three" to 3,
            "four" to 4,
            "five" to 5,
            "six" to 6,
            "seven" to 7,
            "eight" to 8,
            "nine" to 9
        )

        // Key=index, value=digit value
        var allDigits: MutableMap<Int, Int> = HashMap()
        for (pair in mapping) {
            if (word.contains(pair.key, true)) {
                val regex = Regex(pair.key)
                regex.findAll(word).forEach { allDigits[it.range.first] = pair.value }
            }
        }
        for ((index, char) in word.withIndex()) {
            if (char.isDigit()) {
                allDigits[index] = char.digitToInt()
            }
        }

        val list : List<Int> = allDigits.entries.stream().sorted { o1, o2 -> compareValues(o1.key, o2.key) }.map{ v -> v.value}.collect(
            Collectors.toList())
        return "${list.first()}${list.last()}".toInt()
    }
}


