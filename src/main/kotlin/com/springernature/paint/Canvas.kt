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
        reversingRange(line.start.x, line.end.x).forEach { canvas[line.start.y - 1][it - 1] = 'x' }
        reversingRange(line.start.y, line.end.y).forEach { canvas[it - 1][line.start.x - 1] = 'x' }
    }

    fun draw(rectangle: Rectangle) {
        rectangle.lines.forEach{ this.draw(it) }
    }
}

fun reversingRange(start: Int, end: Int) = if (start < end) (start .. end) else (start downTo end)

fun array2dOfChar(x: Int, y: Int) = Array(y) { CharArray(x, {' '}) }
fun CharArray.render(): String {return this.asSequence().map { it }.joinToString("")}