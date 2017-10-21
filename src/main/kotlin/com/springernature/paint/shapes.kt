package com.springernature.paint

data class Line(val p1: Point, val p2: Point)

data class Point(val x: Int, val y: Int): Comparable<Point>{
    override fun compareTo(other: Point): Int {
        val xComparison = x.compareTo(other.x)
        if(xComparison == 0) {
            return y.compareTo(other.y)
        }
        return xComparison
    }

    fun neighbours() = setOf(
            Point(x - 1, y - 1),
            Point(x, y - 1),
            Point(x + 1, y - 1),
            Point(x - 1, y + 1),
            Point(x, y + 1),
            Point(x + 1, y + 1),
            Point(x + 1, y),
            Point(x - 1, y)
    )
}

class Rectangle(p1: Point, p2: Point) {
    private val start: Point
    private val end: Point
    init {
        if(p1 < p2) {
            start = p1
            end = p2
        } else {
            start = p2
            end = p1
        }
    }
    val lines: Iterable<Line> = setOf(
            Line(start, Point(start.x, end.y)),
            Line(start, Point(end.x, start.y)),
            Line(end, Point(start.x, end.y)),
            Line(end, Point(end.x, start.y))
    )

    operator fun contains(point: Point): Boolean {
        return point.x in (start.x .. end.x) && point.y in (start.y .. end.y)
    }

    override fun toString(): String {
        return "Rectangle(start=$start, end=$end)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Rectangle

        if (start != other.start) return false
        if (end != other.end) return false
        if (lines != other.lines) return false

        return true
    }

    override fun hashCode(): Int {
        var result = start.hashCode()
        result = 31 * result + end.hashCode()
        result = 31 * result + lines.hashCode()
        return result
    }


}

data class ColourFill(val from: Point, val colour: Char)