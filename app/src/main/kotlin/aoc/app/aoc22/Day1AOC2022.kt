package aoc.app.aoc22

import aoc.app.Utils

private const val FILE_AOC_2022_1 = "/aoc-2022-1-input.txt"

class Day1AOC2022 {
    fun solve(){
        val lines = Utils.resourceToLines(FILE_AOC_2022_1)

        val allElfs = ArrayList<Int>()
        var currentElfVal = 0;

        for (value in lines) {
            if (value.isBlank()) {
                allElfs.add(currentElfVal)
                currentElfVal = 0
            } else {
                currentElfVal += value.toInt()
            }
        }

        println("Highest elf: " + allElfs.max())
    }
}