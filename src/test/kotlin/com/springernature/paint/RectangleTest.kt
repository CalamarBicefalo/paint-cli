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

    @Test
    fun `point in rectangle - when inside rectangle - returns true`() {
        val rectangle = Rectangle(Point(2, 5), Point(4, 7))

        val pointInRectangle = rectangle.contains(Point(3, 6))

        assertThat(pointInRectangle).isTrue()
    }

    @Test
    fun `point in rectangle - when inside inverted rectangle - returns true`() {
        val rectangle = Rectangle(Point(4, 7), Point(2, 5))

        val pointInRectangle = rectangle.contains(Point(3, 6))

        assertThat(pointInRectangle).isTrue()
    }

    @Test
    fun `point in rectangle - when in border - returns true`() {
        val rectangle = Rectangle(Point(2, 5), Point(4, 7))

        val pointInRectangle = rectangle.contains(Point(3, 5))

        assertThat(pointInRectangle).isTrue()
    }

    @Test
    fun `point in rectangle - when outside rectangle - returns false`() {
        val rectangle = Rectangle(Point(2, 5), Point(4, 7))

        assertThat(rectangle.contains(Point(2, 4))).isFalse()
        assertThat(rectangle.contains(Point(3, 4))).isFalse()
        assertThat(rectangle.contains(Point(4, 4))).isFalse()
        assertThat(rectangle.contains(Point(5, 4))).isFalse()
        assertThat(rectangle.contains(Point(5, 5))).isFalse()
        assertThat(rectangle.contains(Point(5, 6))).isFalse()
        assertThat(rectangle.contains(Point(5, 7))).isFalse()

        assertThat(rectangle.contains(Point(1, 6))).isFalse()
        assertThat(rectangle.contains(Point(1, 7))).isFalse()
        assertThat(rectangle.contains(Point(1, 8))).isFalse()
        assertThat(rectangle.contains(Point(2, 8))).isFalse()
        assertThat(rectangle.contains(Point(3, 8))).isFalse()

        assertThat(rectangle.contains(Point(100, 117))).isFalse()
    }

    @Test
    fun `point in rectangle - when outside inverted rectangle - returns false`() {
        val rectangle = Rectangle(Point(4, 7), Point(2, 5))

        assertThat(rectangle.contains(Point(2, 4))).isFalse()
        assertThat(rectangle.contains(Point(3, 4))).isFalse()
        assertThat(rectangle.contains(Point(4, 4))).isFalse()
        assertThat(rectangle.contains(Point(5, 4))).isFalse()
        assertThat(rectangle.contains(Point(5, 5))).isFalse()
        assertThat(rectangle.contains(Point(5, 6))).isFalse()
        assertThat(rectangle.contains(Point(5, 7))).isFalse()

        assertThat(rectangle.contains(Point(1, 6))).isFalse()
        assertThat(rectangle.contains(Point(1, 7))).isFalse()
        assertThat(rectangle.contains(Point(1, 8))).isFalse()
        assertThat(rectangle.contains(Point(2, 8))).isFalse()
        assertThat(rectangle.contains(Point(3, 8))).isFalse()

        assertThat(rectangle.contains(Point(100, 117))).isFalse()
    }
}