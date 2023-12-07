package aoc.app


private const val FILE_AOC_2023_3_1 = "/aoc-2023-3-1-input.txt"
private const val FILE_AOC_2023_3_2 = "/aoc-2023-3-2-input.txt"

class Day3 {

    fun solve(){
        val lines1 = Utils.resourceToLines(FILE_AOC_2023_3_1)
        val signs = findSigns(lines1)
        val grownMatrix = grow(signs)

        var sumPartNumbers = 0
        for ((index, line) in lines1.withIndex()) {
            val sumLine = findNumbersForLine(line, grownMatrix[index]).sum()
            sumPartNumbers += sumLine
        }
        println("Sum of partnumbers: $sumPartNumbers")

        val lines2 = Utils.resourceToLines(FILE_AOC_2023_3_2)
        val ratiosSum = calculateGearRatioSum(lines2)
        println("Sum of gear ratios: $ratiosSum")

    }

    private fun calculateGearRatioSum(lines: List<String>) : Int {
        val gearRatios : MutableList<Int> = mutableListOf()
        for ((lineIndex, line) in lines.withIndex()) {
            for ((charIndex, char) in line.withIndex()) {
                if (char == '*') {
                    val gearList: MutableList<Int> = mutableListOf()
                    if (charIndex - 1 >= 0 && line[charIndex - 1].isDigit()) {
                        gearList.add(extendNumber(line, charIndex - 1))
                    }
                    if (charIndex + 1 < line.length && line[charIndex + 1].isDigit()) {
                        gearList.add(extendNumber(line, charIndex + 1))
                    }
                    if (lineIndex - 1 >= 0) {
                        gearList.addAll(numbersForUpperLowerLine(lines[lineIndex - 1], charIndex))
                    }
                    if (lineIndex + 1 < lines.size) {
                        gearList.addAll(numbersForUpperLowerLine(lines[lineIndex + 1], charIndex))
                    }

                    if (gearList.size == 2) {
                        val gearRatio = gearList[0]*gearList[1]
                        gearRatios.add(gearRatio)
                    }
                }
            }
        }
        return gearRatios.sum()
    }

    private fun numbersForUpperLowerLine(line: String, centerIndex: Int): List<Int>{
        val list: MutableList<Int> = mutableListOf()
        val leftIsNum = centerIndex - 1 >= 0 && line[centerIndex - 1].isDigit()
        val centerIsNum = line[centerIndex].isDigit()
        val rightIsNum = centerIndex + 1 < line.length && line[centerIndex + 1].isDigit()
        if (leftIsNum) {
            list.add(extendNumber(line, centerIndex - 1))
        }
        if (centerIsNum && !leftIsNum) {
            list.add(extendNumber(line, centerIndex))
        }
        if (rightIsNum && !centerIsNum) {
            list.add(extendNumber(line, centerIndex + 1))
        }

        return list
    }

    private fun extendNumber(line: String, index: Int) : Int{
        var number = line[index].toString()
        var left = index - 1
        while (left >= 0 && line[left].isDigit()) {
            number = line[left] + number
            left--
        }
        var right = index + 1
        while (right < line.length && line[right].isDigit()) {
            number += line[right]
            right++
        }
        return number.toInt()
    }

    private fun findNumbersForLine(line : String, matches : List<Boolean>) : List<Int>{
        val matchedNumbers : MutableList<Int> = mutableListOf()
        var num = ""
        var hasMatch = false
        for ((index, char) in line.withIndex()) {
            if (char.isDigit()) {
                num = num.plus(char)
                hasMatch = hasMatch || matches[index]
            } else if (hasMatch) {
                matchedNumbers.add(num.toInt())
                num = ""
                hasMatch = false
            } else {
                num = ""
            }
        }
        if (hasMatch) {
            matchedNumbers.add(num.toInt())
        }
        return matchedNumbers
    }

    // <lineIndex, indexInLine>
    private fun findSigns(lines: List<String>) : List<List<Boolean>>{
        val allLines : MutableList<List<Boolean>> = mutableListOf()
        for (line in lines) {
            val lineList : MutableList<Boolean> = mutableListOf()
            for (char in line) {
                lineList.add(!char.isDigit() && char != '.')
            }
            allLines.add(lineList)
        }
        return allLines
    }

    private fun grow(matches : List<List<Boolean>>) : List<List<Boolean>> {
        val grower : MutableList<MutableList<Boolean>> = cloneNestedList(matches)
        val numLines = matches.size
        val numColumns = matches[0].size
        for ((lineIndex, line) in matches.withIndex()) {
            for ((boolIndex, bool) in line.withIndex()) {
                if (bool) {
                    if (boolIndex > 0) {
                        grower[lineIndex][boolIndex - 1] = true
                    }
                    if (boolIndex < numColumns - 1) {
                        grower[lineIndex][boolIndex + 1] = true
                    }

                    if (lineIndex > 0) {
                        grower[lineIndex - 1][boolIndex]=true
                        if (boolIndex > 0) {
                            grower[lineIndex - 1][boolIndex - 1] = true
                        }
                        if (boolIndex < numColumns - 1) {
                            grower[lineIndex - 1][boolIndex + 1] = true
                        }
                    }

                    if (lineIndex < numLines - 1) {
                        grower[lineIndex + 1][boolIndex] = true
                        if (boolIndex > 0) {
                            grower[lineIndex + 1][boolIndex - 1] = true
                        }
                        if (boolIndex < numColumns - 1) {
                            grower[lineIndex + 1][boolIndex + 1] = true
                        }
                    }
                }
            }
        }
        return grower
    }

    private fun cloneNestedList(source : List<List<Boolean>>) : MutableList<MutableList<Boolean>> {
        val target : MutableList<MutableList<Boolean>> = mutableListOf()
        for (line in source) {
            val targetLine : MutableList<Boolean> = mutableListOf()
            for (someVal in line) {
                targetLine.add(someVal.and(true))
            }
            target.add(targetLine)
        }
        return target
    }
}


