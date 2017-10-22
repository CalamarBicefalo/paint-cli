package com.springernature.paint

/**
 * Blank space that supports drawing and rendering operations
 */
interface Canvas : Drawable, Renderable

/**
 * Drawing operations based on geometric shapes and operations
 */
interface Drawable {
    fun draw(shape: Shape)
    fun draw(colourFill: ColourFill)
}

/**
 * Rendering operations returning Strings as a result (simplest solution that works)
 */
interface Renderable {
    fun render(): String
}

/**
 * Char based implementation of a canvas.
 *
 * Holds state in a 2d Array of chars.
 */
class CharCanvas(val width: Int, val height: Int) : Canvas {
    val canvas = array2dOfChar(width, height)
    // The rectangle is used for convenience as it has some logical operations that are necessary for validation
    private val canvasRectangle = Rectangle(Point(1, 1), Point(width, height))

    private fun getColour(point: Point) = canvas[point.y - 1][point.x - 1]
    private fun setColour(point: Point, colour: Char) {
        canvas[point.y - 1][point.x - 1] = colour
    }

    /*
    DRAWABLE
     */
    override fun draw(shape: Shape) {
        if (shape !in canvasRectangle) {
            throw ShapeOutOfCanvasException()
        }
        shape.getPoints().forEach { setColour(it, 'x') }
    }

    // This certainly deserves an abstraction of its own, but the best moment to do so would be once we had
    // another 'operation' to implement so we can refactor it with confidence that the abstraction works
    // Once the abstraction is clear, we should refactor this logic back into its domain representation i.e. ColourFill
    override fun draw(colourFill: ColourFill) {
        if (colourFill.from !in canvasRectangle) {
            throw ShapeOutOfCanvasException()
        }
        fun draw(points: Iterable<Point>, newColour: Char, originalColour: Char) {
            points
                    .filter { it in canvasRectangle }
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

    As discussed in the README this could be an abstraction of its own e.g. CanvasRenderer. Abstracting it now
    however is not a good idea as there are no other Renderer requirements and we could create the abstraction using
    a suboptimal design. This should be done once a second Renderer is specified.
     */
    override fun render(): String {
        fun drawHorizontalDivider(): String {
            var horizontal = "-".repeat(width + 2)
            horizontal += "\n"
            return horizontal
        }

        var output = ""
        val horizontal = drawHorizontalDivider()
        output += horizontal
        (0 until height).forEach {
            output += "|" + canvas[it].render() + "|\n"
        }
        output += horizontal
        return output
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

fun array2dOfChar(x: Int, y: Int) = Array(y) { CharArray(x, { ' ' }) }
fun CharArray.render(): String {
    return this.asSequence().map { it }.joinToString("")
}