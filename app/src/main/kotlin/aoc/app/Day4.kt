package aoc.app

import kotlin.math.pow


private const val FILE_AOC_2023_4_1 = "/aoc-2023-4-1-input.txt"
private const val FILE_AOC_2023_4_2 = "/aoc-2023-4-2-input.txt"

class Day4 {

    fun solve(){
        val lines1 = Utils.resourceToLines(FILE_AOC_2023_4_1)
        val sumPoints = lines1.sumOf { lineToPoints(it) }

        println("Sum of points: $sumPoints")

        val lines2 = Utils.resourceToLines(FILE_AOC_2023_4_2)
        val sumCards = howManyCards(lines2)
        println("Number of cards: $sumCards")
    }

    private fun howManyCards(lines: List<String>) : Int{
        val cardCounters: MutableList<Int> = mutableListOf()
        for (line in lines) {
            cardCounters.add(1)
        }

        for ((index, line) in lines.withIndex()) {
            var winners = lineToNumWinners(line)
            while (winners > 0) {
                cardCounters[index + winners] += cardCounters[index]
                winners--
            }
        }
        return cardCounters.sumOf { it }
    }

    private fun lineToNumWinners(line: String): Int {
        val winHave = line.split(":")[1].split("|")
        val winnerNumbers = winHave[0].trim().split(" ").filter { it.isNotBlank() }
        val haveNumbers = winHave[1].trim().split(" ").filter { it.isNotBlank() }

        return haveNumbers.filter { winnerNumbers.contains(it) }.size
    }

    private fun lineToPoints(line: String): Double {
        val numWinners = lineToNumWinners(line)
        if (numWinners == 0) {
            return 0.0
        }
        return 2.0.pow(numWinners - 1)
    }


}


