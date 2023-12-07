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
    }
}
