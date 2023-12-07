package aoc.app.aoc22

import aoc.app.Utils

private const val FILE_AOC_2022_2_1 = "/aoc-2022-2-1-input.txt"
private const val FILE_AOC_2022_2_2 = "/aoc-2022-2-2-input.txt"

class Day2AOC2022 {

    private val charMappings = mapOf(
        "X" to "rock",
        "Y" to "paper",
        "Z" to "scissors",
        "A" to "rock",
        "B" to "paper",
        "C" to "scissors",
    )

    private val pointMappings = mapOf(
        "rock" to 1,
        "paper" to 2,
        "scissors" to 3,
    )

    private val winners = mapOf(
        "rock" to "scissors",
        "paper" to "rock",
        "scissors" to "paper"
    )

    private val losers = winners.map { (k,v) -> v to k }.toMap()

    fun solve(){
        val lines1 = Utils.resourceToLines(FILE_AOC_2022_2_1)
        val pointsSum1 = lines1.sumOf { pointsForLine(it) }
        println("Sum of points: $pointsSum1")

        val lines2 = Utils.resourceToLines(FILE_AOC_2022_2_2)
        val pointsSum2 = lines2.sumOf { pointsForLine2(it) }
        println("Sum of points 2: $pointsSum2")
    }

    private fun pointsForLine2(line: String): Int {
        val his = charMappings[line[0].toString()].orEmpty()
        var mine = ""
        var winPoints = 0
        when (line[2]) {
            // lose
            'X' -> {
                mine = winners[his].orEmpty()
                winPoints = 0
                println("Losing with $mine")
            }
            // draw
            'Y' -> {
                mine = his
                winPoints = 3
                println("Draw with $mine")
            }
            // win
            'Z' -> {
                mine = losers[his].orEmpty()
                winPoints = 6
                println("Winning with $mine")
            }
        }
        println("Points: " + (winPoints + pointMappings.getOrDefault(mine, 0)))
        return winPoints + pointMappings.getOrDefault(mine, 0)
    }

    private fun pointsForLine(line: String): Int {
        val mine = charMappings[line[2].toString()].orEmpty()
        val his = charMappings[line[0].toString()].orEmpty()

        return whatAreMyWinPoints(mine, his) + pointMappings.getOrDefault(mine, 0)
    }

    private fun whatAreMyWinPoints(mine: String, his: String) : Int {
        if (mine == his) {
            return 3
        }

        if (winners[mine] == his) {
            return 6
        }
        return 0
    }
}