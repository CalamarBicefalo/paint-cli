package com.springernature.paint


/**
 * Blank space that supports drawing and rendering operations
 */
interface Canvas : Drawable, Renderable

/**
 * Char based implementation of a canvas.
 *
 * Holds state in a 2d Array of chars.
 */
class CharCanvas(val width: Int, val height: Int) : Canvas {
    val canvas = array2dOfChar(width, height)

    override fun draw(line: Line) {
        reversingRange(line.start.x, line.end.x).forEach { canvas[line.start.y - 1][it - 1] = 'x' }
        reversingRange(line.start.y, line.end.y).forEach { canvas[it - 1][line.start.x - 1] = 'x' }
    }

    override fun draw(rectangle: Rectangle) {
        rectangle.lines.forEach{ this.draw(it) }
    }

    override fun render(): String {
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

}

/**
 * Drawing operations based on geometric shapes
 */
interface Drawable {
    fun draw(line: Line)
    fun draw(rectangle: Rectangle)
}

/**
 * Rendering operations returning Strings as a result
 */
interface Renderable {
    fun render(): String
}

fun reversingRange(start: Int, end: Int) = if (start < end) (start .. end) else (start downTo end)
fun array2dOfChar(x: Int, y: Int) = Array(y) { CharArray(x, {' '}) }
fun CharArray.render(): String {return this.asSequence().map { it }.joinToString("")}