package aoc.app.aoc23

import aoc.app.Utils
import kotlin.math.*

private const val FILE_AOC_2023_6 = "/aoc-2023-6-input.txt"
private val EXAMPLE_INPUT = listOf(
    "Time:      7  15   30",
    "Distance:  9  40  200"
)

class Day6 {
    fun solve(){
        val lines = Utils.resourceToLines(FILE_AOC_2023_6)

        val result1 = linesToRaces(lines)
            .map { calculateNumOfOptions(it) }
            .reduce { acc: Long, i: Long -> acc * i }
        println("Results1: $result1")

        val race2 = linesToSingleRace(lines)
        val result2 = calculateNumOfOptions(race2)
        println("Result2: $result2")
    }

    // returns lower bound and upper bound
    private fun calculateNumOfOptions(race: Race): Long {
        val a: Double = -1.0
        val b: Double = race.first.toDouble()
        val c: Double = race.second * -1.0

        val firstPart = -b/(2*a)
        val secondPart = sqrt(b.pow(2.0)-4*a*c)/(2*a)

        val h1 = firstPart - secondPart
        val h2 = firstPart + secondPart

        return if (h1 < h2) {
            floor(h2).roundToLong() - ceil(h1).roundToLong() + 1
        } else {
            floor(h1).roundToLong() - ceil(h2).roundToLong() + 1
        }
    }

    private fun linesToRaces(lines:List<String>): List<Race>{
        val splitterFunction : ((line: String) -> List<Long>) = {
                line -> line.split(":")[1].trim().split("\\s+".toRegex()).map { it.toLong() }
        }

        val times = splitterFunction(lines[0])
        val distances = splitterFunction(lines[1])
        return times.mapIndexed { index, time -> Race(time, distances[index]) }
    }

    private fun linesToSingleRace(lines:List<String>): Race {
        val splitterFunction : ((line: String) -> Long) = {
                line -> line.split(":")[1].replace(" ", "").toLong()
        }

        return Race(splitterFunction(lines[0]), splitterFunction(lines[1]))
    }

}

typealias Time = Long
typealias Distance = Long
typealias Race = Pair<Time, Distance>