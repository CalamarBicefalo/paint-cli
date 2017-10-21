package com.springernature.paint

data class Line(val start: Point, val end: Point)

data class Point(val x: Int, val y: Int)

class Rectangle(start: Point, end: Point) {
    val lines: Iterable<Line> = setOf(
        Line(start, Point(start.x, end.y)),
        Line(start, Point(end.x, start.y)),
        Line(end, Point(start.x, end.y)),
        Line(end, Point(end.x, start.y))
    )
}