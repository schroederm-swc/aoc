package aoc.app

class Utils {
    companion object {
        fun getMessage(): String = "Hello      World!"

        fun resourceToLines(filename: String) : List<String> {
            val lines = object {}.javaClass.getResourceAsStream(filename)?.bufferedReader()?.readLines()
            if (lines.isNullOrEmpty()) {
                println("$filename empty or not found")
            }
            return lines.orEmpty()
        }

        fun stringToLines(content: String) : List<String> {
            return content.split("\n")
        }

        fun resourceToCharMatrix(filename: String) : List<List<Char>> {
            val lines = resourceToLines(filename)
            return lines.map { it.toList() }
        }
    }
}
