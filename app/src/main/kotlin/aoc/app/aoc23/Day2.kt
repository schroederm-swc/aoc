package aoc.app.aoc23

import aoc.app.Utils


private const val FILE_AOC_2023_2_1 = "/aoc-2023-2-1-input.txt"
private const val FILE_AOC_2023_2_2 = "/aoc-2023-2-2-input.txt"

class Day2 {

    fun solve(){
        val lines1 = Utils.resourceToLines(FILE_AOC_2023_2_1)

        val indexSum = lines1.stream()
            .map { isGamePossible(it) }
            .filter{ it.second }
            .mapToInt { it.first }
            .sum()
        val cubePowers = Utils.resourceToLines(FILE_AOC_2023_2_2)
            .stream()
            .mapToInt { lineToColorNumPower(it) }
            .sum()

        println("Sum of working game indices: $indexSum")
        println("Sum of cube powers: $cubePowers")

    }

    private fun isGamePossible(line: String) : Pair<Int, Boolean>{
        val gameAndRest = line.split(":")
        val index = gameAndRest[0].split(" ")[1].toInt()
        val cubeSets = gameAndRest[1].split(";")
        val colorNumbers = cubeSets.stream().map { setToColorNums(it) }
        val redMax = 12
        val greenMax = 13
        val blueMax = 14

        for (numbers in colorNumbers) {
            if (numbers[0] > redMax || numbers[1] > greenMax || numbers[2] > blueMax) {
                return index to false
            }
        }

        return index to true
    }

    private fun lineToColorNumPower(line: String) : Int {
        val gameAndRest = line.split(":")
        val sets = gameAndRest[1].trim().split(";")
        val colorNumbers = sets.stream().map { setToColorNums(it) }
        var redMax = 0
        var greenMax = 0
        var blueMax = 0
        for (nums in colorNumbers) {
            if (nums[0] > redMax) {
                redMax = nums[0]
            }
            if (nums[1] > greenMax){
                greenMax = nums[1]
            }
            if (nums[2] > blueMax){
                blueMax = nums[2]
            }
        }
        return redMax * greenMax * blueMax
    }

    // red, green, blue
    private fun setToColorNums(set: String) : Array<Int>{
        val parts = set.split(",")
        var red = 0
        var green = 0
        var blue = 0
        for (part in parts) {
            val values = part.strip().split(" ")
            val number = values[0].toInt()
            when (values[1]) {
                "red" -> red = number
                "green" -> green = number
                "blue" -> blue = number
            }
        }
        return arrayOf(red, green, blue)
    }
}


