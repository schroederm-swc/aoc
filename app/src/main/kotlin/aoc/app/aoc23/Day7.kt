package aoc.app.aoc23

import aoc.app.Utils

private const val FILE_AOC_2023_7_1 = "/aoc-2023-7-1-input.txt"
private const val FILE_AOC_2023_7_2 = "/aoc-2023-7-2-input.txt"

class Day7 {

    fun solve(){
        val lines1 = Utils.resourceToLines(FILE_AOC_2023_7_1)
        val lines2 = Utils.resourceToLines(FILE_AOC_2023_7_2)

        val winnings1 = lines1.map { readHand(it) }
            .sortedWith { h1, h2 -> compareHands(h1, h2, false) }
            .withIndex()
            .sumOf { (it.index + 1) * it.value.second }

        println("Total sum of winnings no joker: $winnings1")

        val winnings2 = lines2.map { readHand(it) }
            .sortedWith { h1, h2 -> compareHands(h1, h2, true) }
            .withIndex()
            .sumOf { (it.index + 1) * it.value.second }

        println("Total sum of winnings joker: $winnings2")
    }

    private fun compareHands(h1: Hand, h2: Hand, enableJoker: Boolean): Int {
        val rating1 = getHandRating(h1.first, enableJoker)
        val rating2 = getHandRating(h2.first, enableJoker)

        if (rating1 != rating2) {
            return rating1 - rating2
        }

        val possibleValues = if (enableJoker) {
            listOf("A", "K", "Q", "T", "9", "8", "7", "6", "5", "4", "3", "2", "J").reversed()
        } else {
            listOf("A", "K", "Q", "J", "T", "9", "8", "7", "6", "5", "4", "3", "2").reversed()
        }

        for (i in h1.first.indices) {
            if (h1.first[i] == h2.first[i]) {
                continue
            }
            return possibleValues.indexOf(h1.first[i].toString()) - possibleValues.indexOf(h2.first[i].toString())
        }
        return 0
    }

    private fun getHandRating(hand: String, enableJoker: Boolean): Int {
        val workingHand = if (enableJoker) {
            mapJs(hand)
        } else {
            hand
        }

        val characterGroups = workingHand.groupBy { it }.values
        val numGroups = characterGroups.size
        if (numGroups == 1) {
            return 7;
        }
        if (numGroups == 2) {
            val group0 = characterGroups.first()
            return if (group0.size == 1 || group0.size == 4) {
                6
            } else {
                5
            }
        }
        if (numGroups == 3) {
            return if (!characterGroups.find { it.size == 3 }.isNullOrEmpty()) {
                4
            } else {
                3
            }
        }
        return if (numGroups == 4) {
            2
        } else {
            1
        }
    }

    private fun mapJs(hand: String): String {
        if (!hand.contains("J")) {
            return hand
        }
        val characterGroups = hand.groupBy { it }.values
        val biggestGroup = characterGroups
            .filter { it[0] != 'J' }
            .maxByOrNull { it.size }
        if (biggestGroup == null) {
            return "KKKKK"
        }
        return hand.replace('J', biggestGroup[0])
    }


    private fun readHand(line: String): Hand {
        val values = line.split(" ")
        return Hand(values[0], values[1].toLong())
    }
}

typealias Hand = Pair<String, Long>