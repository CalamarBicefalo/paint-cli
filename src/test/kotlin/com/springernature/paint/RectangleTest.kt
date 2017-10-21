package com.springernature.paint

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class RectangleTest {
    @Test
    fun `rectangle lines - returns boundaries of built rectangle`() {
        val rectangle = Rectangle(Point(2, 5), Point(4, 7))

        val lines = rectangle.lines

        assertThat(lines).containsExactlyInAnyOrder(
                Line(Point(2,5), Point(2,7)),
                Line(Point(2,5), Point(4,5)),
                Line(Point(4,7), Point(2,7)),
                Line(Point(4,7), Point(4,5))
        )
    }
}