package com.springernature.paint

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class LineTest {

    @Test
    fun `isOblique - when horizontal - returns false`() {
        val line = Line(Point(2, 2), Point(7, 2))

        assertThat(line.isOblique()).isFalse()
    }

    @Test
    fun `isOblique - when vertical - returns false`() {
        val line = Line(Point(7, 5), Point(7, 2))

        assertThat(line.isOblique()).isFalse()
    }

    @Test
    fun `isOblique - when oblique - returns true`() {
        val line = Line(Point(2, 2), Point(3, 5))

        assertThat(line.isOblique()).isTrue()
    }

    @Test
    fun `getPoints - when horizontal line - returns points`() {
        val line = Line(Point(2, 2), Point(4, 2))

        assertThat(line.getPoints()).containsExactly(
                Point(2, 2),
                Point(3, 2),
                Point(4, 2))
    }

    @Test
    fun `getPoints - when vertical line - returns points`() {
        val line = Line(Point(2, 2), Point(2, 4))

        assertThat(line.getPoints()).containsExactly(
                Point(2, 2),
                Point(2, 3),
                Point(2, 4))
    }
    @Test
    fun `getPoints - when single point line - returns point`() {
        val line = Line(Point(2, 2), Point(2, 2))

        assertThat(line.getPoints()).containsExactly(Point(2, 2))
    }

    @Test(expected = UnsupportedShapeException::class)
    fun `getPoints - when oblique - throws unsupported shape exception`() {
        Line(Point(2, 2), Point(4, 4)).getPoints()
    }
}