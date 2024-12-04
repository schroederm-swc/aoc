package aoc.app.aoc23

import aoc.app.Utils
import kotlin.system.exitProcess

private const val FILE_AOC_2023_8_1 = "/aoc-2023-8-input.txt"
private const val EXAMPLE_INPUT_1 = "RL\n" +
        "\n" +
        "AAA = (BBB, CCC)\n" +
        "BBB = (DDD, EEE)\n" +
        "CCC = (ZZZ, GGG)\n" +
        "DDD = (DDD, DDD)\n" +
        "EEE = (EEE, EEE)\n" +
        "GGG = (GGG, GGG)\n" +
        "ZZZ = (ZZZ, ZZZ)"
private const val EXAMPLE_INPUT_2 = "LLR\n" +
        "\n" +
        "AAA = (BBB, BBB)\n" +
        "BBB = (AAA, ZZZ)\n" +
        "ZZZ = (ZZZ, ZZZ)"

private const val EXAMPLE_INPUT_3 = "LR\n" +
        "\n" +
        "11A = (11B, XXX)\n" +
        "11B = (XXX, 11Z)\n" +
        "11Z = (11B, XXX)\n" +
        "22A = (22B, XXX)\n" +
        "22B = (22C, 22C)\n" +
        "22C = (22Z, 22Z)\n" +
        "22Z = (22B, 22B)\n" +
        "XXX = (XXX, XXX)"

class Day8 {

    fun solve(){
        val lines = Utils.resourceToLines(FILE_AOC_2023_8_1)
        //solve1(lines)
        solve2(lines)
    }

    private fun solve1(lines : List<String>){
        val infiniteInstructions = getInfiniteInstructions(lines[0])
        val nodeMap = getNodeMap(lines)

        var steps = 0
        var currentNode = "AAA"
        for (instruction in infiniteInstructions) {
            steps++
            val nodeMapping = nodeMap[currentNode] ?: exitProcess(1)
            if (instruction.value == 'L') {
                currentNode = nodeMapping.first
            } else {
                currentNode = nodeMapping.second
            }

            if (currentNode == "ZZZ") {
                break
            }
        }
        println("Needed steps: $steps")

    }

    private fun solve2(lines : List<String>){
        val infiniteInstructions = getInfiniteInstructions(lines[0])
        val nodeMap = getNodeMap(lines)
        val nodes = nodeMap.keys.toList()

        val endNodes = findEndNodes(nodes)

        var steps = 0
        var currentNodes = findStartNodes(nodes)

        println("Startnodes: ${currentNodes.joinToString(",")}")
        println("Endnodes: ${endNodes.joinToString(",")}")
        for (instruction in infiniteInstructions) {
            steps++

            val followInstruction: (Pair<String, String>) -> String = when (instruction.value == 'L') {
                true -> {{it.first}}
                else -> {{it.second}}
            }

            currentNodes = currentNodes.mapNotNull { nodeMap[it] }.map { followInstruction(it) }

            if (endNodes.containsAll(currentNodes)) {
                break
            }
        }
        println("Needed steps: $steps")

    }

    private fun getNodeMap(lines: List<String>): Map<String, Pair<String, String>>{
        val nodeMap: MutableMap<String, Pair<String, String>> = mutableMapOf()
        lines.subList(2, lines.size).map { lineToMapEntry(it) }.forEach{ nodeMap[it.first] = it.second }
        return nodeMap
    }

    private fun getInfiniteInstructions(instructionSet: String): Sequence<IndexedValue<Char>> {
        return generateSequence ( IndexedValue(0, instructionSet[0])) {
            IndexedValue(
                it.index + 1,
                instructionSet[(it.index + 1) % instructionSet.length]
            )
        }
    }

    private fun findStartNodes(nodes: List<String>): List<String> {
        return nodes.filter { it.endsWith("A") }
    }

    private fun findEndNodes(nodes: List<String>): List<String> {
        return nodes.filter { it.endsWith("Z") }
    }

    private fun lineToMapEntry(line: String): Pair<String, Pair<String, String>> {
        val nodeName = line.substring(0,3)
        val left = line.substring(7,10)
        val right = line.substring(12,15)
        return Pair(nodeName,Pair(left, right))
    }
}