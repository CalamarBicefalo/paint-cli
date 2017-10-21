package com.springernature.paint

import java.util.*


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
    private fun getColour(point: Point) = canvas[point.y - 1][point.x - 1]
    private fun setColour(point: Point, colour: Char) {
        canvas[point.y - 1][point.x - 1] = colour
    }

    /*
    DRAWABLE
     */
    override fun draw(line: Line) {
        reversingRange(line.p1.x, line.p2.x).forEach { setColour(Point(it, line.p1.y), 'x') }
        reversingRange(line.p1.y, line.p2.y).forEach { setColour(Point(line.p1.x, it), 'x') }
    }

    override fun draw(rectangle: Rectangle) {
        rectangle.lines.forEach { this.draw(it) }
    }

    override fun draw(colourFill: ColourFill) {
        fun inCanvas(point: Point) = point in Rectangle(Point(1, 1), Point(width, height))
        fun draw(points: Iterable<Point>, newColour: Char, originalColour: Char) {
            points
                    .filter { inCanvas(it) }
                    .filter { getColour(it) == originalColour }
                    .forEach {
                        setColour(it, newColour)
                        draw(it.neighbours(), newColour, originalColour)
                    }
        }

        draw(colourFill.from.neighbours(), colourFill.colour, getColour(colourFill.from))
        setColour(colourFill.from, colourFill.colour)
    }

    /*
    RENDERABLE
     */
    override fun render(): String {
        var output = ""
        val horizontal = drawHorizontalDivider()
        output += horizontal
        (0 until height).forEach {
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

    override fun toString(): String {
        return render()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CharCanvas

        if (width != other.width) return false
        if (height != other.height) return false

        return true
    }

    override fun hashCode(): Int {
        var result = width
        result = 31 * result + height
        return result
    }
}

/**
 * Drawing operations based on geometric shapes
 */
interface Drawable {
    fun draw(line: Line)
    fun draw(rectangle: Rectangle)
    fun draw(colourFill: ColourFill)
}

/**
 * Rendering operations returning Strings as a result
 */
interface Renderable {
    fun render(): String
}

fun reversingRange(start: Int, end: Int) = if (start < end) (start..end) else (start downTo end)
fun array2dOfChar(x: Int, y: Int) = Array(y) { CharArray(x, { ' ' }) }
fun CharArray.render(): String {
    return this.asSequence().map { it }.joinToString("")
}