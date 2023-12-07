package aoc.app

import kotlin.streams.asStream

private const val FILE_AOC_2023_5_1 = "/aoc-2023-5-1-input.txt"
private const val FILE_AOC_2023_5_2 = "/aoc-2023-5-2-input.txt"

class Day5 {
    companion object {
        private const val SEED_TO_SOIL = "seed-to-soil map:";
        private const val SOIL_TO_FERT = "soil-to-fertilizer map:";
        private const val FERT_TO_WATER = "fertilizer-to-water map:";
        private const val WATER_TO_LIGHT = "water-to-light map:";
        private const val LIGHT_TO_TEMP = "light-to-temperature map:";
        private const val TEMP_TO_HUM = "temperature-to-humidity map:";
        private const val HUM_TO_LOC = "humidity-to-location map:";
    }

    fun solve(){
        val lines1 = Utils.resourceToLines(FILE_AOC_2023_5_1)
        val lines2 = Utils.resourceToLines(FILE_AOC_2023_5_2)

        solveInternal(lines1, this::parseSeedsLineTask1)
        solveInternal(lines2, this::parseSeedsLineTask2)
    }

    private fun solveInternal(lines: List<String>, seedParser: (line: String) -> Sequence<Long>){
        val seeds = seedParser(lines[0])

        val seedToSoil = findSectionAndMap(lines, SEED_TO_SOIL)
        val soilToFert = findSectionAndMap(lines, SOIL_TO_FERT)
        val fertToWater = findSectionAndMap(lines, FERT_TO_WATER)
        val waterToLight = findSectionAndMap(lines, WATER_TO_LIGHT)
        val lightToTemp = findSectionAndMap(lines, LIGHT_TO_TEMP)
        val tempToHum = findSectionAndMap(lines, TEMP_TO_HUM)
        val humToLoc = findSectionAndMap(lines, HUM_TO_LOC)


        val lowest = seeds.asStream().parallel()
            .map { seedToSoil.getMapping(it) }
            .map { soilToFert.getMapping(it) }
            .map { fertToWater.getMapping(it) }
            .map { waterToLight.getMapping(it) }
            .map { lightToTemp.getMapping(it) }
            .map { tempToHum.getMapping(it) }
            .map { humToLoc.getMapping(it) }
            .mapToLong{ it }
            .min()

        println("Lowest location: $lowest")
    }

    private fun parseSeedsLineTask1(line: String): Sequence<Long> {
        return line.split(":")[1].trim()
            .split(" ").map{ it.toLong() }.asSequence()
    }

    private fun parseSeedsLineTask2(line: String): Sequence<Long> {
        val splitLine = line.split(":")[1].trim().split(" ").map { it.toLong() }
        val pairs: MutableList<Pair<Long, Long>> = mutableListOf()
        var numSeeds: Long = 0
        for (i in (0..<(splitLine.size/2))) {
            val pairStartIndex = i*2
            val newPair = Pair(splitLine[pairStartIndex],splitLine[pairStartIndex+1])
            numSeeds += splitLine[pairStartIndex+1]
            pairs.add(newPair)
        }
        println("Number of seeds: $numSeeds")

        return pairs.asSequence()
            .map { (it.first..<(it.first+it.second)) }
            .flatten()
    }

    private fun findSectionAndMap(lines: List<String>, sectionHeader: String): List<Mapping> {
        val headerIndex = lines.indexOf(sectionHeader)
        var sectionEndIndex = headerIndex
        while (sectionEndIndex < lines.size) {
            if (lines[sectionEndIndex].isBlank()) {
                break
            } else {
                sectionEndIndex++
            }
        }

        return lines.subList(headerIndex+1, sectionEndIndex)
            .map { splitLineToTriple( it ) }
            .sortedBy { it.first }
    }

    // source range start, dest range start, range length
    private fun splitLineToTriple(line: String): Mapping{
        // input is dest range start, source range start, range length
        val split = line.trim().split(" ")
        return Triple(
            split[1].trim().toLong(),
            split[0].trim().toLong(),
            split[2].trim().toLong()
        )
    }

    private fun List<Mapping>.getMapping(source: Long): Long {
        val nearestTriple = this.lastOrNull { it.first <= source }
            ?: return source

        val dist = source - nearestTriple.first
        if (dist < nearestTriple.third) {
            return nearestTriple.second + dist
        }
        return source
    }
}

typealias Mapping = Triple<Long, Long, Long>
