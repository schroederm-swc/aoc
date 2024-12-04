package aoc.app

import aoc.app.aoc2024.Day3AOC2024
import aoc.app.aoc2024.Day4AOC2024
import aoc.app.aoc22.Day1AOC2022
import aoc.app.aoc22.Day2AOC2022
import aoc.app.aoc23.*


enum class Task {
    AOC_2022_D1,
    AOC_2022_D2,
    AOC_2023_D1,
    AOC_2023_D2,
    AOC_2023_D3,
    AOC_2023_D4,
    AOC_2023_D5,
    AOC_2023_D6,
    AOC_2023_D7,
    AOC_2023_D8,
    AOC_2024_D3,
    AOC_2024_D4,
}

fun main() {

    val tasks = mapOf(
            Task.AOC_2022_D1 to Day1AOC2022()::solve,
            Task.AOC_2022_D2 to Day2AOC2022()::solve,
            Task.AOC_2023_D1 to Day1()::solve,
            Task.AOC_2023_D2 to Day2()::solve,
            Task.AOC_2023_D3 to Day3()::solve,
            Task.AOC_2023_D4 to Day4()::solve,
            Task.AOC_2023_D5 to Day5()::solve,
            Task.AOC_2023_D6 to Day6()::solve,
            Task.AOC_2023_D7 to Day7()::solve,
            Task.AOC_2023_D8 to Day8()::solve,
            Task.AOC_2024_D3 to Day3AOC2024()::solve,
            Task.AOC_2024_D4 to Day4AOC2024()::solve,
        )

    tasks[Task.AOC_2024_D4]?.invoke()
}


