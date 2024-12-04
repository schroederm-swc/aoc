package aoc.app.aoc2024

import aoc.app.Utils

private const val FILE_AOC_2024_3 = "/aoc-2024-4-input.txt"

class Day4AOC2024 {
    fun solve(){
        solve2()
    }

    private fun solve1(){
        val charMatrix = Utils.resourceToCharMatrix(FILE_AOC_2024_3)
        findCoordinatesOfChar('X', charMatrix)
            .sumOf { howManyXmasForThisX(it, charMatrix) }
            .let { println("Sum of XMAS: $it") }
    }

    private fun solve2(){
        // doesn't seem to be correct yet
        val charMatrix = Utils.resourceToCharMatrix(FILE_AOC_2024_3)
        findCoordinatesOfChar('A', charMatrix)
            .sumOf { howManyXMasForThisA(it, charMatrix) }
            .let { println("Sum of X-MAS: $it") }
    }

    private fun findCoordinatesOfChar(charToFind: Char ,charMatrix: List<List<Char>>): List<Pair<Int, Int>> {
        val coordinates = mutableListOf<Pair<Int, Int>>()
        charMatrix.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { colIndex, char ->
                if (char == charToFind) {
                    coordinates.add(Pair(rowIndex, colIndex))
                }
            }
        }
        return coordinates
    }

    private fun howManyXMasForThisA(aCoord: Pair<Int, Int>, charMatrix: List<List<Char>>): Int {
        val xRow = aCoord.first
        val xCol = aCoord.second
        val rowMaxIndex = charMatrix.size - 1
        val colMaxIndex = charMatrix[0].size - 1
        if (xRow-1<0 || xRow+1>rowMaxIndex || xCol-1<0 || xCol+1>colMaxIndex) {
            return 0
        }

        val charCircle = charMatrix[xRow-1].subList(xCol-1, xCol+2)
            .plus(charMatrix[xRow][xCol+1])
            .plus(charMatrix[xRow+1].subList(xCol-1, xCol+2).reversed())
            .plus(charMatrix[xRow][xCol-1])

        val possibleMsInCircle = findMasMsForCharCircle(charCircle)
        var numCrosses = 0
        // diagonal cross
        if (possibleMsInCircle.filter { it % 2 == 0 }.size == 2) {
            numCrosses++
        }
        // vertical cross
        if (possibleMsInCircle.filter { it % 2 == 1 }.size == 2) {
            numCrosses++
        }
        return numCrosses
    }

    private fun findMasMsForCharCircle(charCircle: List<Char>): List<Int> {
        val foundMs = mutableListOf<Int>()
        charCircle.forEachIndexed { index, c ->
            val possibleSIndex = if (index < 4) index+4 else index-4
            if (c == 'M' && charCircle[possibleSIndex] == 'S') {
                foundMs.add(index)
            }
        }
        return foundMs
    }

    private fun howManyXmasForThisX(xCoord: Pair<Int, Int>, charMatrix: List<List<Char>>): Int {
        var foundXmas = 0
        val xRow = xCoord.first
        val xCol = xCoord.second
        val rowMaxIndex = charMatrix.size - 1
        val colMaxIndex = charMatrix[0].size - 1
        // vertical
        if (colMaxIndex >= xCol+3
            && charMatrix[xRow][xCol+1] == 'M'
            && charMatrix[xRow][xCol+2] == 'A'
            && charMatrix[xRow][xCol+3] == 'S') {
            foundXmas++
        }
        // reverse
        if (xCol-3 >= 0
            && charMatrix[xRow][xCol-1] == 'M'
            && charMatrix[xRow][xCol-2] == 'A'
            && charMatrix[xRow][xCol-3] == 'S') {
            foundXmas++
        }
        // down
        if (xRow+3 <= rowMaxIndex
            && charMatrix[xRow+1][xCol] == 'M'
            && charMatrix[xRow+2][xCol] == 'A'
            && charMatrix[xRow+3][xCol] == 'S') {
            foundXmas++
        }
        // up
        if (xRow-3 >= 0
            && charMatrix[xRow-1][xCol] == 'M'
            && charMatrix[xRow-2][xCol] == 'A'
            && charMatrix[xRow-3][xCol] == 'S') {
            foundXmas++
        }
        // up vert
        if (xRow-3 >= 0 && colMaxIndex >= xCol+3
            && charMatrix[xRow-1][xCol+1] == 'M'
            && charMatrix[xRow-2][xCol+2] == 'A'
            && charMatrix[xRow-3][xCol+3] == 'S') {
            foundXmas++
        }
        // down vert
        if (xRow+3 <= rowMaxIndex && colMaxIndex >= xCol+3
            && charMatrix[xRow+1][xCol+1] == 'M'
            && charMatrix[xRow+2][xCol+2] == 'A'
            && charMatrix[xRow+3][xCol+3] == 'S') {
            foundXmas++
        }
        // up reverse
        if (xRow-3 >= 0 && xCol-3 >= 0
            && charMatrix[xRow-1][xCol-1] == 'M'
            && charMatrix[xRow-2][xCol-2] == 'A'
            && charMatrix[xRow-3][xCol-3] == 'S') {
            foundXmas++
        }
        // down reverse
        if (xRow+3 <= rowMaxIndex && xCol-3 >= 0
            && charMatrix[xRow+1][xCol-1] == 'M'
            && charMatrix[xRow+2][xCol-2] == 'A'
            && charMatrix[xRow+3][xCol-3] == 'S') {
            foundXmas++
        }

        return foundXmas
    }

}