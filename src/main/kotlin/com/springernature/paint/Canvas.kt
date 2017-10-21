package com.springernature.paint

class Canvas(val width: Int, val height: Int) {
    val canvas = array2dOfChar(width, height)

    override fun toString(): String {
        var output = ""
        val horizontal = drawHorizontalDivider()
        output += horizontal
        (0 until height).forEach{
            output += "|" + canvas[it].render() + "|\n"
        }
        output += horizontal
        return output
    }

    private fun drawHorizontalDivider(): String {
        var horizontal = "-".repeat(width + 2)
        horizontal += "\n"
        return horizontal
    }

    fun draw(line: Line) {
        (line.start.x .. line.end.x).forEach { canvas[line.start.y - 1][it - 1] = 'x' }
    }
}

fun array2dOfChar(x: Int, y: Int) = Array(y) { CharArray(x, {' '}) }
fun CharArray.render(): String {return this.asSequence().map { it }.joinToString("")}